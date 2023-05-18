package dat.backend.model.entities;

import java.util.Map;

public class Shed {
    private double price;
    private double indicativePrice;
    private int width;
    private int length;
    private int minHeight;
    private Map<Material, Integer> materials;

    public Shed(Map<Material, Integer> materials, int width, int length, int minHeight){
        this.materials = materials;
        this.price = calculatePrice();
        this.indicativePrice = calculateIndicativePrice();
        this.width = width;
        this.length = length;
        this.minHeight = minHeight;
    }


    public double calculatePrice(){
        double price = 0;
        for(Map.Entry<Material, Integer> m: materials.entrySet()){
            price += m.getKey().calculateActualPrice()*m.getValue();
        }
        this.price = Math.ceil(price);
        return this.price;
    }

    /**
     * Calculates a suggested sales price based on the price of all the materials, with a GM of 39%.
     * @return Suggested sales price.
     * @author MrJustMeDahl
     */
    public double calculateIndicativePrice(){
        double indicativePrice = ((100*price)/61)*1.25;
        this.indicativePrice = Math.ceil(indicativePrice);
        return this.indicativePrice;
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

    public void setMaterials(Map<Material, Integer> materials) {
        this.materials = materials;
    }

}
