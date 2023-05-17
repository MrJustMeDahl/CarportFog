package dat.backend.entities;

import dat.backend.model.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    void calculatePosts(){

    }

    @Test
    void calculatePurlins(){

    }

    @Test
    void calculateRafters(){

    }
}
