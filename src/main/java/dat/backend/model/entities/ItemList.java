package dat.backend.model.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemList {

    private List<ItemListMaterial> materials;
    private final MaterialCalculator materialCalculator;

    public ItemList(int length, int width, int minHeight, List<Post> allPosts, List<Purlin> allPurlins, List<Rafter> allRafters){
        this.materialCalculator = new MaterialCalculator(length, width, minHeight);
        materials = new ArrayList<>();
        generateItemListContent(allPosts, allPurlins, allRafters);
    }

    public ItemList(int length, int width, int minHeight){
        this.materialCalculator = new MaterialCalculator(length, width, minHeight);
        materials = new ArrayList<>();
    }

    public List<ItemListMaterial> getMaterials() {
        return materials;
    }

    public ItemList generateItemListContent(List<Post> allPosts, List<Purlin> allPurlins, List<Rafter> allRafters){
        materials.add(materialCalculator.calculatePosts(allPosts));
        for(ItemListMaterial i: materialCalculator.calculatePurlins(allPurlins)){
            materials.add(i);
        }
        materials.add(materialCalculator.calculateRafters(allRafters));
        return this;
    }

    public void addMaterialToItemList(ItemListMaterial material){
        materials.add(material);
    }

    public Map<Material, Integer> getMaterialsForCarport(){
        Map<Material, Integer> materialMap = new HashMap<>();
        for(ItemListMaterial i: materials){
            materialMap.put(i.getMaterial(), i.getAmount());
        }
        return materialMap;
    }

}
