package com.inventory.service;
import com.inventory.model.Product;
import java.util.*;
public class InventoryService {
    private List<Product> products;
    public InventoryService(List<Product> products){this.products=products;}
    public List<Product> getLowStockProducts(int threshold){
        List<Product> low=new ArrayList<>();
        for(Product p:products)if(p.getQuantity()<=threshold)low.add(p);
        return low;
    }
    public double getTotalStockValue(){
        double total=0;for(Product p:products)total+=p.getQuantity()*p.getPrice();return total;
    }
    public Map<String,Integer> getStockSummaryByCategory(){
        Map<String,Integer> summary=new TreeMap<>();
        for(Product p:products)summary.merge(p.getCategory(),p.getQuantity(),Integer::sum);
        return summary;
    }
    public int getTotalUniqueProducts(){return products.size();}
}
