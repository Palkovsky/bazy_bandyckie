package lab3.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name="invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int invoiceNumber;
    private int quantity;

    @ManyToMany
    private Set<Product> products;

    public Invoice() {
        this.products = new HashSet<>();
    }

    public Invoice(int quantity) {
        this();
        this.quantity = quantity;
    }

    public Set<Product> getProducts() {
        return products;
    }
}
