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

    @ManyToMany(mappedBy = "invoices")
    private Set<Product> products;

    public Invoice() {
        this.products = new HashSet<>();
        this.quantity = 0;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.getInvoices().add(this);
        this.quantity = this.products.size();
    }
}
