package dat.backend.model.entities;

import java.util.Map;

public class Shed {
    private double price;
    private double indicativePrice;
    private int width;
    private int length;
    private int minHeight;

    public Shed( double price, double indicativePrice, int width, int length, int minHeight){
        this.price = price;
        this.indicativePrice = indicativePrice;
        this.width = width;
        this.length = length;
        this.minHeight = minHeight;
    }

    public double calculateIndicativePrice(){
        double indicativePrice = ((100*price)/61)*1.25;
        return indicativePrice;
    }

}
