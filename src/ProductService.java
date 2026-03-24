package com.inventory.service;

import com.inventory.model.Product;

import java.util.*;

public class ProductService {

    private List<Product> products;
    private Map<String, Product> productMap; // for fast lookup by ID

    public ProductService(List<Product> products) {
        this.products = products;
        this.productMap = new HashMap<>();
        for (Product p : products) {
            productMap.put(p.getId().toLowerCase(), p);
        }
    }

    // Generate next unique ID
    private String generateId() {
        int max = 0;
        for (Product p : products) {
            try {
                int num = Integer.parseInt(p.getId().replaceAll("[^0-9]", ""));
                if (num > max) max = num;
            } catch (NumberFormatException ignored) {}
        }
        return String.format("P%03d", max + 1);
    }

    public void addProduct(String name, String category, int quantity, double price) {
        String id = generateId();
        Product p = new Product(id, name, category, quantity, price);
        products.add(p);
        productMap.put(id.toLowerCase(), p);
        System.out.println("[SUCCESS] Product added with ID: " + id);
    }

    public boolean updateProduct(String id, String name, String category, int quantity, double price) {
        Product p = productMap.get(id.toLowerCase());
        if (p == null) {
            System.out.println("[ERROR] Product not found with ID: " + id);
            return false;
        }
        p.setName(name);
        p.setCategory(category);
        p.setQuantity(quantity);
        p.setPrice(price);
        System.out.println("[SUCCESS] Product updated: " + id);
        return true;
    }

    public boolean deleteProduct(String id) {
        Product p = productMap.get(id.toLowerCase());
        if (p == null) {
            System.out.println("[ERROR] Product not found with ID: " + id);
            return false;
        }
        products.remove(p);
        productMap.remove(id.toLowerCase());
        System.out.println("[SUCCESS] Product deleted: " + id);
        return true;
    }

    public List<Product> searchByName(String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Product> searchByCategory(String category) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                result.add(p);
            }
        }
        return result;
    }

    public Product getById(String id) {
        return productMap.get(id.toLowerCase());
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }
}
