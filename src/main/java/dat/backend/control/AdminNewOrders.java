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

@WebServlet(name = "AdminNewOrders", value = "/adminneworders")
public class AdminNewOrders extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Order> newOrders = (List<Order>) session.getAttribute("newOrders");
        Set<User> newOrdersUsers = (Set<User>) session.getAttribute("newOrdersUsers");
        if(newOrders == null || newOrdersUsers == null){
            try {
                newOrders = OrderFacade.getNewOrders(connectionPool);
                newOrdersUsers = UserFacade.getUsersWithNewOrders(newOrders, connectionPool);
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
