package org.example.view;

import org.example.controller.ProductController;
import org.example.model.ProductModel;
import org.example.service.ProductService;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProductView {
    //MVC and table Declaration
    ProductService ps=new ProductService();
    ProductController pc;
    List<ProductModel> products=ps.getAllProduct();
    Table productTable = new Table(5,BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
    CellStyle Center = new CellStyle(CellStyle.HorizontalAlign.CENTER);
    Scanner sc =new Scanner(System.in);



    public void showMenu() throws SQLException {

        productTable.setColumnWidth(0,20,20);
        productTable.setColumnWidth(1,20,20);
        productTable.setColumnWidth(2,20,20);
        productTable.setColumnWidth(3,20,20);
        productTable.setColumnWidth(4,20,20);
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

            System.out.println("________________Menu________________");
            System.out.println("N.Next page        P.Previous Page        F. First Page        L. Last Page        G.Goto");
            System.out.println();
            System.out.println("W) Write        R) Read (id)        D) Delete        S) Search (name)        Se) Set rows");
            System.out.println("sa) Save        Un) Unsaved        Ba) Backup        Re) Restore        E) Exit");
            System.out.println("---------------------------");




    }
}
