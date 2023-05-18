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

    @BeforeEach
    void setup(){
        posts = new ArrayList<>();
        purlins = new ArrayList<>();
        rafters = new ArrayList<>();
        posts.add(new Post(1, 1, "97x97mm. trykimp.", "træ", "stolpe", 50, 420));
        purlins.add(new Purlin(2, 2, "45x195mm. spærtræ", "træ", "rem", 45, 600));
        purlins.add(new Purlin(2, 3, "45x195mm. spærtræ", "træ", "rem", 45, 400));
        rafters.add(new Rafter(3, 4, "45x195mm. spærtræ", "træ", "rem", 45, 600));
    }

    @Test
    void calculatePosts() throws NoMaterialFoundException{
        MaterialCalculator mc1 = new MaterialCalculator(780, 600, 500, true);
        assertThrows(NoMaterialFoundException.class, () -> mc1.calculatePosts(posts));
        MaterialCalculator mc2 = new MaterialCalculator(780, 600, 210, true);
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
        MaterialCalculator mc1 = new MaterialCalculator(1210, 600, 210, true);
        assertThrows(NoMaterialFoundException.class, () -> mc1.calculatePurlins(purlins));
        MaterialCalculator mc2 = new MaterialCalculator(780, 600, 210, true);
        Set<ItemListMaterial> materialSet = mc2.calculatePurlins(purlins);
        assertEquals(2, materialSet.size());
        MaterialCalculator mc3 = new MaterialCalculator(580, 600, 210, true);
        Set<ItemListMaterial> materials = mc3.calculatePurlins(purlins);
        assertEquals(1, materials.size());
    }

    @Test
    void calculateRafters() throws NoMaterialFoundException{
        MaterialCalculator mc1 = new MaterialCalculator(780, 610, 210, true);
        assertThrows(NoMaterialFoundException.class, () -> mc1.calculateRafters(rafters));
        MaterialCalculator mc2 = new MaterialCalculator(780, 600, 210, true);
        ItemListMaterial rafter = mc2.calculateRafters(rafters);
        assertEquals(15, rafter.getAmount());
     }
}
