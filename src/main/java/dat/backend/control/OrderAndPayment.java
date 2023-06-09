package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;
import dat.backend.model.persistence.OrderMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "orderandpayment", urlPatterns = {"/orderandpayment"} )
public class OrderAndPayment extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    /**
     * doGet is used to update the orderStatus of the order from "confirmed" to "payed", and then redirect back to the
     * same page.
     * @param request Used for loading in the data on the request scope.
     * @param response Is used to set the contentType.
     * @throws IOException Is cast if the input/output is invalid.
     * @throws ServletException is cast when theres an error using Servlets in general.
     * @author pelle112112
     */

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {


        try {
            response.setContentType("text/html");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            Order order;

            int idCheck;
            idCheck = OrderFacade.updateOrderPayed(Integer.parseInt(request.getParameter("currentID")), connectionPool);

            List<Order> list  = user.getOrders();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getOrderID()==idCheck){
                    order = list.get(i);
                    order.setOrderStatus("payed");
                    request.setAttribute("order",order);
                }
            }
            request.getRequestDispatcher("orderreciept").forward(request, response);
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }


    }

    /**
     * doPost will make use of the orderMapper method updateOrderOrdered(), which changes the order status of the order
     * from "pending" to "ordered", and then redirect the user back to the shopping cart.
     * @param request Used for loading in the data on the request scope.
     * @param response Is used to set the contentType.
     * @throws IOException Is cast if the input/output is invalid.
     * @throws ServletException is cast when theres an error using Servlets in general.
     * @author pelle112112
     */

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int orderID = Integer.parseInt(request.getParameter("OrderId"));
        Order order = null;
        for(Order o: user.getOrders()){
            if(o.getOrderID() == orderID){
                order = o;
            }
        }

        try {
            OrderFacade.updateOrderOrdered(order.getOrderID(), connectionPool);
            order.setOrderStatus("ordered");
            request.getRequestDispatcher("shoppingbasket").forward(request, response);

        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}