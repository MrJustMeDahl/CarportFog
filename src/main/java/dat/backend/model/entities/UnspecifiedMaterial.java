package dat.backend.model.entities;

/**
 * The class extends Material and contains the implementations of how a material with this function behaves.
 * @author MrJustMeDahl
 */
public class UnspecifiedMaterial extends Material{

    private double actualPrice;

    public UnspecifiedMaterial(int materialID, int materialVariantID, String description, String type, String function, double price, int length, double actualPrice) {
        super(materialID, materialVariantID, description, type, function, price, length);
        this.actualPrice = actualPrice;
    }

    /**
     * This method returns the price of the actual price of the material.
     * @return double - Price of the material.
     * @author MrJustMeDahl
     */
    @Override
    public double calculateActualPrice() {
        return actualPrice;
    }
}
