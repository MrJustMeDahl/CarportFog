package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Material;
import dat.backend.model.entities.Post;
import dat.backend.model.entities.Purlin;
import dat.backend.model.entities.Rafter;
import dat.backend.model.persistence.ConnectionPool;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EditChosenMaterialFunction", value = "/editchosenmaterialfunction")
public class EditChosenMaterialFunction extends HttpServlet {

    private static ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        HttpSession session = request.getSession();
        int chosenmaterialId = -1;
        int materialfunction = Integer.parseInt(request.getParameter("materialfunction"));
        List<Material> chosenmaterials = null;
        List<Material>postList = (List<Material>) request.getServletContext().getAttribute("allPosts");
        List<Material>rafterList = (List<Material>) request.getServletContext().getAttribute("allRafters");
        List<Material>purlinList = (List<Material>) request.getServletContext().getAttribute("allPurlins");
        Material chosenMaterial = null;

        try {
            chosenmaterials = (List<Material>) session.getAttribute("editmateriallist");
        }catch (NullPointerException e){}

        try{
        if(materialfunction == 1){
            session.setAttribute("editmateriallist",postList);
            if (chosenmaterials == postList) {
                chosenmaterialId = Integer.parseInt(request.getParameter("materialdescription"));
                for(Material m: chosenmaterials){
                    if(m.getMaterialID() == chosenmaterialId){
                        chosenMaterial = m;
                    }
                }
            }else {
                chosenmaterialId = -1;
            }
        }

        else if (materialfunction == 2){
            session.setAttribute("editmateriallist",purlinList);
            if(chosenmaterials == purlinList) {
                chosenmaterialId = Integer.parseInt(request.getParameter("materialdescription"));
                for(Material m: chosenmaterials){
                    if(m.getMaterialID() == chosenmaterialId){
                        chosenMaterial = m;
                    }
                }
            }else {
                chosenmaterialId = -1;
            }
        }
        else if (materialfunction == 3){
            session.setAttribute("editmateriallist",rafterList);
            if(chosenmaterials == rafterList) {
                chosenmaterialId = Integer.parseInt(request.getParameter("materialdescription"));
                for(Material m: chosenmaterials){
                    if(m.getMaterialID() == chosenmaterialId){
                        chosenMaterial = m;
                    }
                }
            }else {
                chosenmaterialId = -1;
            }
        }
        }catch(Exception e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }

        request.setAttribute("chosenMaterial", chosenMaterial);
        request.setAttribute("materialfunction",materialfunction);
        request.setAttribute("chosenmaterialId",chosenmaterialId);
        request.getRequestDispatcher("WEB-INF/updatematerials.jsp").forward(request,response);

    }
}

