package dat.backend.control;

import org.abstractica.javacsg.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet(name = "AdminGenerate3d", value = "/admingenerate3d")
public class AdminGenerate3d extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Generator3d.generateModel();
        request.getRequestDispatcher("WEB-INF/allorders.jsp").forward(request, response);
        /*
        String filePath = "";
        File file = new File(filePath);

        if (file.exists()) {
            response.setContentType("application/octet-stream");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }

            fileInputStream.close();
        } else {
            request.setAttribute("errormessage", "File does not exist");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

         */
    }

    public static void main(String[] args)
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
