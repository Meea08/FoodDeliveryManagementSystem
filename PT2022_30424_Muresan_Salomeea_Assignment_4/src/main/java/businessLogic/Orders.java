package businessLogic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Orders implements Serializable, Comparable<Orders> {
    private int orderId;
    private int clientId;
    private LocalDateTime date;

    public Orders(int clientId, int orderId){
        this.clientId = clientId;
        this.orderId = orderId;
    }

    public Orders() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return orderId == orders.orderId && clientId == orders.clientId && date.equals(orders.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, clientId, date);
    }

    public int getOrderId(){
        return this.orderId;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDateNow(){
        this.date  = LocalDateTime.now();
    }
    public LocalDateTime getDate(){
        return this.date;
    }

    public int getClientId() {
        return clientId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public int compareTo(Orders o) {
        return Integer.compare(orderId, o.orderId);
    }
}
