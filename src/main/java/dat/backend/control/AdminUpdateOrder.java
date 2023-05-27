package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NoMaterialFoundException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * This servlet handles what happens when the admin updates the measurements of an order.
 *
 * @author MrJustMeDahl
 */
@WebServlet(name = "AdminUpdateOrder", value = "/adminupdateorder")
public class AdminUpdateOrder extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * This method handles what happens when the admin updates the measurements of an order.
     * It retrieves the order ID of the chosen order and all the measurements of the product on that order from the requestScope.
     * Then it retrieves the list of new orders from the sessionScope and finds the order that matches the order ID of the chosen order.
     * If the length and width of the shed is more than 0, it will create a new itemList for the order that also calculates materials for a shed and if not only materials for a carport will be calculated.
     * When the new item list is generated the order object will be updated and the data for the order in the database will be updated.
     * @param request  Http request object
     * @param response Http response object
     * @throws ServletException
     * @throws IOException
     * @author MrJustMeDahl
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderID = Integer.parseInt(request.getParameter("orderID"));
        int length = Integer.parseInt(request.getParameter("length"));
        int width = Integer.parseInt(request.getParameter("width"));
        int minHeight = Integer.parseInt(request.getParameter("minHeight"));
        int shedLength = Integer.parseInt(request.getParameter("shedLength"));
        int shedWidth = Integer.parseInt(request.getParameter("shedWidth"));
        HttpSession sessionScope = request.getSession();
        ServletContext applicationScope = getServletContext();
        List<Order> newOrders = (List<Order>) sessionScope.getAttribute("newOrders");
        Order updateOrder = null;
        for (Order o : newOrders) {
            if (o.getOrderID() == orderID) {
                updateOrder = o;
            }
        }
        boolean hasShed = false;

        if (shedLength > 0 && shedWidth > 0) {
            hasShed = true;
        }

        try {
            if (!hasShed) {
                updateOrder.setItemList(new ItemList(length, width, minHeight, hasShed, 0, 0, (List<Post>) applicationScope.getAttribute("allPosts"), (List<Purlin>) applicationScope.getAttribute("allPurlins"), (List<Rafter>) applicationScope.getAttribute("allRafters"), (List<Roof>) applicationScope.getAttribute("allRoofs"), (List<Sheathing>) applicationScope.getAttribute("allSheathings")));
            } else {
                updateOrder.setItemList(new ItemList(length, width, minHeight, hasShed, shedLength, shedWidth, (List<Post>) applicationScope.getAttribute("allPosts"), (List<Purlin>) applicationScope.getAttribute("allPurlins"), (List<Rafter>) applicationScope.getAttribute("allRafters"), (List<Roof>) applicationScope.getAttribute("allRoofs"), (List<Sheathing>) applicationScope.getAttribute("allSheathings")));
            }
            updateOrder.getCarport().setMaterials(updateOrder.getItemList().getMaterialsForCarport());
            updateOrder.getCarport().setLength(length);
            updateOrder.getCarport().setWidth(width);
            updateOrder.getCarport().setMinHeight(minHeight);
            updateOrder.getCarport().getShed().setMaterials(updateOrder.getItemList().getMaterialsForShed());
            updateOrder.getCarport().getShed().setLength(shedLength);
            updateOrder.getCarport().getShed().setWidth(shedWidth);
            updateOrder.setPrice();
            updateOrder.setIndicativePrice();
        } catch (NoMaterialFoundException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        try {
            OrderFacade.updateItemListForOrder(orderID, updateOrder.getItemList(), connectionPool);
            OrderFacade.updateMeasurementsForOrder(orderID, updateOrder.getCarport(), connectionPool);
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        request.setAttribute("chosenOrderID", orderID);
        request.getRequestDispatcher("WEB-INF/newordersadministration.jsp").forward(request, response);
    }
}
