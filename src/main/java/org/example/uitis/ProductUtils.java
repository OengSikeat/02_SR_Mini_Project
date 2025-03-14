package org.example.uitis;

import org.example.controller.ProductController;
import org.example.model.ProductModel;
import org.example.service.ProductService;
import org.example.view.ProductView;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ProductUtils {
    ProductService ps = new ProductService();

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
        ps.Update();
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
        ps.unSaved();
    }

    public void Backup() {

    }

    public void Restore() {

    }
}
