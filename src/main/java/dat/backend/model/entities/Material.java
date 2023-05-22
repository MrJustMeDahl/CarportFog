package dat.backend.model.entities;

/**
 * This class contains data regarding all the different kind of building materials and functions as a recipe for what defines a material including methods which all material types must implement.
 * @author MrJustMeDahl
 */
public abstract class Material {

    protected int materialID;
    protected int materialVariantID;
     String description;
    protected String type;
    protected String function;
    protected double price;
    int length;

    /**
     * This constructor represents the minimum amount of data needed to create any material
     * @param materialID Material variant id.
     * @param description Description/name of the material.
     * @param type What the material is made of.
     * @param function What is the material supposed to be used for.
     * @param price What is the cost price of the material.
     * @param length The length of the material.
     * @author MrJustMeDahl
     */
    protected Material(int materialID, int materialVariantID, String description, String type, String function, double price, int length){
        this.materialID = materialID;
        this.materialVariantID = materialVariantID;
        this.description = description;
        this.type = type;
        this.function = function;
        this.price = price;
        this.length = length;
    }

    /**
     * Materials calculate their price in different ways and therefore needs to be specific for each class.
     * @return Cost price of the material
     * @author MrJustMeDahl
     */
    public double getPrice(){
        return price;
    }

    public int getMaterialID() {
        return materialID;
    }

    public int getMaterialVariantID(){
        return materialVariantID;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getFunction() {
        return function;
    }

    public int getLength() {
        return length;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public abstract double calculateActualPrice();
}
