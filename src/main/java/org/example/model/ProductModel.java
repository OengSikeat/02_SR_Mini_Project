package org.example.model;

public class ProductModel {
    private int id;
    private String product_name;
    private double unit_price;
    private int quantity;


    public ProductModel(int id, String product_name, double unit_price, int quantity) {
        this.id = id;
        this.product_name = product_name;
        this.unit_price = unit_price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", unit_price=" + unit_price +
                ", quantity=" + quantity +
                '}';
    }
}
