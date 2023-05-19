package dat.backend.model.entities;

public class UnspecifiedMaterial extends Material{

    private double actualPrice;

    public UnspecifiedMaterial(int materialID, int materialVariantID, String description, String type, String function, double price, int length, double actualPrice) {
        super(materialID, materialVariantID, description, type, function, price, length);
        this.actualPrice = actualPrice;
    }

    @Override
    public double calculateActualPrice() {
        return actualPrice;
    }
}
