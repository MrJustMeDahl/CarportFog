package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "shoppingbasket", urlPatterns = {"/shoppingbasket"})
public class ShoppingBasket extends HttpServlet {
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }


    /**
     * doGet will load a pending order into the shoppingbasket on requestscope, which will act as the shoppingbasket
     *
     * @param request  Used for loading in the data on the request scope.
     * @param response Is used to set the contentType.
     * @throws IOException      Is cast if the input/output is invalid.
     * @throws ServletException is cast when theres an error using Servlets in general.
     * @author pelle112112
     */

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String message = (String) request.getAttribute("message");
        if(message == null){
            message = "Din indkøbskurv er tom";
        }
        request.setAttribute("message", message);

        List<Order> orderList = user.getOrders();
        request.setAttribute("orderlist", orderList);

        for (Order o : orderList) {
            if (Objects.equals(o.getOrderStatus(), "pending")) {
                request.setAttribute("order", o);
            }
        }


        request.getRequestDispatcher("WEB-INF/shoppingbasket.jsp").forward(request, response);
    }

    /**
     * doPost will just redirect to the doGet() method.
     *
     * @param request  Used for loading in the data on the request scope.
     * @param response Is used to set the contentType.
     * @throws IOException      Is cast if the input/output is invalid.
     * @throws ServletException is cast when theres an error using Servlets in general.
     * @author pelle112112
     */

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

}