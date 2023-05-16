package dat.backend.model.entities;

public class ItemListMaterial {

    private Material material;
    private int amount;
    private String message;

    public ItemListMaterial(Material material, int amount, String message){
        this.material = material;
        this.amount = amount;
        this.message = message;
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
}
