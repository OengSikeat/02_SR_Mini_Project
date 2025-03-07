package org.example.controller;

import org.example.model.ProductModel;
import org.example.uitis.ProductUtils;
import org.example.view.ProductView;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ProductController {

    ProductView pv=new ProductView();
    ProductModel pm;
    ProductUtils utils=new ProductUtils();
    Scanner sc=new Scanner(System.in);
    //Regex
    String stringOnlyRegex="^[A-Za-z]+$";
    String choiceRegex="^(n|p|f|l|g|w|r|d|s|se|sa|un|ba|re|e)$";
    //Color
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";

    String choice;
    public void choice(){
        while(true){
            System.out.println("Choose your option() : ");
            choice=sc.nextLine().toLowerCase();
            if (!Pattern.matches(stringOnlyRegex,choice)){
                System.out.println(RED+"wrong input, please enter string only"+RESET);
                continue;
            }
            if (!Pattern.matches(choiceRegex,choice)){
                System.out.println(RED+"wrong input try again"+RESET);
                continue;
            }
            switch (choice){
                case"n"->utils.Next();
                case"p"->utils.Previous();
                case"f"->utils.First();
                case"l"->utils.Last();
                case"g"->utils.Goto();
                case"w"->utils.Write();
                case"r"->utils.ReadID();
                case"u"->utils.Update();
                case"d"->utils.Delete();
                case"s"->utils.SearchName();
                case"se"->utils.SetRow();
                case"sa"->utils.Save();
                case"un"->utils.Unsaved();
                case"ba"->utils.Backup();
                case"re"->utils.Restore();
                case"e"->utils.Exit();
            }
            break;
        }
    }

}
