package com.inventory.service;
import com.inventory.model.Product;
import com.inventory.util.CSVExporter;
import java.util.*;
public class ReportService {
    private List<Product> products;
    private InventoryService inventoryService;
    public ReportService(List<Product> products,InventoryService inventoryService){
        this.products=products;this.inventoryService=inventoryService;
    }
    public void printReport(){
        System.out.println("\n========================================");
        System.out.println("         INVENTORY REPORT               ");
        System.out.println("========================================");
        printTableHeader();
        for(Product p:products)System.out.println(p.toString());
        printTableFooter();
        System.out.printf("%n Total Products  : %d%n",inventoryService.getTotalUniqueProducts());
        System.out.printf(" Total Stock Value: Rs. %.2f%n",inventoryService.getTotalStockValue());
        System.out.println("========================================\n");
    }
    public void printCategorySummary(){
        Map<String,Integer> summary=inventoryService.getStockSummaryByCategory();
        System.out.println("\n---- Category-wise Stock Summary ----");
        System.out.printf("%-20s | %s%n","Category","Total Qty");
        System.out.println("------------------------------");
        for(Map.Entry<String,Integer> e:summary.entrySet())System.out.printf("%-20s | %d%n",e.getKey(),e.getValue());
        System.out.println();
    }
    public String exportToCSV(String reportDir){return CSVExporter.exportReport(products,reportDir);}
    public static void printTableHeader(){
        System.out.println("+----------+----------------------+--------------+----------+---------------+");
        System.out.println("| ID       | Name                 | Category     | Quantity | Price         |");
        System.out.println("+----------+----------------------+--------------+----------+---------------+");
    }
    public static void printTableFooter(){
        System.out.println("+----------+----------------------+--------------+----------+---------------+");
    }
}
