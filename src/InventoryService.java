package com.inventory.service;

import com.inventory.model.Product;

import java.util.*;

public class InventoryService {

    private List<Product> products;

    public InventoryService(List<Product> products) {
        this.products = products;
    }

    // Return products below the given stock threshold
    public List<Product> getLowStockProducts(int threshold) {
        List<Product> lowStock = new ArrayList<>();
        for (Product p : products) {
            if (p.getQuantity() <= threshold) {
                lowStock.add(p);
            }
        }
        return lowStock;
    }

    // Total value of all stock
    public double getTotalStockValue() {
        double total = 0;
        for (Product p : products) {
            total += p.getQuantity() * p.getPrice();
        }
        return total;
    }

    // Summary: category -> total quantity
    public Map<String, Integer> getStockSummaryByCategory() {
        Map<String, Integer> summary = new TreeMap<>(); // TreeMap keeps categories sorted
        for (Product p : products) {
            summary.merge(p.getCategory(), p.getQuantity(), Integer::sum);
        }
        return summary;
    }

    // Total number of unique products
    public int getTotalUniqueProducts() {
        return products.size();
    }
}
