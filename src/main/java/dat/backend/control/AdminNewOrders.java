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
import java.util.List;
import java.util.Set;

/**
 * This is servlet that loads in the data needed to display newordersadministration.jsp.
 * @author MrJustMeDahl
 */
@WebServlet(name = "AdminNewOrders", value = "/adminneworders")
public class AdminNewOrders extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    /**
     * This method loads in the data needed to display newordersadministration.jsp.
     * The data includes a List of Order objects with order status 'ordered', and a Set of User objects who own 1 or more of the before mentioned orders.
     * The List and the Set are saved on sessionScope, so that it will only be retrieved from the database once.
     * If an orderID exists on the requestScope, it will save that on the requestScope.
     * @param request Http servlet request object
     * @param response Http servlet response object
     * @throws ServletException
     * @throws IOException
     * @author MrJustMeDahl
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Order> newOrders = (List<Order>) session.getAttribute("newOrders");
        Set<User> newOrdersUsers = (Set<User>) session.getAttribute("newOrdersUsers");
        if(newOrders == null || newOrdersUsers == null){
            try {
                newOrders = OrderFacade.getNewOrders(connectionPool);
                newOrdersUsers = UserFacade.getUsersForOrders(newOrders, connectionPool);
                session.setAttribute("newOrders", newOrders);
                session.setAttribute("newOrdersUsers", newOrdersUsers);
            } catch (DatabaseException e){
                request.setAttribute("errormessage", e);
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
        try {
            int chosenOrderID = Integer.parseInt(request.getParameter("orderID"));
            request.setAttribute("chosenOrderID", chosenOrderID);
        } catch (RuntimeException e){
        }
        request.getRequestDispatcher("WEB-INF/newordersadministration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
