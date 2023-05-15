package dat.backend.model.entities;

/**
 * The class extends Material and contains the implementations of how a material with this function behaves.
 * @author MrJustMeDahl
 */
public class Rafter extends Material{

    private double actualPrice;

    public Rafter(int materialID, int materialVariantID, String description, String type, String function, double price, int length){
        super(materialID, materialVariantID, description, type, function, price, length);
        actualPrice = calculateActualPrice();
    }

    /**
     * This method calculates the price of the material, considering it's length and material type.
     * @return double - Price of the material.
     * @author MrJustMeDahl
     */

    public double calculateActualPrice() {
        return price * ((double) length / 100);
    }

    public double getActualPrice() {
        return actualPrice;
    }
}
