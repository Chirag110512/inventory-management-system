package com.inventory.ui;

import com.inventory.model.Product;
import com.inventory.service.InventoryService;
import com.inventory.service.ProductService;
import com.inventory.service.ReportService;

import java.util.List;
import java.util.Scanner;

public class MenuHandler {

    private ProductService productService;
    private InventoryService inventoryService;
    private ReportService reportService;
    private Scanner scanner;
    private static final String REPORT_DIR = "reports";

    public MenuHandler(ProductService productService,
                       InventoryService inventoryService,
                       ReportService reportService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.reportService = reportService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("\n==========================================");
        System.out.println("   INVENTORY MANAGEMENT SYSTEM v1.0      ");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": viewAllProducts();       break;
                case "2": addProduct();            break;
                case "3": updateProduct();         break;
                case "4": deleteProduct();         break;
                case "5": searchProduct();         break;
                case "6": checkLowStock();         break;
                case "7": showReports();           break;
                case "8": running = false;
                          System.out.println("\n[INFO] Saving data and exiting. Goodbye!"); break;
                default:  System.out.println("[WARN] Invalid choice. Try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n------------------------------------------");
        System.out.println("  1. View All Products");
        System.out.println("  2. Add Product");
        System.out.println("  3. Update Product");
        System.out.println("  4. Delete Product");
        System.out.println("  5. Search Product");
        System.out.println("  6. Low Stock Alert");
        System.out.println("  7. Reports & Export");
        System.out.println("  8. Exit");
        System.out.println("------------------------------------------");
    }

    private void viewAllProducts() {
        List<Product> all = productService.getAllProducts();
        if (all.isEmpty()) {
            System.out.println("[INFO] No products found.");
            return;
        }
        ReportService.printTableHeader();
        for (Product p : all) System.out.println(p);
        ReportService.printTableFooter();
        System.out.println("Total: " + all.size() + " product(s)");
    }

    private void addProduct() {
        System.out.println("\n--- Add New Product ---");
        System.out.print("Name     : "); String name     = scanner.nextLine().trim();
        System.out.print("Category : "); String category = scanner.nextLine().trim();
        int    qty   = readInt("Quantity : ");
        double price = readDouble("Price    : ");
        productService.addProduct(name, category, qty, price);
    }

    private void updateProduct() {
        System.out.println("\n--- Update Product ---");
        System.out.print("Enter Product ID to update: ");
        String id = scanner.nextLine().trim();
        Product existing = productService.getById(id);
        if (existing == null) {
            System.out.println("[ERROR] Product not found: " + id);
            return;
        }
        System.out.println("Current: " + existing);
        System.out.print("New Name     [" + existing.getName()     + "]: "); String name     = scanner.nextLine().trim();
        System.out.print("New Category [" + existing.getCategory() + "]: "); String category = scanner.nextLine().trim();
        int    qty   = readInt("New Quantity [" + existing.getQuantity() + "]: ");
        double price = readDouble("New Price    [" + existing.getPrice() + "]: ");

        if (name.isEmpty())     name     = existing.getName();
        if (category.isEmpty()) category = existing.getCategory();

        productService.updateProduct(id, name, category, qty, price);
    }

    private void deleteProduct() {
        System.out.println("\n--- Delete Product ---");
        System.out.print("Enter Product ID to delete: ");
        String id = scanner.nextLine().trim();
        System.out.print("Are you sure? (y/n): ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("y")) {
            productService.deleteProduct(id);
        } else {
            System.out.println("[INFO] Delete cancelled.");
        }
    }

    private void searchProduct() {
        System.out.println("\n--- Search Product ---");
        System.out.println("  1. Search by Name");
        System.out.println("  2. Search by Category");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();

        List<Product> results;
        if (choice.equals("1")) {
            System.out.print("Enter name keyword: ");
            String keyword = scanner.nextLine().trim();
            results = productService.searchByName(keyword);
        } else if (choice.equals("2")) {
            System.out.print("Enter category: ");
            String cat = scanner.nextLine().trim();
            results = productService.searchByCategory(cat);
        } else {
            System.out.println("[WARN] Invalid choice.");
            return;
        }

        if (results.isEmpty()) {
            System.out.println("[INFO] No matching products found.");
        } else {
            ReportService.printTableHeader();
            for (Product p : results) System.out.println(p);
            ReportService.printTableFooter();
            System.out.println("Found: " + results.size() + " product(s)");
        }
    }

    private void checkLowStock() {
        int threshold = readInt("Enter low stock threshold (e.g. 5): ");
        List<Product> lowStock = inventoryService.getLowStockProducts(threshold);
        if (lowStock.isEmpty()) {
            System.out.println("[INFO] All products are sufficiently stocked.");
        } else {
            System.out.println("\n[ALERT] " + lowStock.size() + " product(s) below threshold " + threshold + ":");
            ReportService.printTableHeader();
            for (Product p : lowStock) System.out.println(p);
            ReportService.printTableFooter();
        }
    }

    private void showReports() {
        System.out.println("\n--- Reports ---");
        System.out.println("  1. Full Inventory Report");
        System.out.println("  2. Category-wise Summary");
        System.out.println("  3. Export Report to CSV");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1": reportService.printReport();              break;
            case "2": reportService.printCategorySummary();     break;
            case "3": reportService.exportToCSV(REPORT_DIR);   break;
            default:  System.out.println("[WARN] Invalid choice.");
        }
    }

    // Helper: safely read int
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(scanner.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("[WARN] Enter a valid integer."); }
        }
    }

    // Helper: safely read double
    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Double.parseDouble(scanner.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("[WARN] Enter a valid number."); }
        }
    }
}
