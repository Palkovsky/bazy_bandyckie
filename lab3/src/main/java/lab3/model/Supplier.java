package lab3.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name="suppliers")
public class Supplier extends Company{
    private String bankAccountNumber;

    @OneToMany(mappedBy = "supplier")
    private Set<Product> suppliedProducts;

    public Supplier() {
        super();
    }

    public Supplier(String companyName, String bankAccountNumber, String street, String city, String zip) {
        super(companyName, street, city, zip);
        this.bankAccountNumber = bankAccountNumber;
        this.suppliedProducts = new HashSet<>();
    }

    public void addProduct(Product product) {
        suppliedProducts.add(product);
    }
}