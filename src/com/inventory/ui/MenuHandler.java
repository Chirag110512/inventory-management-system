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
    private static final String REPORT_DIR="reports";
    public MenuHandler(ProductService ps,InventoryService is,ReportService rs){
        this.productService=ps;this.inventoryService=is;this.reportService=rs;
        this.scanner=new Scanner(System.in);
    }
    public void start(){
        System.out.println("\n==========================================");
        System.out.println("   INVENTORY MANAGEMENT SYSTEM v1.0      ");
        System.out.println("==========================================");
        boolean running=true;
        while(running){
            printMenu();
            System.out.print("Enter choice: ");
            String choice=scanner.nextLine().trim();
            switch(choice){
                case "1": viewAllProducts();break;
                case "2": addProduct();break;
                case "3": updateProduct();break;
                case "4": deleteProduct();break;
                case "5": searchProduct();break;
                case "6": checkLowStock();break;
                case "7": showReports();break;
                case "8": running=false;System.out.println("\n[INFO] Saving and exiting. Goodbye!");break;
                default: System.out.println("[WARN] Invalid choice.");
            }
        }
    }
    private void printMenu(){
        System.out.println("\n------------------------------------------");
        System.out.println("  1. View All Products");
        System.out.println("  2. Add Product");
        System.out.println("  3. Update Product");
        System.out.println("  4. Delete Product");
        System.out.println("  5. Search Product");
        System.out.println("  6. Low Stock Alert");
        System.out.println("  7. Reports and Export");
        System.out.println("  8. Exit");
        System.out.println("------------------------------------------");
    }
    private void viewAllProducts(){
        List<Product> all=productService.getAllProducts();
        if(all.isEmpty()){System.out.println("[INFO] No products found.");return;}
        ReportService.printTableHeader();
        for(Product p:all)System.out.println(p);
        ReportService.printTableFooter();
        System.out.println("Total: "+all.size()+" product(s)");
    }
    private void addProduct(){
        System.out.println("\n--- Add New Product ---");
        System.out.print("Name     : ");String name=scanner.nextLine().trim();
        System.out.print("Category : ");String category=scanner.nextLine().trim();
        int qty=readInt("Quantity : ");
        double price=readDouble("Price    : ");
        productService.addProduct(name,category,qty,price);
    }
    private void updateProduct(){
        System.out.println("\n--- Update Product ---");
        System.out.print("Enter Product ID: ");String id=scanner.nextLine().trim();
        Product e=productService.getById(id);
        if(e==null){System.out.println("[ERROR] Not found: "+id);return;}
        System.out.println("Current: "+e);
        System.out.print("New Name ["+e.getName()+"]: ");String name=scanner.nextLine().trim();
        System.out.print("New Category ["+e.getCategory()+"]: ");String category=scanner.nextLine().trim();
        int qty=readInt("New Quantity ["+e.getQuantity()+"]: ");
        double price=readDouble("New Price ["+e.getPrice()+"]: ");
        if(name.isEmpty())name=e.getName();
        if(category.isEmpty())category=e.getCategory();
        productService.updateProduct(id,name,category,qty,price);
    }
    private void deleteProduct(){
        System.out.println("\n--- Delete Product ---");
        System.out.print("Enter Product ID: ");String id=scanner.nextLine().trim();
        System.out.print("Are you sure? (y/n): ");String confirm=scanner.nextLine().trim();
        if(confirm.equalsIgnoreCase("y"))productService.deleteProduct(id);
        else System.out.println("[INFO] Delete cancelled.");
    }
    private void searchProduct(){
        System.out.println("\n--- Search ---");
        System.out.println("  1. By Name");System.out.println("  2. By Category");
        System.out.print("Choice: ");String choice=scanner.nextLine().trim();
        List<Product> results;
        if(choice.equals("1")){System.out.print("Keyword: ");results=productService.searchByName(scanner.nextLine().trim());}
        else if(choice.equals("2")){System.out.print("Category: ");results=productService.searchByCategory(scanner.nextLine().trim());}
        else{System.out.println("[WARN] Invalid.");return;}
        if(results.isEmpty())System.out.println("[INFO] No results found.");
        else{ReportService.printTableHeader();for(Product p:results)System.out.println(p);ReportService.printTableFooter();}
    }
    private void checkLowStock(){
        int t=readInt("Enter threshold: ");
        List<Product> low=inventoryService.getLowStockProducts(t);
        if(low.isEmpty())System.out.println("[INFO] All products sufficiently stocked.");
        else{System.out.println("[ALERT] "+low.size()+" product(s) below "+t);ReportService.printTableHeader();for(Product p:low)System.out.println(p);ReportService.printTableFooter();}
    }
    private void showReports(){
        System.out.println("\n--- Reports ---");
        System.out.println("  1. Full Report");System.out.println("  2. Category Summary");System.out.println("  3. Export to CSV");
        System.out.print("Choice: ");String choice=scanner.nextLine().trim();
        switch(choice){
            case "1":reportService.printReport();break;
            case "2":reportService.printCategorySummary();break;
            case "3":reportService.exportToCSV(REPORT_DIR);break;
            default:System.out.println("[WARN] Invalid.");
        }
    }
    private int readInt(String prompt){
        while(true){System.out.print(prompt);try{return Integer.parseInt(scanner.nextLine().trim());}catch(NumberFormatException e){System.out.println("[WARN] Enter a valid integer.");}}
    }
    private double readDouble(String prompt){
        while(true){System.out.print(prompt);try{return Double.parseDouble(scanner.nextLine().trim());}catch(NumberFormatException e){System.out.println("[WARN] Enter a valid number.");}}
    }
}
