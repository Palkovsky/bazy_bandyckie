package lab3.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name="products")
public class Product {

    @Id
    private String productName;
    private int unitsOnStock;

    @ManyToOne
    private Supplier supplier;

    @ManyToOne
    private Category category;

    @ManyToMany
    private Set<Invoice> invoices;

    public Product() {
        this.invoices = new HashSet<>();
        this.supplier = null;
        this.category = null;
    }

    public Product(String productName, int unitsOnStock) {
        this();
        this.productName = productName;
        this.unitsOnStock = unitsOnStock;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }
}
