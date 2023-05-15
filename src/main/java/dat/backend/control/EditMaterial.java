package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.MaterialFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EditMaterial", value = "/editmaterial")
public class EditMaterial extends HttpServlet {

    private static ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * The editmaterialServlet is used to edit items already in the database.
     * only the admin user can use this servlet, and can change the price and description of a material
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @author CarstenJuhl
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        ServletContext applicationScope = getServletContext();
        User admin = (User) session.getAttribute("admin");
        int materialId = Integer.parseInt(request.getParameter("changematerial"));
        double newPrice = Double.parseDouble(request.getParameter("changeprice"));
        String newDescription = request.getParameter("changematerialdescription");
        int chosenMaterialId = Integer.parseInt(request.getParameter("materialdescription"));
        Material chosenMaterial = null;

        try {
            MaterialFacade.updateMaterial(materialId, newPrice, newDescription, connectionPool);
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        int materialfunction = Integer.parseInt(request.getParameter("materialfunction"));


        switch (materialfunction) {
            case 1:
                for (Material m : (List<Post>) applicationScope.getAttribute("allPosts")) {
                    if (m.getMaterialID() == materialId) {
                        m.setPrice(newPrice);
                        m.setDescription(newDescription);
                        if(chosenMaterialId == m.getMaterialVariantID()){
                            chosenMaterial = m;
                        }
                    }
                }
                break;
            case 2:
                for (Material m : (List<Purlin>) applicationScope.getAttribute("allPurlins")) {
                    if (m.getMaterialID() == materialId) {
                        m.setPrice(newPrice);
                        m.setDescription(newDescription);
                        if(chosenMaterialId == m.getMaterialVariantID()){
                            chosenMaterial = m;
                        }
                    }
                }
                break;
            case 3:
                for (Material m : (List<Rafter>) applicationScope.getAttribute("allRafters")) {
                    if (m.getMaterialID() == materialId) {
                        m.setPrice(newPrice);
                        m.setDescription(newDescription);
                        if(chosenMaterialId == m.getMaterialVariantID()){
                            chosenMaterial = m;
                        }
                    }
                }
                break;
        }
        //TODO Her skal det nye applicationScope laves med updaterede ændringer

        request.setAttribute("chosenMaterial", chosenMaterial);
        request.setAttribute("materialfunction", materialfunction);
        request.setAttribute("chosenmaterialId", chosenMaterialId);
        request.getRequestDispatcher("WEB-INF/updatematerials.jsp").forward(request, response);
    }
}
