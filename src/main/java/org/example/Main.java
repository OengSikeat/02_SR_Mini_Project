package org.example;

import org.example.controller.ProductController;
import org.example.service.ProductService;
import org.example.uitis.ProductUtils;
import org.example.view.ProductView;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            ProductService ps = new ProductService();
            ProductView pv = new ProductView(ps, null);
            ProductUtils utils = new ProductUtils(ps, pv);
            ProductController pc = new ProductController(ps, utils);

            while (true) {
                pv.showMenu();
                pc.choice(pv);
            }
        } catch (SQLException e) {
            System.err.println("Application failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}