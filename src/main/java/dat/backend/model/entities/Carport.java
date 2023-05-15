package dat.backend.model.entities;

import java.util.Map;

/**
 * This class contains all the data of which a carport persists and has methods help calculate price and sales price.
 * @author MrJustMeDahl
 */
public class Carport {

    private double price;
    private double indicativePrice;
    private Map<Material, Integer> materials;
    private int width;
    private int length;
    private int minHeight;

    /**
     * This constructor is used when creating a new order - meaning you don't know the price yet, it will calculate the price of all materials for you and give you a suggested indicative price.
     * @param materials Key is the material and value is number of that material used for this specific Carport
     * @author MrJustMeDahl
     */
    public Carport(Map<Material, Integer> materials, int width, int length, int minHeight){
        this.materials = materials;
        this.price = calculatePrice();
        this.indicativePrice = calculateIndicativePrice();
        this.width = width;
        this.length = length;
        this.minHeight = minHeight;
    }


    /**
     * This constructor is used when loading in existing orders from the database.
     * @param materials Key is the material and value is number of that material used for this specific Carport
     * @param price The price of all the materials used for the carport.
     * @param indicativePrice Sales price.
     * @author MrJustMeDahl
     */
    public Carport(Map<Material, Integer> materials, double price, double indicativePrice, int width, int length, int minHeight){
        this.materials = materials;
        this.price = price;
        this.indicativePrice = indicativePrice;
        this.width = width;
        this.length = length;
        this.minHeight = minHeight;
    }


    /**
     * Calculates the combined price of all materials in the map.
     * @return combined price of all the materials.
     * @author MrJustMeDahl
     */
    public double calculatePrice(){
        double price = 0;
        for(Map.Entry<Material, Integer> m: materials.entrySet()){
            price += m.getKey().calculateActualPrice()*m.getValue();
        }
        return price;
    }

    /**
     * Calculates a suggested sales price based on the price of all the materials, with a GM of 39%.
     * @return Suggested sales price.
     * @author MrJustMeDahl
     */
    public double calculateIndicativePrice(){
        double indicativePrice = ((100*price)/61)*1.25;
        return indicativePrice;
    }

    public double getPrice() {
        return price;
    }

    public double getIndicativePrice() {
        return indicativePrice;
    }

    public Map<Material, Integer> getMaterials() {
        return materials;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setIndicativePrice(double indicativePrice) {
        this.indicativePrice = indicativePrice;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }
}
