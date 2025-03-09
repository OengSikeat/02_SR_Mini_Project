package org.example.service;

import org.example.model.ProductModel;
import org.example.uitis.ValidationUtils;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ProductService {
    private static final String URL = "jdbc:postgresql://localhost:5432/tbproduct";
    private static final String USER = "postgres";
    private static final String PASSWORD = "keat6951";
    private static final String BACKUP_FILE = "backup_products.dat";

    private final Scanner sc = new Scanner(System.in);
    private static List<ProductModel> unsavedInsertProducts = new ArrayList<>();
    private static List<ProductModel> unsavedUpdatedProducts = new ArrayList<>();

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CRUD Operations
    public void create() {
        String name = ValidationUtils.validateName();
        double price = ValidationUtils.validateUnitPrice();
        int quantity = ValidationUtils.validateQuantity();

        addUnsavedInsertedProduct(name, price, quantity);
    }

    public void addUnsavedInsertedProduct(String name, double price, int quantity) {
        ProductModel product = new ProductModel(0, name, price, quantity);
        unsavedInsertProducts.add(product);
    }

    public void addUnsavedUpdatedProduct(ProductModel product) {
        unsavedUpdatedProducts.add(product);
    }

    public List<ProductModel> getAllProduct() {
        return getProductsByPage(1, Integer.MAX_VALUE);
    }

    public int getTotalRecords() throws SQLException {
        String sql = "SELECT COUNT(*) FROM productTB";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int getTotalPages(int recordsPerPage) throws SQLException {
        int totalRecords = getTotalRecords();
        return (int) Math.ceil((double) totalRecords / recordsPerPage);
    }

    public List<ProductModel> getProductsByPage(int page, int pageSize) {
        List<ProductModel> products = new ArrayList<>();
        String sql = "SELECT * FROM productTB ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, (page - 1) * pageSize);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new ProductModel(
                            rs.getInt("id"),
                            rs.getString("product_name"),
                            rs.getDouble("unit_price"),
                            rs.getInt("quantity"),
                            rs.getDate("import_date")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public ProductModel getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM productTB WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ProductModel(
                            rs.getInt("id"),
                            rs.getString("product_name"),
                            rs.getDouble("unit_price"),
                            rs.getInt("quantity"),
                            rs.getDate("import_date")
                    );
                }
            }
        }
        return null;
    }

    public List<ProductModel> searchByName(String name) throws SQLException {
        List<ProductModel> results = new ArrayList<>();
        String sql = "SELECT * FROM productTB WHERE product_name ILIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new ProductModel(
                            rs.getInt("id"),
                            rs.getString("product_name"),
                            rs.getDouble("unit_price"),
                            rs.getInt("quantity"),
                            rs.getDate("import_date")
                    ));
                }
            }
        }
        return results;
    }

    public void saveToDatabase() throws SQLException {
        String insertSQL = "INSERT INTO productTB (product_name, unit_price, quantity, import_date) VALUES (?, ?, ?, CURRENT_DATE)";
        String updateSQL = "UPDATE productTB SET product_name = ?, unit_price = ?, quantity = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {

            conn.setAutoCommit(false);

            // Save inserted products
            for (ProductModel product : unsavedInsertProducts) {
                insertStmt.setString(1, product.getProduct_name());
                insertStmt.setDouble(2, product.getUnit_price());
                insertStmt.setInt(3, product.getQuantity());
                insertStmt.executeUpdate();
            }

            // Save updated products
            for (ProductModel product : unsavedUpdatedProducts) {
                updateStmt.setString(1, product.getProduct_name());
                updateStmt.setDouble(2, product.getUnit_price());
                updateStmt.setInt(3, product.getQuantity());
                updateStmt.setInt(4, product.getId());
                updateStmt.executeUpdate();
            }

            conn.commit();
            unsavedInsertProducts.clear();
            unsavedUpdatedProducts.clear();
            System.out.println("\nChanges saved to database successfully.");
        } catch (SQLException e) {
            System.err.println("\nFailed to save changes: " + e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM productTB WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "\nProduct deleted successfully." : "Product not found.");
        }
    }

    public void backup() throws SQLException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
            oos.writeObject(getAllProduct());
            System.out.println("Backup created successfully.");
        } catch (IOException e) {
            System.err.println("Backup failed: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void restore() throws SQLException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            List<ProductModel> products = (List<ProductModel>) ois.readObject();
            try (Connection conn = getConnection()) {
                conn.setAutoCommit(false);
                Statement stmt = conn.createStatement();
                stmt.execute("TRUNCATE productTB");

                String sql = "INSERT INTO productTB (id, product_name, unit_price, quantity, import_date) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    for (ProductModel p : products) {
                        pstmt.setInt(1, p.getId());
                        pstmt.setString(2, p.getProduct_name());
                        pstmt.setDouble(3, p.getUnit_price());
                        pstmt.setInt(4, p.getQuantity());
                        pstmt.setDate(5, p.getImportdate() != null ? new java.sql.Date(p.getImportdate().getTime()) : null);
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
                conn.commit();
                System.out.println("Restore completed successfully.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Restore failed: " + e.getMessage());
        }
    }

    public void displayUnsavedInsertedProducts() {
        displayUnsavedList(unsavedInsertProducts, "Inserted");
    }

    public void displayUnsavedUpdatedProducts() {
        displayUnsavedList(unsavedUpdatedProducts, "Updated");
    }

    private void displayUnsavedList(List<ProductModel> list, String type) {
        if (list.isEmpty()) {
            System.out.println("No unsaved " + type.toLowerCase() + " products.");
            return;
        }

        Table table = new Table(5, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
        CellStyle centerStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);

        // Add headers
        List.of("ID", "Product Name", "Unit Price", "Quantity", "Import Date")
                .forEach(header -> table.addCell(header, centerStyle));

        // Set column widths
        IntStream.range(0, 5).forEach(i -> table.setColumnWidth(i, 10, 15));

        // Add product data
        list.forEach(product -> {
            table.addCell(String.valueOf(product.getId()), centerStyle);
            table.addCell(product.getProduct_name(), centerStyle);
            table.addCell(String.valueOf(product.getUnit_price()), centerStyle);
            table.addCell(String.valueOf(product.getQuantity()), centerStyle);
            table.addCell(product.getImportdate() != null ? String.valueOf(product.getImportdate()) : "N/A", centerStyle);
        });

        System.out.println("Unsaved " + type + " Products:");
        System.out.println(table.render());
    }
}