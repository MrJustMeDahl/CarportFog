package dat.backend.model.entities;


import java.util.Map;

public class Carport {

    private double price;
    private double indicativePrice;
    private Map<Material, Integer> materials;

    public Carport(Map<Material, Integer> materials){
        this.materials = materials;
        this.price = calculatePrice();
        this.indicativePrice = calculateIndicativePrice();
    }

    public Carport(Map<Material, Integer> materials, double price, double indicativePrice){
        this.materials = materials;
        this.price = price;
        this.indicativePrice = indicativePrice;
    }

    private double calculatePrice(){
        double price = 0;
        for(Map.Entry<Material, Integer> m: materials.entrySet()){
            price += m.getKey().getPrice()*m.getValue();
        }
        return price;
    }

    private double calculateIndicativePrice(){
        double indicativePrice = (100*price)/61;
        return indicativePrice;
    }
}
