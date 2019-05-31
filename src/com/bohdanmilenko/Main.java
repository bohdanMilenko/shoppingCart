package com.bohdanmilenko;

import java.util.Map;

public class Main {
    private static StockList stockList = new StockList();

    public static void main(String[] args) {
	    StockItem temp = new StockItem("bread", 0.86, 100);
        stockList.addStock(temp);

        temp = new StockItem("cake", 1.10, 7);
        stockList.addStock(temp);

        temp = new StockItem("car", 12.50, 2);
        stockList.addStock(temp);

        temp = new StockItem("chair", 62.0, 10);
        stockList.addStock(temp);

        temp = new StockItem("cup", 0.50, 200);
        stockList.addStock(temp);
        temp = new StockItem("cup", 0.45, 7);
        stockList.addStock(temp);

        temp = new StockItem("door", 72.95, 4);
        stockList.addStock(temp);

        temp = new StockItem("juice", 2.50, 36);
        stockList.addStock(temp);

        temp = new StockItem("phone", 96.99, 35);
        stockList.addStock(temp);

        temp = new StockItem("towel", 2.40, 80);
        stockList.addStock(temp);

        temp = new StockItem("vase", 8.76, 40);
        stockList.addStock(temp);

        System.out.println(stockList);

        for(String s: stockList.Items().keySet()) {
            System.out.println(s);
        }

        Basket jamesCart = new Basket("James");
        addToBasket(jamesCart, "car", 1);
        System.out.println(jamesCart);

        addToBasket(jamesCart, "car", 1);
        System.out.println(jamesCart);

        if(addToBasket(jamesCart, "car", 1) != 1) {
            System.out.println("There are no more cars in stock");
        }

        addToBasket(jamesCart, "spanner", 5);
        System.out.println(jamesCart);

        addToBasket(jamesCart, "juice", 4);
        addToBasket(jamesCart, "cup", 12);
        addToBasket(jamesCart, "bread", 1);
        addToBasket(jamesCart,"chair",2);
        System.out.println(jamesCart);

        System.out.println(stockList);

        unreserveQntty(jamesCart,"cup",8);
        System.out.println(stockList);
        unreserveQntty(jamesCart,"cup",8);
        unreserveQntty(jamesCart,"door",8);
       
	for(Map.Entry<String, Double> price: stockList.PriceList().entrySet()) {
            System.out.println(price.getKey() + " costs " + price.getValue());
        }
        System.out.println("========================");
        jamesCart.checkout();

        Basket bohdansCart = new Basket("Bohdan");
        addToBasket(bohdansCart,"door", 4);
        System.out.println(jamesCart);
        System.out.println();
        System.out.println(bohdansCart);
        System.out.println();
        System.out.println(stockList);
    }


    public static int addToBasket(Basket basket, String item, int quantity) {
        //retrieve the item from stock list
        StockItem stockItem = stockList.get(item);
        if(stockItem == null) {
            System.out.println("We don't sell " + item);
            return 0;
        }
        if(stockList.reserveStock(item, quantity) != 0) {
            basket.addToBasket(stockItem, quantity);
            return quantity;
        }
        return 0;
    }

    public static int unreserveQntty(Basket basket, String item, int quantity){
        StockItem temp = stockList.get(item);
        int reserved = temp.getReservedQuantity();
        if(temp!=null && reserved>0 && reserved>=quantity){
            basket.removeFromBasket(temp,quantity);
            stockList.unreserveStock(item, quantity);
            System.out.println("You removed " + quantity + " units of " + item);
            return quantity;
        }
        if(basket.Items().get(temp)==null) {
            System.out.println("There is no such item in your basket");
            return 0;
        }else if(quantity > reserved){
            System.out.println("You cannot discard more items than you have in the basket.");
            return 0;
        }
        return 0;
    }
}
