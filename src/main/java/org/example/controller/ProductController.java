package org.example.controller;

import org.example.service.ProductService;
import org.example.uitis.ProductUtils;
import org.example.uitis.ValidationUtils;
import org.example.view.ProductView;

import java.sql.SQLException;
import java.util.Scanner;

public class ProductController {
    private final ProductService service;
    private final ProductUtils utils;
    private final Scanner sc = new Scanner(System.in);

    public ProductController(ProductService service, ProductUtils utils) {
        this.service = service;
        this.utils = utils;
    }

    public void choice(ProductView view) throws SQLException {
        String choice = ValidationUtils.validateMainOption();
        while (true) {
            switch (choice) {
                case "n" -> utils.Next();
                case "p" -> utils.Previous();
                case "f" -> utils.First();
                case "l" -> utils.Last();
                case "g" -> utils.Goto();
                case "w" -> utils.Write();
                case "r" -> utils.ReadID();
                case "u" -> utils.Update();
                case "d" -> utils.Delete();
                case "s" -> utils.SearchName();
                case "se" -> utils.SetRow();
                case "sa" -> utils.Save();
                case "un" -> utils.Unsaved();
                case "ba" -> utils.Backup();
                case "re" -> utils.Restore();
                case "e" -> {
                    return;
                }
                default -> System.out.println("\nInvalid option! Please try again.");
            }
            break;
        }

    }
}