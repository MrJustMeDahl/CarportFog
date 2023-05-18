package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.MaterialFacade;
import dat.backend.model.persistence.UserFacade;
import dat.backend.model.persistence.ConnectionPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "login", urlPatterns = {"/login"} )
public class Login extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        // You shouldn't end up here with a GET-request, thus you get sent back to frontpage
        response.sendRedirect("index.jsp");
    }

    /**
     * Servlet used for logging in.
     * The first time a user logs on the application all the materials is pulled from the database and saved on the applicationScope.
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     * @author MrJustMeDahl
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        session.setAttribute("user", null); // invalidating user object in session scope
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        ServletContext applicationScope = getServletContext();
        try
        {
            User user = UserFacade.login(username, password, connectionPool);
            session = request.getSession();
            session.setAttribute("user", user); // adding user object to session scope
            List<Post> allPosts = (List<Post>) applicationScope.getAttribute("allPosts");
            List<Rafter> allRafters = (List<Rafter>) applicationScope.getAttribute("allRafters");
            List<Purlin> allPurlins = (List<Purlin>) applicationScope.getAttribute("allPurlins");
            List<Roof> allRoofs = (List<Roof>) applicationScope.getAttribute("allRoofs");
            List<Sheathing> allSheathing = (List<Sheathing>) applicationScope.getAttribute("allSheathing");
            List<String> allMaterialTypes = (List<String>) applicationScope.getAttribute("allMaterialTypes");
            List<String> allMaterialFunctions = (List<String>) applicationScope.getAttribute("allMaterialFunctions");
            if(allPosts == null){
                allPosts = MaterialFacade.getAllPosts(connectionPool);
                applicationScope.setAttribute("allPosts", allPosts);
            }
            if(allRafters == null){
                allRafters = MaterialFacade.getAllRafters(connectionPool);
                applicationScope.setAttribute("allRafters", allRafters);
            }
            if(allPurlins == null){
                allPurlins = MaterialFacade.getAllPurlins(connectionPool);
                applicationScope.setAttribute("allPurlins", allPurlins);
            }
            if(allRoofs == null){
                allRoofs = MaterialFacade.getAllRoofs(connectionPool);
                applicationScope.setAttribute("allRoofs", allRoofs);
            }
            if(allSheathing == null){
                allSheathing = MaterialFacade.getAllSheathing(connectionPool);
                applicationScope.setAttribute("allSheathings", allSheathing);
            }
            if(allMaterialTypes == null){
                allMaterialTypes = MaterialFacade.getAllMaterialTypes(connectionPool);
                applicationScope.setAttribute("allMaterialTypes", allMaterialTypes);
            }
            if(allMaterialFunctions == null){
                allMaterialFunctions = MaterialFacade.getAllMaterialFunctions(connectionPool);
                applicationScope.setAttribute("allMaterialFunctions", allMaterialFunctions);
            }
            request.getRequestDispatcher("WEB-INF/welcome.jsp").forward(request, response);
        }
        catch (DatabaseException e)
        {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}