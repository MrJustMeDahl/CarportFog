package dat.backend.model.entities;

/**
 * This class represents a line on an itemlist.
 * @author MrJustMeDahl
 */
public class ItemListMaterial {

    private Material material;
    private int amount;
    private String message;
    private String partFor;
    private int actualLength;

    public ItemListMaterial(Material material, int amount, String message, String partFor, int actualLength){
        this.material = material;
        this.amount = amount;
        this.message = message;
        this.partFor = partFor;
        this.actualLength = actualLength;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }

    public String getPartFor() {
        return partFor;
    }

    public int getActualLength() {
        return actualLength;
    }
}
