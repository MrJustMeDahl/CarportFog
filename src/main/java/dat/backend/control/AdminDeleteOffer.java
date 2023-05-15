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
