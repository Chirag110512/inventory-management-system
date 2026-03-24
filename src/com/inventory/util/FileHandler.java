package com.inventory.util;
import com.inventory.model.Product;
import java.io.*;
import java.util.*;
public class FileHandler {
    public static List<Product> loadProducts(String filePath){
        List<Product> products=new ArrayList<>();
        File file=new File(filePath);
        if(!file.exists()){System.out.println("[INFO] No existing data file found. Starting fresh.");return products;}
        try(BufferedReader reader=new BufferedReader(new FileReader(file))){
            String line;boolean firstLine=true;
            while((line=reader.readLine())!=null){
                if(firstLine){firstLine=false;continue;}
                line=line.trim();if(line.isEmpty())continue;
                Product p=Product.fromCSV(line);if(p!=null)products.add(p);
            }
            System.out.println("[INFO] Loaded "+products.size()+" products from "+filePath);
        }catch(IOException e){System.out.println("[ERROR] "+e.getMessage());}
        return products;
    }
    public static void saveProducts(List<Product> products,String filePath){
        File file=new File(filePath);
        if(file.getParentFile()!=null)file.getParentFile().mkdirs();
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(file))){
            writer.write("id,name,category,quantity,price");writer.newLine();
            for(Product p:products){writer.write(p.toCSV());writer.newLine();}
        }catch(IOException e){System.out.println("[ERROR] "+e.getMessage());}
    }
}
