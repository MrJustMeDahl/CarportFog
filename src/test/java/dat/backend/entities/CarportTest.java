package dat.backend.entities;

import dat.backend.model.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CarportTest {

    private Carport carport;
    private Map<Material, Integer> materials;

    @BeforeEach
    void setup(){
        materials = new HashMap<>();
        materials.put(new Post(1, 1,"97x97mm. trykimp.", "træ", "stolpe", 55, 210), 8);
        materials.put(new Rafter(2, 2, "45x195mm. spærtræ", "træ", "spær", 35, 360), 15);
        materials.put(new Purlin(3, 3, "45x195mm. spærtræ", "træ", "rem", 35, 450), 6);
        carport = new Carport(materials, 400, 700, 300, null);
    }

    @Test
    void calculateActualPrice(){
        assertEquals(3759, carport.calculatePrice());
    }

    @Test
    void calculateIndicativePrice(){
        assertEquals(7703, carport.calculateIndicativePrice());
    }
}
