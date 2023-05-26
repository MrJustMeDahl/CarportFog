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

    /**
     * Objects of this class is instantiated only from the MaterialCalculator class.
     * @param material The specific material chosen that accommodates a specific orders measurements.
     * @param amount The amount of the specific material.
     * @param message A note that is to be printed to the user, consisting of specifying instructions regarding the material chosen for a specific function.
     * @param partFor Indicates if the material is to be used for the carport or the shed.
     * @param actualLength This is the length the material needs to be cut to, and not the length of the material.
     * @author MrJustMeDahl
     */
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

    public String getMaterialFunction(){
        if(material instanceof Post){
            return "stolpe";
        } else if(material instanceof  Purlin){
            return "rem";
        } else if(material instanceof Rafter){
            return "spær";
        } else if(material instanceof Roof){
            return "tag";
        } else if(material instanceof Sheathing){
            return "bræddebeklædning";
        }
        return null;
    }

    public int getActualLength() {
        return actualLength;
    }
}
