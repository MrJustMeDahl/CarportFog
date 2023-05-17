package dat.backend.entities;

import dat.backend.model.entities.*;
import dat.backend.model.exceptions.NoMaterialFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ItemListTest {

    private ItemList itemList;
    private List<Post> posts;
    private List<Purlin> purlins;
    private List<Rafter> rafters;

    @BeforeEach
    void setup(){
        itemList = new ItemList(780, 600, 300, true);
        posts = new ArrayList<>();
        purlins = new ArrayList<>();
        rafters = new ArrayList<>();
        posts.add(new Post(1, 1, "97x97mm. trykimp.", "træ", "stolpe", 50, 420));
        purlins.add(new Purlin(2, 2, "45x195mm. spærtræ", "træ", "rem", 45, 600));
        purlins.add(new Purlin(2, 3, "45x195mm. spærtræ", "træ", "rem", 45, 400));
        rafters.add(new Rafter(3, 4, "45x195mm. spærtræ", "træ", "rem", 45, 600));
    }

    @Test
    void generateItemListContent() throws NoMaterialFoundException{
        List<Post> posts = new ArrayList<>();
        List<Purlin> purlins = new ArrayList<>();
        List<Rafter> rafters = new ArrayList<>();
        List<Purlin> finalPurlins = purlins;
        List<Post> finalPosts = posts;
        List<Rafter> finalRafters = rafters;
        assertThrows(NoMaterialFoundException.class, () -> itemList.generateItemListContent(finalPosts, finalPurlins, finalRafters));
        posts = this.posts;
        purlins = this.purlins;
        rafters = this.rafters;
        itemList.generateItemListContent(posts, purlins, rafters);
        assertEquals(5, itemList.getMaterials().size());
        assertEquals(11, itemList.getMaterials().get(0).getAmount() + itemList.getMaterials().get(1).getAmount());
    }

    @Test
    void getMaterialsForCarport(){
        itemList.addMaterialToItemList(new ItemListMaterial(posts.get(0), 8, "Stolpe graves 90 cm ned i jorden", "carport"));
        itemList.addMaterialToItemList(new ItemListMaterial(posts.get(0), 3, "Stolpe graves 90 cm ned i jorden", "shed"));
        itemList.addMaterialToItemList(new ItemListMaterial(purlins.get(1), 2, "Remme i sider, sadles ned i stolper", "carport"));
        itemList.addMaterialToItemList(new ItemListMaterial(purlins.get(0), 2, "Remme i sider, sadles ned i stolper", "carport"));
        itemList.addMaterialToItemList(new ItemListMaterial(rafters.get(0), 8, "Spær monteres på rem", "carport"));
        Map<Material, Integer> carportMaterials = itemList.getMaterialsForCarport();
        assertEquals(4, carportMaterials.size());
        int totalAmount = 0;
        for(Map.Entry<Material, Integer> entry: carportMaterials.entrySet()){
            totalAmount += entry.getValue();
        }
        assertEquals(20, totalAmount);
    }

    @Test
    void getMaterialsForShed(){
        itemList.addMaterialToItemList(new ItemListMaterial(posts.get(0), 8, "Stolpe graves 90 cm ned i jorden", "carport"));
        itemList.addMaterialToItemList(new ItemListMaterial(posts.get(0), 3, "Stolpe graves 90 cm ned i jorden", "shed"));
        itemList.addMaterialToItemList(new ItemListMaterial(purlins.get(1), 2, "Remme i sider, sadles ned i stolper", "carport"));
        itemList.addMaterialToItemList(new ItemListMaterial(purlins.get(0), 2, "Remme i sider, sadles ned i stolper", "carport"));
        itemList.addMaterialToItemList(new ItemListMaterial(rafters.get(0), 8, "Spær monteres på rem", "carport"));
        Map<Material, Integer> shedMaterials = itemList.getMaterialsForShed();
        assertEquals(1, shedMaterials.size());
        int totalAmount = 0;
        for(Map.Entry<Material, Integer> entry: shedMaterials.entrySet()){
            totalAmount += entry.getValue();
        }
        assertEquals(3, totalAmount);
    }

}
