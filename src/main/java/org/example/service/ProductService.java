package org.example.service;

import org.example.model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    ProductModel pm;
    //
    String url="jdbc:postgresql://localhost:5432/tbproduct";
    String user="postgres";
    String password="keat6951";


    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,user,password);
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //CREATE


    //READ
    public List<ProductModel> getAllProduct(){
        List <ProductModel> products= new ArrayList<>();
        String sql="SELECT * FROM productTB";

        try (Connection cnt = getConnection();
             Statement stm=cnt.createStatement();
             ResultSet rs=stm.executeQuery(sql)){

            while (rs.next()) {

                int id = rs.getInt("id");
                String productName = rs.getString("product_name");
                double unitPrice = rs.getDouble("unit_price");
                int quantity = rs.getInt("quantity");
                Date importDate = rs.getDate("import_date");


                ProductModel product = new ProductModel(id, productName, unitPrice, quantity, importDate);
                products.add(product);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return products;
    }

    //UPDATE


    //DELETE
}
