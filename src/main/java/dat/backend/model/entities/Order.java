package dat.backend.model.entities;

public class Order {

    private int orderID;
    private int userID;
    private Carport carport;
    private String orderStatus;
    private double price;
    private double indicativePrice;

    public Order(int orderID, int userID, Carport carport, String orderStatus, double price, double indicativePrice){
        this.orderID = orderID;
        this.userID = userID;
        this.carport = carport;
        this.orderStatus = orderStatus;
        this.price = price;
        this.indicativePrice = indicativePrice;
    }
}
