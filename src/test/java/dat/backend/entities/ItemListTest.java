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
    private List<Roof> roofs;
    private List<Sheathing> sheathings;

    @BeforeEach
    void setup(){
        itemList = new ItemList(780, 600, 300, true, 300, 600);
        posts = new ArrayList<>();
        purlins = new ArrayList<>();
        rafters = new ArrayList<>();
        roofs = new ArrayList<>();
        sheathings = new ArrayList<>();
        posts.add(new Post(1, 1, "97x97mm. trykimp.", "træ", "stolpe", 50, 420));
        purlins.add(new Purlin(2, 2, "45x195mm. spærtræ", "træ", "rem", 45, 600));
        purlins.add(new Purlin(2, 3, "45x195mm. spærtræ", "træ", "rem", 45, 400));
        rafters.add(new Rafter(3, 4, "45x195mm. spærtræ", "træ", "spær", 45, 600));
        roofs.add(new Roof(4, 5, "bølgeplade sunlux sort", "plastik", "tag", 140, 200));
        sheathings.add(new Sheathing(5, 6, "25x125mm. beklædning høvlet", "træ", "bræddebeklædning", 18, 330));
    }

    @Test
    void generateItemListContent() throws NoMaterialFoundException{
        List<Post> posts = new ArrayList<>();
        List<Purlin> purlins = new ArrayList<>();
        List<Rafter> rafters = new ArrayList<>();
        List<Purlin> finalPurlins = purlins;
        List<Post> finalPosts = posts;
        List<Rafter> finalRafters = rafters;
        assertThrows(NoMaterialFoundException.class, () -> itemList.generateItemListContent(finalPosts, finalPurlins, finalRafters, roofs, sheathings));
        posts = this.posts;
        purlins = this.purlins;
        rafters = this.rafters;
        itemList.generateItemListContent(posts, purlins, rafters, roofs, sheathings);
        assertEquals(10, itemList.getMaterials().size());
        assertEquals(11, itemList.getMaterials().get(0).getAmount() + itemList.getMaterials().get(1).getAmount());
        assertNotEquals(5, itemList.getMaterials().size() );
        assertNotEquals(15, itemList.getMaterials().get(0).getAmount() + itemList.getMaterials().get(1).getAmount() );
    }

    @Test
    void getMaterialsForCarport(){
        itemList.addMaterialToItemList(new ItemListMaterial(posts.get(0), 8, "Stolpe graves 90 cm ned i jorden", "carport", 210));
        itemList.addMaterialToItemList(new ItemListMaterial(posts.get(0), 3, "Stolpe graves 90 cm ned i jorden", "shed", 230));
        itemList.addMaterialToItemList(new ItemListMaterial(purlins.get(1), 2, "Remme i sider, sadles ned i stolper", "carport", 280));
        itemList.addMaterialToItemList(new ItemListMaterial(purlins.get(0), 2, "Remme i sider, sadles ned i stolper", "carport", 300));
        itemList.addMaterialToItemList(new ItemListMaterial(rafters.get(0), 8, "Spær monteres på rem", "carport", 300));
        Map<Material, Integer> carportMaterials = itemList.getMaterialsForCarport();
        assertEquals(4, carportMaterials.size());
        int totalAmount = 0;
        for(Map.Entry<Material, Integer> entry: carportMaterials.entrySet()){
            totalAmount += entry.getValue();
        }
        assertEquals(20, totalAmount);
        assertNotEquals(18,totalAmount);
    }

    @Test
    void getMaterialsForShed(){
        itemList.addMaterialToItemList(new ItemListMaterial(posts.get(0), 8, "Stolpe graves 90 cm ned i jorden", "carport", 320));
        itemList.addMaterialToItemList(new ItemListMaterial(posts.get(0), 3, "Stolpe graves 90 cm ned i jorden", "shed", 210));
        itemList.addMaterialToItemList(new ItemListMaterial(purlins.get(1), 2, "Remme i sider, sadles ned i stolper", "carport", 300));
        itemList.addMaterialToItemList(new ItemListMaterial(purlins.get(0), 2, "Remme i sider, sadles ned i stolper", "carport", 410));
        itemList.addMaterialToItemList(new ItemListMaterial(rafters.get(0), 8, "Spær monteres på rem", "carport", 450));
        Map<Material, Integer> shedMaterials = itemList.getMaterialsForShed();
        assertEquals(1, shedMaterials.size());
        int totalAmount = 0;
        for(Map.Entry<Material, Integer> entry: shedMaterials.entrySet()){
            totalAmount += entry.getValue();
        }
        assertEquals(3, totalAmount);
        assertNotEquals(1,totalAmount);
    }

}
