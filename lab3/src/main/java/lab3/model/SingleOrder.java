package lab3.model;

import javax.persistence.*;

@Entity(name="singleorders")
public class SingleOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private int quantity;

    private boolean finalized;

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
        this.finalized = false;
    }

    public void setBatchOrder(BatchOrder batchOrder) {
        this.batchOrder = batchOrder;
    }

    public int getId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isFinalized() {
        return finalized;
    }

    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
    }
}
