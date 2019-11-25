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

    @OneToMany(mappedBy = "product")
    private Set<SingleOrder> orders;

    public Product() {
        this.orders = new HashSet<>();
    }

    public Product(String productName, int unitsOnStock) {
        this();
        this.productName = productName;
        this.unitsOnStock = unitsOnStock;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCategoryAsString() {
        return (category != null) ? category.getName() : "none";
    }

    public Category getCategory() {
        return category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public int getUnitsOnStock() {
        return unitsOnStock;
    }

    public void setUnitsOnStock(int unitsOnStock) {
        this.unitsOnStock = unitsOnStock;
    }

    public String getName() {
        return productName;
    }

    public Set<SingleOrder> getOrders() {
        return orders;
    }
}