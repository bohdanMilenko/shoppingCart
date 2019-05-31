package com.bohdanmilenko;


public class StockItem implements Comparable<StockItem> {
    private final String name;
    private double price;
    private int availableInStock = 0;
    private int reservedQuantity;

    public StockItem(String name, double price) {
        this.name = name;
        this.price = price;
        this.availableInStock = 0;// or here (but you wouldn't normally do both).
        this.reservedQuantity=0;
    }

    public StockItem(String name, double price, int availableInStock) {
        this.name = name;
        this.price = price;
        this.availableInStock = availableInStock;
        this.reservedQuantity=0;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public int getAvailableInStock() {
        return availableInStock;
    }

    public void setPrice(double price) {
        if(price > 0.0) {
            this.price = price;
        }
    }

    public void cleanBasket(int quantity){
        this.reservedQuantity-=quantity;
    }

    public void adjustStock(int quantity) {
        int newQuantity = this.availableInStock + quantity;
        if(newQuantity >=0) {
            this.availableInStock = newQuantity;
        }
    }

    public void reserveStock(int quantity){
        if(this.availableInStock >=0 && availableInStock>=quantity){
            this.reservedQuantity -= quantity;
            this.availableInStock +=quantity;
        }
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("Entering StockItem.equals");
        if(obj == this) {
            return true;
        }

        if((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }

        String objName = ((StockItem) obj).getName();
        return this.name.equals(objName);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + 31;
    }

    @Override
    public int compareTo(StockItem o) {
       // System.out.println("Entering StockItem.compareTo");
        if(this == o) {
            return 0;
        }

        if(o != null) {
            return this.name.compareTo(o.getName());
        }

        throw new NullPointerException();
    }

    @Override
    public String toString() {
        return this.name + " : price " + this.price;
    }
}
