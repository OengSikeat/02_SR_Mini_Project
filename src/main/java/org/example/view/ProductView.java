package org.example.view;

import org.example.controller.ProductController;
import org.example.model.ProductModel;
import org.example.service.ProductService;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.SQLException;
import java.util.List;

public class ProductView {
    //MVC and table Declaration
    ProductService ps = new ProductService();
    List<ProductModel> products = ps.getAllProduct();

    public void showMenu() throws SQLException {
        Table productTable = new Table(5, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        CellStyle Center = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        productTable.setColumnWidth(0, 20, 20);
        productTable.setColumnWidth(1, 20, 20);
        productTable.setColumnWidth(2, 20, 20);
        productTable.setColumnWidth(3, 20, 20);
        productTable.setColumnWidth(4, 20, 20);
        productTable.addCell("ID");
        productTable.addCell("Name");
        productTable.addCell("Unit Price");
        productTable.addCell("Quantity");
        productTable.addCell("Import Date");
        for (ProductModel product : products) {
            productTable.addCell(String.valueOf(product.getId()));
            productTable.addCell(product.getProduct_name());
            productTable.addCell(String.valueOf(product.getUnit_price()));
            productTable.addCell(String.valueOf(product.getQuantity()));
            productTable.addCell(String.valueOf(product.getImportdate()));
        }

        System.out.println(productTable.render());

        System.out.println("\n\t\t\t\t\t\t\t=================* MENU *=================\n");
        System.out.println("\t\t\t\tN.Next\t\tP.Previous\t\tF. First Page\t\tL. Last\t\tG.Goto\t\n");
        System.out.println("W) Write\t\tR) Read (id)\t\tU) Update\t\tD) Delete\t\tS) Search (name)\t\tSe) Set rows");
        System.out.println("sa) Save\t\tUn) Unsaved\t\t\tBa) Backup\t\tRe) Restore\t\tE) Exit");
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }
}
