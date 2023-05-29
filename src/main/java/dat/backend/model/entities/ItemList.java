package dat.backend.model.entities;

import dat.backend.model.exceptions.NoMaterialFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents an itemlist for an order.
 * It contains list of ItemListMaterial objects which represents each line of an itemlist.
 * Each instance of this class has an instance of the material calculator class that is used to calculate, which materials and sizes are needed for the order the itemlist is connected to.
 *
 * @author MrJustMeDahl
 */
public class ItemList {

    private List<ItemListMaterial> materials;
    private final MaterialCalculator materialCalculator;

    /**
     * This constructor is used when creating a new order or editing an existing order, if the order doesn't have a shed.
     * When this constructor is used it will use the MaterialCalculator to create content for the itemlist.
     *
     * @param length     length of the carport in centimeter
     * @param width      width of the carport in centimeter
     * @param minHeight  minimum height for the carport in centimeter
     * @param hasShed    A boolean which is used to check for a shed
     * @param shedLength Length of the shed
     * @param shedWidth  Width of the shed
     * @param allPosts   list of all posts that can be used for calculations
     * @param allPurlins list of all purlins that can be used for calculations
     * @param allRafters list of all Rafters that can be used for calculations
     * @author MrJustMeDahl
     */
    public ItemList(int length, int width, int minHeight, boolean hasShed, int shedLength, int shedWidth, List<Post> allPosts, List<Purlin> allPurlins, List<Rafter> allRafters, List<Roof> allRoofs, List<Sheathing> allSheathings) throws NoMaterialFoundException {

        this.materialCalculator = new MaterialCalculator(length, width, minHeight, hasShed, shedLength, shedWidth);
        materials = new ArrayList<>();
        generateItemListContent(allPosts, allPurlins, allRafters, allRoofs, allSheathings);
    }

    /**
     * This constructor is used when creating an object of an already existing order.
     * It will not generate content for the itemlist, and it is therefore required you use the addMaterialToItemList() method to fill out the content.
     *
     * @param length     length of the carport in centimeter
     * @param width      width of the carport in centimeter
     * @param minHeight  minimum height of the carport in centimeter
     * @param hasShed    A boolean which is used to check for a shed
     * @param shedLength Length of the shed
     * @param shedWidth  Width of the shed
     * @author MrJustMeDahl
     */
    public ItemList(int length, int width, int minHeight, boolean hasShed, int shedLength, int shedWidth) {
        this.materialCalculator = new MaterialCalculator(length, width, minHeight, hasShed, shedLength, shedWidth);
        materials = new ArrayList<>();
    }

    public List<ItemListMaterial> getMaterials() {
        return materials;
    }

    /**
     * This method is used to calculate to content of the itemlist.
     * It requires a list of all the different material types it can choose from.
     *
     * @param allPosts       List of posts
     * @param allPurlins    List of Purlins
     * @param allRafters    List of Rafters
     * @param allRoofs      list of the roof
     * @param allSheathings list of the sheathings
     * @author MrJustMeDahl
     */
    public void generateItemListContent(List<Post> allPosts, List<Purlin> allPurlins, List<Rafter> allRafters, List<Roof> allRoofs, List<Sheathing> allSheathings) throws NoMaterialFoundException {
        materials = new ArrayList<>();
        for (ItemListMaterial i : materialCalculator.calculatePosts(allPosts)) {
            materials.add(i);
        }
        for (ItemListMaterial i : materialCalculator.calculatePurlins(allPurlins)) {
            materials.add(i);
        }
        materials.add(materialCalculator.calculateRafters(allRafters));
        for (ItemListMaterial i : materialCalculator.calculateRoofs(allRoofs)) {
            materials.add(i);
        }
        if (materialCalculator.getHasShed()) {
            for (ItemListMaterial i : materialCalculator.calculateSheathings(allSheathings)) {
                materials.add(i);
            }
        }
    }

    /**
     * This method adds a material to the itemList
     *
     * @param material List of materials
     * @author MrJustMeDahl
     */
    public void addMaterialToItemList(ItemListMaterial material) {
        materials.add(material);
    }

    /**
     * This method is used to create the Material map which is needed to create the carport object.
     *
     * @return Map<Material, Integer> where the key is a specific Material and the value is the amount of the material needed for the carport.
     * @MrJustMeDahl
     */
    public Map<Material, Integer> getMaterialsForCarport() {
        Map<Material, Integer> materialMap = new HashMap<>();
        for (ItemListMaterial i : materials) {
            if (i.getPartFor().equals("carport"))
                materialMap.put(i.getMaterial(), i.getAmount());
        }
        return materialMap;
    }

    /**
     * This method is used to create the Material map which is needed to create the shed object.
     *
     * @return Map<Material, Integer> where the key is a specific Material and the value is the amount of the material needed for the shed.
     * @MrJustMeDahl
     */
    public Map<Material, Integer> getMaterialsForShed() {
        Map<Material, Integer> materialMap = new HashMap<>();
        for (ItemListMaterial i : materials) {
            if (i.getPartFor().equals("shed")) {
                materialMap.put(i.getMaterial(), i.getAmount());
            }
        }
        return materialMap;
    }

}
