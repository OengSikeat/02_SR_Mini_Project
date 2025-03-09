package org.example.view;

import org.example.controller.ProductController;
import org.example.model.ProductModel;
import org.example.service.ProductService;
import org.example.uitis.rowManager;
import org.nocrala.tools.texttablefmt.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ProductView {
    private final ProductService ps;
    private final ProductController pc;
    private int recordsPerPage;
    private int currentPage = 1;
    private int totalPages;

    public ProductView(ProductService ps, ProductController pc) throws SQLException {
        this.ps = ps;
        this.pc = pc;
        this.recordsPerPage = rowManager.getRecordsPerPage();
        this.totalPages = ps.getTotalPages(recordsPerPage);
    }

    public void setRecordsPerPage(int records) throws SQLException {
        this.recordsPerPage = records;
        rowManager.setRecordsPerPage(records);
        this.totalPages = ps.getTotalPages(recordsPerPage);
        this.currentPage = 1;
    }

    public void showMenu() throws SQLException {
        List<ProductModel> products = ps.getProductsByPage(currentPage, recordsPerPage);
        totalPages = ps.getTotalPages(recordsPerPage);

        Table table = new Table(5, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        CellStyle center = new CellStyle(CellStyle.HorizontalAlign.CENTER);

        // Add headers
//        table.addCell("");
        List.of("ID", "Product Name", "Unit Price", "Quantity", "Import Date").forEach(header -> table.addCell(header, center));

        // Set column widths
        IntStream.range(0, 5).forEach(i -> table.setColumnWidth(i, 20, 30));


        for (ProductModel product : products) {
            table.addCell(String.valueOf(product.getId()), center);
            table.addCell(product.getProduct_name(), center);
            table.addCell(String.format("%.2f", product.getUnit_price()), center);
            table.addCell(String.valueOf(product.getQuantity()), center);
            table.addCell(product.getImportdate() != null ? product.getImportdate().toString() : "N/A", center);
        }

        table.addCell("Page: " + currentPage + "/" + totalPages, center, 2);
        table.addCell("Total Records: " + ps.getTotalRecords(), center, 3);

        System.out.println(table.render());
        displayMenu();
    }

    private void displayMenu() {
        System.out.println("\n\t\t\t\t\t\t\t=================* MENU *=================\n");
        System.out.println("\t\t\t\tN.Next\t\tP.Previous\t\tF. First Page\t\tL. Last\t\tG.Goto\t\n");
        System.out.println("W) Write\t\tR) Read (id)\t\tU) Update\t\tD) Delete\t\tS) Search (name)\t\tSe) Set rows");
        System.out.println("sa) Save\t\tUn) Unsaved\t\t\tBa) Backup\t\tRe) Restore\t\tE) Exit");
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }

    public void setCurrentPage(int page) throws SQLException {
        if (page >= 1 && page <= totalPages) {
            currentPage = page;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }
}