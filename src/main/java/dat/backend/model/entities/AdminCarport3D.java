package dat.backend.model.entities;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

import java.util.ArrayList;
import java.util.List;


public class AdminCarport3D {

    JavaCSG csg = JavaCSGFactory.createDefault();

    int materialId;
    int materialVariantId;
    int postWidth = 97;
    int postHeight = 97;
    int postLength;
    int purlinWidth = 45;
    int purlinHeight = 195;
    int purlinLength;
    int rafterWidth = 45;
    int rafterHeight = 195;
    int rafterLength;
    int roofWidth;
    int roofHeight;
    int roofLength;
    int postAmount;
    int purlinAmount;
    int rafterAmount;
    int roofAmount;
    Geometry3D allPurlins;
    Geometry3D allRafters;
    Geometry3D allRoofs;



    Admin3DPurlin purlin = new Admin3DPurlin(materialId,materialVariantId,purlinWidth,purlinHeight,purlinLength);
    Admin3DRafter rafter = new Admin3DRafter(materialId, materialVariantId, rafterWidth, rafterHeight, rafterLength);
    Admin3DRoof roof = new Admin3DRoof(materialId, materialVariantId, roofWidth, roofHeight,roofLength);

    List <Admin3DPost> posts = new ArrayList<>();
    List<Admin3DPost> purlins = new ArrayList<>();
    List<Admin3DPost> rafters = new ArrayList<>();
    List<Admin3DPost> roofs = new ArrayList<>();


    public Geometry3D allPurlins(){
        Geometry3D purlin = csg.box3D(purlinWidth,purlinHeight,purlinLength,true);

        return allPurlins;
    }

    public  Geometry3D allRafters(){
        Geometry3D rafter = csg.box3D(rafterWidth,rafterHeight,rafterLength,true);

        return allRafters;
    }

    public Geometry3D allRoofs(){
        Geometry3D roof = csg.box3D(roofWidth,roofHeight,roofLength,true);

        return allRoofs;
    }

public static void main(String[] args){

    JavaCSG csg = JavaCSGFactory.createDefault();


        Admin3DPost post = new Admin3DPost();
        ArrayList<Admin3DPost> allposts = new ArrayList<>();
/*
        for(int i = 0; i<=3; i++){
            var posts = post;
            posts = csg.translate3DX(100);
            allposts.add(posts);

        }
 */
    Geometry3D cockpit = csg.cone3D(3000,300,3500,100,true);
    csg.view(cockpit);





}




}
