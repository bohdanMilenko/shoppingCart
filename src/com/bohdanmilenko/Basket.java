package com.bohdanmilenko;

import java.util.*;

public class Basket {
    private final String name;
    private final Map<StockItem, Integer> list;

    public Basket(String name) {
        this.name = name;
        this.list = new HashMap<>();
    }


    public int addToBasket(StockItem item, int quantity) {
        if ((item != null) && (quantity > 0)) {
            int inBasket = list.getOrDefault(item, 0);
            list.put(item, inBasket + quantity);
            return inBasket;
        }
        return 0;
    }

    public int removeFromBasket(StockItem item, int quantity){

        if(item != null && quantity>0 && item.getReservedQuantity()>0){
            int actualQuantity=item.getReservedQuantity()-quantity;
            list.put(item,actualQuantity);
            return actualQuantity;
        }
        return 0;
    }

    public void checkout(){
        if(this.list.size()>0){
            System.out.println("Checkout complete, you purchased: \n");
            double checkoutPrice=0;
            for( Map.Entry<StockItem,Integer> s : list.entrySet()){
                checkoutPrice += s.getKey().getPrice()*s.getKey().getReservedQuantity();
                System.out.println("\t " + s.getKey().getName()+": $" + s.getKey().getPrice()*s.getKey().getReservedQuantity() );
                int qntty = s.getKey().getReservedQuantity();
                s.getKey().cleanBasket(qntty);
            }
            list.clear();
            System.out.println("Your were charged $" + String.format("%.2f", checkoutPrice));
        }
    }

    public Map<StockItem, Integer> Items() {
        return Collections.unmodifiableMap(list);
    }

    @Override
    public String toString() {
        String s = "\nShopping basket " + name + " contains " + list.size() + ((list.size() == 1) ? " item" : " items") + "\n";
        double totalCost = 0.0;
        for (Map.Entry<StockItem, Integer> item : list.entrySet()) {
            s = s + item.getKey() + ". " + item.getValue() + " units reserved\n";
            totalCost += item.getKey().getPrice() * item.getValue();
        }
        return s + "Total cost " +String.format("%.2f", totalCost);
    }
}
