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
    @JoinColumn
    private Supplier supplier;

    @ManyToOne
    @JoinColumn
    private Category category;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private Set<Invoice> invoices;

    public Product() {
        this.invoices = new HashSet<>();
    }

    public Product(String productName, int unitsOnStock) {
        this();
        this.productName = productName;
        this.unitsOnStock = unitsOnStock;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public int getUnitsOnStock() {
        return unitsOnStock;
    }

    public void setUnitsOnStock(int unitsOnStock) {
        this.unitsOnStock = unitsOnStock;
    }

    public String getProductName() {
        return productName;
    }
}