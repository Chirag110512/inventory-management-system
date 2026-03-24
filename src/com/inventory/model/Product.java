package com.inventory.model;
public class Product {
    private String id;
    private String name;
    private String category;
    private int quantity;
    private double price;
    public Product(String id,String name,String category,int quantity,double price){
        this.id=id;this.name=name;this.category=category;this.quantity=quantity;this.price=price;
    }
    public String getId(){return id;}
    public String getName(){return name;}
    public String getCategory(){return category;}
    public int getQuantity(){return quantity;}
    public double getPrice(){return price;}
    public void setName(String name){this.name=name;}
    public void setCategory(String category){this.category=category;}
    public void setQuantity(int quantity){this.quantity=quantity;}
    public void setPrice(double price){this.price=price;}
    public String toCSV(){return id+","+name+","+category+","+quantity+","+price;}
    public static Product fromCSV(String line){
        String[] p=line.split(",");
        if(p.length!=5)return null;
        return new Product(p[0].trim(),p[1].trim(),p[2].trim(),Integer.parseInt(p[3].trim()),Double.parseDouble(p[4].trim()));
    }
    public String toString(){return String.format("| %-8s | %-20s | %-12s | %-8d | Rs.%-10.2f |",id,name,category,quantity,price);}
}
