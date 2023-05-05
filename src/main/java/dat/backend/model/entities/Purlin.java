package dat.backend.model.entities;

public class Purlin extends Material{

    public Purlin(int materialID, String description, String type, String function, double price, int length){
        super(materialID, description, type, function, price, length);
    }

    @Override
    public double getPrice() {
        return price * (length / 100);
    }

}
