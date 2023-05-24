package dat.backend.control;

import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

public class Generator3d {

    public static void generateModel()
    {
        JavaCSG csg = JavaCSGFactory.createDefault();

        Geometry3D sphere = csg.sphere3D(27.50, 64, true);
        Geometry3D cutOffHole = csg.cylinder3D(40, 1000, 64, true);
        Geometry3D cutOffBot = csg.box3D(55, 55, 50, true);
        Geometry3D cutOffTop = csg.box3D(55, 55, 40, true);
        Geometry3D circle = csg.difference3D(sphere, cutOffHole);
        cutOffBot = csg.translate3D(0, 0, -25).transform(cutOffBot);
        circle = csg.difference3D(circle, cutOffBot);
        cutOffTop = csg.translate3D(0,0, 35).transform(cutOffTop);
        circle = csg.difference3D(circle, cutOffTop);
        csg.view(circle);


        //Cross1
        Geometry3D sqCross1 = csg.box3D(77.78, 10, 10, true);
        Geometry3D cylCross1 = csg.cylinder3D(10, 77.78, 64, true);
        cylCross1 = csg.rotate3DX(csg.degrees(90)).transform(cylCross1);
        cylCross1 = csg.translate3D(0, 0, 5).transform(cylCross1);
        sqCross1 = csg.rotate3DZ(csg.degrees(90)).transform(sqCross1); // WHY Z WHEN THE OTHER IS X?
        Geometry3D combCross1 = csg.union3D(sqCross1, cylCross1);

        //Cross2
        Geometry3D sqCross2 = csg.box3D(77.78, 10, 10, true);
        Geometry3D cylCross2 = csg.cylinder3D(10, 77.78, 64, true);
        cylCross2 = csg.rotate3DX(csg.degrees(90)).transform(cylCross2);
        cylCross2 = csg.translate3D(0,0,5).transform(cylCross2);
        sqCross2 = csg.rotate3DZ(csg.degrees(90)).transform(sqCross2);
        Geometry3D combCross2 = csg.union3D(cylCross2, sqCross2);
        combCross2 = csg.rotate3DZ(csg.degrees(90)).transform(combCross2);

        //Putting shapes together
        Geometry3D sqCross = csg.union3D(combCross1, combCross2);
        sqCross = csg.translate3D(0, 0, 5).transform(sqCross);
        csg.view(sqCross);

    }
}
