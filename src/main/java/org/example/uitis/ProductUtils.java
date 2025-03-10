package org.example.uitis;

import org.example.model.ProductModel;
import org.example.service.ProductService;
import org.example.view.ProductView;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ProductUtils {
    private final Scanner sc = new Scanner(System.in);
    private final ProductService productService;
    private final ProductView view;

    public ProductUtils(ProductService productService, ProductView view) {
        this.productService = productService;
        this.view = view;
    }

    public void Next() throws SQLException {
        view.setCurrentPage(view.getCurrentPage() + 1);
    }

    public void Previous() throws SQLException {
        view.setCurrentPage(view.getCurrentPage() - 1);
    }

    public void First() throws SQLException {
        view.setCurrentPage(1);
    }

    public void Last() throws SQLException {
        view.setCurrentPage(productService.getTotalPages(view.getRecordsPerPage()));
    }

    public void Goto() throws SQLException {
        System.out.print("Enter page number: ");
        int page = ValidationUtils.validateNumber();
        view.setCurrentPage(page);
    }

    public void Write() {
        productService.create();
    }

    public void ReadID() throws SQLException {
        int id = ValidationUtils.validateID();
        ProductModel product = productService.getProductById(id);
        if (product != null) {
            System.out.printf("ID: %d, Name: %s, Price: %.2f, Quantity: %d, Date: %s%n",
                    product.getId(), product.getProduct_name(), product.getUnit_price(),
                    product.getQuantity(), product.getImportdate());
        } else {
            System.out.println("\nProduct not found.");
        }
    }

    public void Update() throws SQLException {
        int id = ValidationUtils.validateID();
        ProductModel product = productService.getProductById(id);

        if (product == null) {
            System.out.println("\nProduct not found.");
            return;
        }

        // Display current product in a table
        Table table = new Table(5, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        CellStyle centerStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);

        // Add headers
        List.of("ID", "Product Name", "Unit Price", "Quantity", "Import Date")
                .forEach(header -> table.addCell(header, centerStyle));

        // Set column widths
        IntStream.range(0, 5).forEach(i -> table.setColumnWidth(i, 15, 20));

        // Add product data
        table.addCell(String.valueOf(product.getId()), centerStyle);
        table.addCell(product.getProduct_name(), centerStyle);
        table.addCell(String.format("%.2f", product.getUnit_price()), centerStyle);
        table.addCell(String.valueOf(product.getQuantity()), centerStyle);
        table.addCell(product.getImportdate() != null ? product.getImportdate().toString() : "N/A", centerStyle);

        System.out.println(table.render());

        // Update process
        System.out.println("1. Name\t2. Price\t3. Quantity\t4. All Fields\t5. Back");
        while (true) {
            System.out.print("=> Choose option: ");
            int choice;
            try {
                choice = ValidationUtils.validateNumber();
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a number between 1-5.");
                return;
            }

            switch (choice) {
                case 1:
                    String name = ValidationUtils.validateName();
                    product.setProduct_name(name);
                    break;
                case 2:
                    double price = ValidationUtils.validateUnitPrice();
                    product.setUnit_price(price);
                    break;
                case 3:
                    int qty = ValidationUtils.validateQuantity();
                    product.setQuantity(qty);
                    break;
                case 4:
                    name = ValidationUtils.validateName();
                    product.setProduct_name(name);

                    price = ValidationUtils.validateUnitPrice();
                    product.setUnit_price(price);

                    qty = ValidationUtils.validateQuantity();
                    product.setQuantity(qty);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("\nInvalid option");
                    return;
            }
            productService.addUnsavedUpdatedProduct(product);
        }

    }

    public void Delete() throws SQLException {
        int id = ValidationUtils.validateID();
        productService.delete(id);
    }

    public void SearchName() throws SQLException {
        String name = ValidationUtils.validateName();
        List<ProductModel> results = productService.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("\nNo products found.");
        } else {
            Table table = new Table(4, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
            CellStyle centerStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);

            List.of("ID", "Name", "Price", "Quantity")
                    .forEach(header -> table.addCell(header, centerStyle));
            IntStream.range(0, 4).forEach(i -> table.setColumnWidth(i, 15, 20));

            results.forEach(p -> {
                table.addCell(String.valueOf(p.getId()), centerStyle);
                table.addCell(p.getProduct_name(), centerStyle);
                table.addCell(String.format("%.2f", p.getUnit_price()), centerStyle);
                table.addCell(String.valueOf(p.getQuantity()), centerStyle);
            });
            System.out.println(table.render());
        }
    }

    public void SetRow() throws SQLException {
        System.out.print("Enter records per page: ");
        int records = ValidationUtils.validateNumber();
        view.setRecordsPerPage(records);
    }

    public void Save() throws SQLException {
        productService.saveToDatabase();
    }

    public void Unsaved() {
        System.out.println("\nUI) Unsaved Insert\t\tUU) Unsaved Update");
        String choice = ValidationUtils.validateMainOption();
        if ("ui".equals(choice)) {
            productService.displayUnsavedInsertedProducts();
        } else if ("uu".equals(choice)) {
            productService.displayUnsavedUpdatedProducts();
        } else {
            System.out.println("\nInvalid option");
        }
    }

    public void Backup() throws SQLException {
        productService.backup();
    }

    public void Restore() throws SQLException {
        productService.restore();
    }
}