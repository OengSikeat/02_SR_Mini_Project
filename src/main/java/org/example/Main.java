package org.example;

import org.example.controller.ProductController;
import org.example.service.ProductService;
import org.example.view.ProductView;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ProductService ps=new ProductService();
        ProductView pv=new ProductView();
        ProductController pc=new ProductController();

        pv.showMenu();
        pc.choice();
    }
}