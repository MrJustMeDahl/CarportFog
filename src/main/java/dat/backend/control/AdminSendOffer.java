package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * This servlet handles what happens when the admin sends an offer to a customer.
 * @author MrJustMeDahl
 */
@WebServlet(name = "AdminSendOffer", value = "/adminsendoffer")
public class AdminSendOffer extends HttpServlet {

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
     * This method handles what happens when the admin sends an offer to a customer.
     * If changes to price and orderstatus is successful in the database, the order will be removed from the list of new orders on the sessionScope.
     * @param request Http request object
     * @param response Http response object
     * @throws ServletException
     * @throws IOException
     * @author MrJustMeDahl
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double salesPrice = Double.parseDouble(request.getParameter("salesPrice"));
        int orderID = Integer.parseInt(request.getParameter("orderID"));
        HttpSession sessionScope = request.getSession();
        List<Order> newOrders = (List<Order>) sessionScope.getAttribute("newOrders");
        try{
            if(OrderFacade.sendOfferToCustomer(orderID, salesPrice, connectionPool)){
                Order removeOrder = null;
                for(Order o: newOrders){
                    if(o.getOrderID() == orderID){
                        o.setIndicativePrice(salesPrice);
                        o.setOrderStatus("confirmed");
                        removeOrder = o;
                    }
                }
                newOrders.remove(removeOrder);
            }
        } catch (DatabaseException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        request.getRequestDispatcher("WEB-INF/newordersadministration.jsp").forward(request, response);
    }
}
