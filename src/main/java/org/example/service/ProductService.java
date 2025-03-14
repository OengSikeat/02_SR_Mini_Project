package org.example.service;

import org.example.controller.ProductController;
import org.example.model.ProductModel;
import org.example.uitis.ValidationUtils;
import org.example.view.ProductView;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ProductService {
    List<ProductModel> unsaveUpdate = new ArrayList<>();
    ProductModel pm;
    //
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String password = "151003";


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //CREATE

    //READ
    public List<ProductModel> getAllProduct() {
        List<ProductModel> products = new ArrayList<>();
        String sql = "SELECT * FROM producttb";

        try (Connection cnt = getConnection();
             Statement stm = cnt.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String productName = rs.getString("product_name");
                double unitPrice = rs.getDouble("unit_price");
                int quantity = rs.getInt("quantity");
                Date importDate = rs.getDate("import_date");


                ProductModel product = new ProductModel(id, productName, unitPrice, quantity, importDate);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    //UPDATE
    public void Update(){
        Scanner sc = new Scanner(System.in);
        ProductService pService = new ProductService();

        try {
            System.out.print("Enter ID to update: ");
            int id = ValidationUtils.validateID();

            String sql = "SELECT * FROM producttb WHERE id = ?";
            PreparedStatement ps = pService.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Check if the product exists
            if (rs.next()) {

                ProductModel updatedProduct = new ProductModel(rs.getInt("id"), rs.getString("product_name"), rs.getDouble("unit_price"), rs.getInt("quantity"), rs.getDate("import_date"));

                unsaveUpdate.add(updatedProduct);
                unsaveUpdate.forEach(product -> {
                });


                // Create table dynamically when product is found
                Table table = new Table(5, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
                CellStyle centerStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);

                // Add headers
                List.of("ID", "Product Name", "Unit Price", "Quantity", "Import Date").forEach(header -> table.addCell(header, centerStyle));

                // Set column widths
                IntStream.range(0, 5).forEach(i -> table.setColumnWidth(i, 10, 15));

                // Add the product data to the table
                unsaveUpdate.forEach(product -> {
                    table.addCell(String.valueOf(product.getId()), centerStyle);
                    table.addCell(product.getProduct_name(), centerStyle);
                    table.addCell(String.valueOf(product.getUnit_price()), centerStyle);
                    table.addCell(String.valueOf(product.getQuantity()), centerStyle);
                    table.addCell(String.valueOf(product.getImportdate()), centerStyle);
                });

                // Display the table with the selected product
                System.out.println(table.render());

                //Option to update
                OUTUPDATE:
                while (true) {
                    System.out.println("1. Name\t2. Price\t3. Quantity\t4. All Fields\t5. Back");
                    int choice = ValidationUtils.validateUpdate();

                    switch (choice) {
                        case 1 -> {
                            String name = ValidationUtils.validateName();
                            unsaveUpdate.forEach(product -> {
                                product.setProduct_name(name);
                            });
                        }
                        case 2 -> {
                            double price = ValidationUtils.validateUnitPrice();
                            unsaveUpdate.forEach(product -> {
                                product.setUnit_price(price);
                            });
                        }
                        case 3 -> {
                            int qty = ValidationUtils.validateQuantity();

                            unsaveUpdate.forEach(product -> {
                                product.setUnit_price(qty);
                            });
                        }
                        case 4 -> {
                            String name = ValidationUtils.validateName();
                            double price = ValidationUtils.validateUnitPrice();
                            int qty = ValidationUtils.validateQuantity();

                            unsaveUpdate.forEach(product -> {
                                product.setProduct_name(name);
                                product.setUnit_price(price);
                                product.setQuantity(qty);
                            });
                        }
                        case 5 -> {
                            break OUTUPDATE;
                        }
                        default -> System.out.println("Invalid choice!");
                    }
                }

            } else {
                System.out.println("No product found with ID: " + id);
            }

            // Close resources
            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Unsaved
    public void unSaved(){
        Table table = new Table(5, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
        CellStyle centerStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);

        // Add headers
        List.of("ID", "Product Name", "Unit Price", "Quantity", "Import Date").forEach(header -> table.addCell(header, centerStyle));

        // Set column widths
        IntStream.range(0, 5).forEach(i -> table.setColumnWidth(i, 10, 15));

        // Add the product data to the table
        unsaveUpdate.forEach(product -> {
            table.addCell(String.valueOf(product.getId()), centerStyle);
            table.addCell(product.getProduct_name(), centerStyle);
            table.addCell(String.valueOf(product.getUnit_price()), centerStyle);
            table.addCell(String.valueOf(product.getQuantity()), centerStyle);
            table.addCell(String.valueOf(product.getImportdate()), centerStyle);
        });
        System.out.println(table.render());
    }

    //DELETE
}
