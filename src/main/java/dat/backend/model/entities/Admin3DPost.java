package dat.backend.model.entities;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

public class Admin3DPost {

    JavaCSG csg = JavaCSGFactory.createDefault();

    int height = 97;
    int width = 97;
    int length = 200;


    public Geometry3D Post(){

        Geometry3D post = csg.box3D(height,width,length,true);

        return post;
    }
    Geometry3D post = csg.box3D(height,width,length,true);




}
