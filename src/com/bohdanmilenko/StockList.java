package com.bohdanmilenko;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class StockList {
    private final Map<String, StockItem> list;

    public StockList() {
        this.list = new LinkedHashMap<>();
    }

    public int addStock(StockItem item) {
        if(item != null) {
            // check if already have quantities of this item
            StockItem inStock = list.getOrDefault(item.getName(), item);
            // If there are already stocks on this item, adjust the quantity
            if(inStock != item) {
                item.adjustStock(inStock.getAvailableInStock());
            }

            list.put(item.getName(), item);
            return item.getAvailableInStock();
        }
        return 0;
    }

    public int reserveStock(String item, int quantity){
        StockItem inStock = list.getOrDefault(item, null);

        if((inStock != null) && (inStock.getAvailableInStock() >= quantity) && (quantity >0)) {
            inStock.reserveStock(-quantity);
            return quantity;
        }
        return 0;
    }

    public int unreserveStock(String item, int quantity){
        StockItem temp = list.getOrDefault(item,null);
        if(temp != null && temp.getReservedQuantity()>=quantity){
            temp.reserveStock(quantity);
            return quantity;
        }
        return 0;
    }

    public StockItem get(String key) {
        return list.get(key);
    }

    public Map<String, Double> PriceList() {
        Map<String, Double> prices = new LinkedHashMap<>();
        for(Map.Entry<String, StockItem> item : list.entrySet()) {
            prices.put(item.getKey(), item.getValue().getPrice());
        }
        return Collections.unmodifiableMap(prices);
    }

    public Map<String, StockItem> Items() {
        return Collections.unmodifiableMap(list);
    }

    @Override
    public String toString() {
        String s = "\nStock List\n";
        double totalCost = 0.0;
        double totalReserved = 0.0;
        for (Map.Entry<String, StockItem> item : list.entrySet()) {
            StockItem stockItem = item.getValue();

            double itemValue = stockItem.getPrice() * stockItem.getAvailableInStock();

            s = s + stockItem + ". There are " + stockItem.getAvailableInStock() + " in stock and " + stockItem.getReservedQuantity()+" are reserved: ";
            s = s + String.format("%.2f",itemValue) + "\n";
            totalCost += itemValue;
            totalReserved +=stockItem.getPrice()*stockItem.getReservedQuantity();
        }

        return s + "Total stock value $" +String.format("%.2f", totalCost)+"\nTotal costs of reserved units: $" + String.format("%.2f",totalReserved);
    }
}
