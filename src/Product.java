package com.inventory.model;

public class Product {

    private String id;
    private String name;
    private String category;
    private int quantity;
    private double price;

    public Product(String id, String name, String category, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public String getId()       { return id; }
    public String getName()     { return name; }
    public String getCategory() { return category; }
    public int getQuantity()    { return quantity; }
    public double getPrice()    { return price; }

    // Setters
    public void setName(String name)         { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setQuantity(int quantity)    { this.quantity = quantity; }
    public void setPrice(double price)       { this.price = price; }

    // Convert to CSV line for saving
    public String toCSV() {
        return id + "," + name + "," + category + "," + quantity + "," + price;
    }

    // Create Product from CSV line
    public static Product fromCSV(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5) return null;
        return new Product(
            parts[0].trim(),
            parts[1].trim(),
            parts[2].trim(),
            Integer.parseInt(parts[3].trim()),
            Double.parseDouble(parts[4].trim())
        );
    }

    @Override
    public String toString() {
        return String.format("| %-8s | %-20s | %-12s | %-8d | Rs.%-10.2f |",
                id, name, category, quantity, price);
    }
}
