package com.inventory.util;

import com.inventory.model.Product;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CSVExporter {

    public static String exportReport(List<Product> products, String reportDir) {
        // Create reports directory if not exists
        File dir = new File(reportDir);
        if (!dir.exists()) dir.mkdirs();

        // Filename with timestamp
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filePath = reportDir + File.separator + "report_" + timestamp + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Header
            writer.write("ID,Name,Category,Quantity,Price,Total Value");
            writer.newLine();

            double grandTotal = 0;
            for (Product p : products) {
                double totalValue = p.getQuantity() * p.getPrice();
                grandTotal += totalValue;
                writer.write(String.format("%s,%s,%s,%d,%.2f,%.2f",
                        p.getId(), p.getName(), p.getCategory(),
                        p.getQuantity(), p.getPrice(), totalValue));
                writer.newLine();
            }

            // Summary row
            writer.newLine();
            writer.write(",,,,Grand Total,");
            writer.write(String.format("%.2f", grandTotal));
            writer.newLine();

            System.out.println("[INFO] Report exported to: " + filePath);
        } catch (IOException e) {
            System.out.println("[ERROR] Could not export report: " + e.getMessage());
        }

        return filePath;
    }
}
