package org.example.uitis;

import org.example.service.ProductService;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ProductUtils {
    public void Next() {

    }

    public void Previous() {

    }

    public void First() {

    }

    public void Last() {

    }

    public void Goto() {

    }

    public void Write() {

    }

    public void ReadID() {

    }

    public void Update() {
        Scanner sc = new Scanner(System.in);
        ProductService pService = new ProductService();

        try {
            System.out.print("Enter ID to update: ");
            int id = sc.nextInt();

            String sql = "SELECT * FROM productTB WHERE id = ?";
            PreparedStatement ps = pService.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Check if the product exists
            if (rs.next()) {
                // Create table dynamically when product is found
                Table table = new Table(5, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
                CellStyle centerStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);

                // Add headers
                List.of("ID", "Product Name", "Unit Price", "Quantity", "Import Date")
                        .forEach(header -> table.addCell(header, centerStyle));

                // Set column widths
                IntStream.range(0, 5).forEach(i -> table.setColumnWidth(i, 10, 15));

                // Add the product data to the table
                table.addCell(String.valueOf(rs.getInt("id")), centerStyle);
                table.addCell(rs.getString("product_name"), centerStyle);
                table.addCell(String.valueOf(rs.getDouble("unit_price")), centerStyle);
                table.addCell(String.valueOf(rs.getInt("quantity")), centerStyle);
                table.addCell(rs.getString("import_date"), centerStyle);

                // Display the table with the selected product
                System.out.println(table.render());

                //Option to update
                System.out.println("1. Name\t2. Price\t3. Quantity\t4. All Fields\t5. Back");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1 ->
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


    public void Delete() {

    }

    public void SearchName() {

    }

    public void SetRow() {

    }

    public void Save() {

    }

    public void Unsaved() {

    }

    public void Backup() {

    }

    public void Restore() {

    }

    public void Exit() {

    }
}
