package dat.backend.model.entities;

public abstract class Material {

    protected int materialID;
    protected String description;
    protected String type;
    protected String function;
    protected double price;
    protected int length;

    protected Material(int materialID, String description, String type, String function, double price, int length){
        this.materialID = materialID;
        this.description = description;
        this.type = type;
        this.function = function;
        this.price = price;
        this.length = length;
    }

    public abstract double getPrice();
}
