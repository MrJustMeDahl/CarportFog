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
        String materialfunctionname = request.getParameter("materialfunction");
        List<Material>postList = (List<Material>) request.getServletContext().getAttribute("allPosts");
        List<Material>rafterList = (List<Material>) request.getServletContext().getAttribute("allRafters");
        List<Material>purlinList = (List<Material>) request.getServletContext().getAttribute("allPurlins");
        int selectedMaterial;

        try {
            chosenmaterials = (List<Material>) session.getAttribute("editmateriallist");
        }catch (NullPointerException e){}

        try{
        if(materialfunction == 1){
            if (chosenmaterials == postList) {
                chosenmaterialId = Integer.parseInt(request.getParameter("materialdescription"));
            }else {
                chosenmaterialId = -1;
            }
            session.setAttribute("editmateriallist",postList);
        }

        else if (materialfunction == 2){
            if(chosenmaterials == purlinList) {
                chosenmaterialId = Integer.parseInt(request.getParameter("materialdescription"));
            }else {
                chosenmaterialId = -1;
            }
            session.setAttribute("editmateriallist",purlinList);
        }
        else if (materialfunction == 3){
            if(chosenmaterials == rafterList) {
                chosenmaterialId = Integer.parseInt(request.getParameter("materialdescription"));
            }else {
                chosenmaterialId = -1;
            }
            session.setAttribute("editmateriallist",rafterList);
        }
        }catch(NullPointerException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }

        request.setAttribute("materialfunction",materialfunction);
        request.setAttribute("chosenmaterialId",chosenmaterialId);
        request.getRequestDispatcher("WEB-INF/updatematerials.jsp").forward(request,response);

    }
}

