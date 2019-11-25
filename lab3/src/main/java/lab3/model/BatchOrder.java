package lab3.model;

import org.apache.derby.client.am.DateTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity(name="batchorders")
public class BatchOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

    private Timestamp orderDate;

    @OneToOne
    @JoinColumn
    private Customer customer;

    @OneToMany(mappedBy = "batchOrder", cascade = {CascadeType.PERSIST})
    private Set<SingleOrder> singleOrders;

    public BatchOrder() {
        this.orderDate = new Timestamp(System.currentTimeMillis());
        this.singleOrders = new HashSet<>();
    }

    public BatchOrder(Customer customer) {
        this();
        this.customer = customer;
    }

    public void makeOrder(Product product, int quantity) {
        if(product.getUnitsOnStock() < quantity) {
            throw new IllegalArgumentException("Not enough units in stock to make order.");
        }

        SingleOrder singleOrder = new SingleOrder(product, quantity);
        singleOrder.setBatchOrder(this);

        singleOrders.add(singleOrder);
        product.setUnitsOnStock(product.getUnitsOnStock() - quantity);
    }
}
