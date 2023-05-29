package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * This servlet handles deleting orders from the database and updating the list on the sessionScope if removal is successful.
 * @author MrJustMeDahl
 */
@WebServlet(name = "AdminDeleteOffer", value = "/admindeleteoffer")
public class AdminDeleteOffer extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * This method handles deleting orders from the database and updating the list on the sessionScope if removal is successful.
     * It retrieves the orderID from the request object and tries to delete it everything in the database connected to that order ID.
     * If successful it will remove the Order object from the List of Orders that are saved on sessionScope.
     * It redirects to adminneworders servlet
     * @param request Http request object
     * @param response Http response object
     * @throws ServletException
     * @throws IOException
     * @author MrJustMeDahl
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderID = Integer.parseInt(request.getParameter("orderID"));
        HttpSession session = request.getSession();
        try {
            if(OrderFacade.deleteOrder(orderID, connectionPool)){
                List<Order> newOrders = (List<Order>) session.getAttribute("newOrders");
                Order chosenOrder = null;
                for(Order o: newOrders){
                    if(o.getOrderID() == orderID){
                        chosenOrder = o;
                    }
                }
                newOrders.remove(chosenOrder);
            }
        } catch (DatabaseException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        response.sendRedirect("adminneworders");
    }
}
