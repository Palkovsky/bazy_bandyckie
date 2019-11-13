package lab3.model;

import javax.persistence.*;

@Entity(name="singleorders")
public class SingleOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private int quantity;

    @OneToOne
    @JoinColumn
    private Product product;

    @ManyToOne
    @JoinColumn
    private BatchOrder batchOrder;

    public SingleOrder() { }

    public SingleOrder(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void setBatchOrder(BatchOrder batchOrder) {
        this.batchOrder = batchOrder;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
