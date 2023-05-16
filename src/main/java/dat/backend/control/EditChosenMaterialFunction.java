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

    /**
     * The doGet function, directs the admin from the login page to the material page
     *
     * @param request  Is used for loading in the data on the request scope.
     * @param response Is used to set the contentType.
     * @throws ServletException
     * @throws IOException
     * @author CarstenJuhl
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {


        request.getRequestDispatcher("WEB-INF/updatematerials.jsp").forward(request, response);

    }

    /**
     * Admin choose which category, and item he/she wants to edit, and system will then load the right information into the forms, via the request and session scope.
     *
     * @param request  Used for loading in the data on the request scope.
     * @param response Is used to set the contentType.
     * @throws ServletException
     * @throws IOException
     * @author Carsten Juhl & MrJustMeDahl
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        HttpSession session = request.getSession();
        int chosenmaterialId = -1;
        int materialfunction = Integer.parseInt(request.getParameter("materialfunction"));
        List<Material> chosenmaterials = null;
        List<Material> postList = (List<Material>) request.getServletContext().getAttribute("allPosts");
        List<Material> rafterList = (List<Material>) request.getServletContext().getAttribute("allRafters");
        List<Material> purlinList = (List<Material>) request.getServletContext().getAttribute("allPurlins");
        Material chosenMaterial = null;

        try {
            chosenmaterials = (List<Material>) session.getAttribute("editmateriallist");
        } catch (NullPointerException e) {
        }

        try {
            if (materialfunction == 1) {
                session.setAttribute("editmateriallist", postList);
                if (chosenmaterials == postList) {
                    chosenmaterialId = Integer.parseInt(request.getParameter("materialdescription"));
                    for (Material m : chosenmaterials) {
                        if (m.getMaterialVariantID() == chosenmaterialId) {
                            chosenMaterial = m;
                        }
                    }
                } else {
                    chosenmaterialId = -1;
                }
            } else if (materialfunction == 2) {
                session.setAttribute("editmateriallist", purlinList);
                if (chosenmaterials == purlinList) {
                    chosenmaterialId = Integer.parseInt(request.getParameter("materialdescription"));
                    for (Material m : chosenmaterials) {
                        if (m.getMaterialVariantID() == chosenmaterialId) {
                            chosenMaterial = m;
                        }
                    }
                } else {
                    chosenmaterialId = -1;
                }
            } else if (materialfunction == 3) {
                session.setAttribute("editmateriallist", rafterList);
                if (chosenmaterials == rafterList) {
                    chosenmaterialId = Integer.parseInt(request.getParameter("materialdescription"));
                    for (Material m : chosenmaterials) {
                        if (m.getMaterialVariantID() == chosenmaterialId) {
                            chosenMaterial = m;
                        }
                    }
                } else {
                    chosenmaterialId = -1;
                }
            }
        } catch (Exception e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        request.setAttribute("chosenMaterial", chosenMaterial);
        request.setAttribute("materialfunction", materialfunction);
        request.setAttribute("chosenmaterialId", chosenmaterialId);
        request.getRequestDispatcher("WEB-INF/updatematerials.jsp").forward(request, response);

    }
}

