package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;
import dat.backend.model.persistence.UserFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AllOrders", value = "/allorders")
public class AllOrders extends HttpServlet {

    private static ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    /**
     * A servlet to pull all orders and users from the DB, stores them on the session scope, and shows specific info to the admin,
     * so he/she can keep a track on all active orders
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @author CarstenJuhl
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        List<Order> allOrders = (List<Order>) session.getAttribute("allOrders");
        List<User> allUsers = (List<User>) session.getAttribute("allUsers");

        try{
            if(allOrders == null) {
                allOrders = OrderFacade.getAllOrders(connectionPool);
                session.setAttribute("allOrders", allOrders);
            }
            if(allUsers == null) {
                allUsers = UserFacade.getAllUsers(connectionPool);
                session.setAttribute("allUsers", allUsers);
            }
        } catch (DatabaseException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        request.getRequestDispatcher("WEB-INF/allorders.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request,response);

    }
}
