package com.inventory;
import com.inventory.model.Product;
import com.inventory.service.InventoryService;
import com.inventory.service.ProductService;
import com.inventory.service.ReportService;
import com.inventory.ui.MenuHandler;
import com.inventory.util.FileHandler;
import java.util.List;
public class Main {
    private static final String DATA_FILE="data/products.csv";
    public static void main(String[] args){
        List<Product> products=FileHandler.loadProducts(DATA_FILE);
        ProductService productService=new ProductService(products);
        InventoryService inventoryService=new InventoryService(products);
        ReportService reportService=new ReportService(products,inventoryService);
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            FileHandler.saveProducts(products,DATA_FILE);
            System.out.println("[INFO] Data saved to "+DATA_FILE);
        }));
        new MenuHandler(productService,inventoryService,reportService).start();
    }
}
