package dat.backend.entities;

import dat.backend.model.entities.*;
import dat.backend.model.exceptions.NoMaterialFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialCalculatorTest {

    private List<Post> posts;
    private List<Purlin> purlins;
    private List<Rafter> rafters;
    private List<Roof> roofs;
    private List<Sheathing> sheathings;

    @BeforeEach
    void setup(){
        posts = new ArrayList<>();
        purlins = new ArrayList<>();
        rafters = new ArrayList<>();
        roofs = new ArrayList<>();
        sheathings = new ArrayList<>();
        posts.add(new Post(1, 1, "97x97mm. trykimp.", "træ", "stolpe", 50, 420));
        purlins.add(new Purlin(2, 2, "45x195mm. spærtræ", "træ", "rem", 45, 600));
        purlins.add(new Purlin(2, 3, "45x195mm. spærtræ", "træ", "rem", 45, 400));
        rafters.add(new Rafter(3, 4, "45x195mm. spærtræ", "træ", "rem", 45, 600));
        roofs.add(new Roof(4, 5, "bølgeplade sunlux sort", "plastik", "tag", 140, 200));
        sheathings.add(new Sheathing(5, 6, "19x125mm. beklædning høvlet", "træ", "bræddebeklædning", 18, 210));
    }

    @Test
    void calculatePosts() throws NoMaterialFoundException{
        MaterialCalculator mc1 = new MaterialCalculator(780, 600, 500, true, 210, 600);
        assertThrows(NoMaterialFoundException.class, () -> mc1.calculatePosts(posts));
        MaterialCalculator mc2 = new MaterialCalculator(780, 600, 210, true, 210, 600);
        Set<ItemListMaterial> materialSet= mc2.calculatePosts(posts);
        assertEquals(2, materialSet.size());
        int totalNumberOfPosts = 0;
        for(ItemListMaterial i: materialSet){
            totalNumberOfPosts += i.getAmount();
        }
        assertEquals(11, totalNumberOfPosts);
    }

    @Test
    void calculatePurlins() throws NoMaterialFoundException{
        MaterialCalculator mc1 = new MaterialCalculator(1210, 600, 210, true, 210, 600);
        assertThrows(NoMaterialFoundException.class, () -> mc1.calculatePurlins(purlins));
        MaterialCalculator mc2 = new MaterialCalculator(780, 600, 210, true, 210, 600);
        Set<ItemListMaterial> materialSet = mc2.calculatePurlins(purlins);
        assertEquals(2, materialSet.size());
        MaterialCalculator mc3 = new MaterialCalculator(580, 600, 210, true, 210 ,600);
        Set<ItemListMaterial> materials = mc3.calculatePurlins(purlins);
        assertEquals(1, materials.size());
    }

    @Test
    void calculateRafters() throws NoMaterialFoundException{
        MaterialCalculator mc1 = new MaterialCalculator(780, 610, 210, true, 210, 600);
        assertThrows(NoMaterialFoundException.class, () -> mc1.calculateRafters(rafters));
        MaterialCalculator mc2 = new MaterialCalculator(780, 600, 210, true, 210, 600);
        ItemListMaterial rafter = mc2.calculateRafters(rafters);
        assertEquals(15, rafter.getAmount());
     }

     @Test
    void calculateRoofs() throws NoMaterialFoundException{
         MaterialCalculator mc1 = new MaterialCalculator(780, 600, 210, true, 210, 600);
         Set<ItemListMaterial> setRoofs = mc1.calculateRoofs(roofs);
         ItemListMaterial itemListMaterial = null;
         for(ItemListMaterial i: setRoofs){
             itemListMaterial = i;
         }
         assertEquals(40, itemListMaterial.getAmount());
     }

     @Test
    void calculateSheathings() throws NoMaterialFoundException{
         MaterialCalculator mc1 = new MaterialCalculator(780, 600, 210, true, 210, 600);
         Set<ItemListMaterial> sheathing = mc1.calculateSheathings(sheathings);
         ItemListMaterial itemListMaterial = null;
         for(ItemListMaterial i: sheathing){
             if(i.getMaterial() instanceof Sheathing) {
                 itemListMaterial = i;
             }
         }
         assertEquals(4, sheathing.size());
         assertEquals(195, itemListMaterial.getAmount());
     }
}
