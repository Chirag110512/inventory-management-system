package com.inventory;

import com.inventory.model.Product;
import com.inventory.service.InventoryService;
import com.inventory.service.ProductService;
import com.inventory.service.ReportService;
import com.inventory.ui.MenuHandler;
import com.inventory.util.FileHandler;

import java.util.List;

public class Main {

    private static final String DATA_FILE = "data/products.csv";

    public static void main(String[] args) {

        // 1. Load existing products from CSV
        List<Product> products = FileHandler.loadProducts(DATA_FILE);

        // 2. Wire up services
        ProductService   productService   = new ProductService(products);
        InventoryService inventoryService = new InventoryService(products);
        ReportService    reportService    = new ReportService(products, inventoryService);

        // 3. Add shutdown hook to auto-save on exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            FileHandler.saveProducts(products, DATA_FILE);
            System.out.println("[INFO] Data saved to " + DATA_FILE);
        }));

        // 4. Start CLI menu
        MenuHandler menu = new MenuHandler(productService, inventoryService, reportService);
        menu.start();
    }
}