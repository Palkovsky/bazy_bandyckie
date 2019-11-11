package lab3.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name="suppliers")
public class Supplier {
    @Id
    private String companyName;
    private String street;
    private String city;

    @OneToMany
    private Set<Product> suppliedProducts;

    public Supplier() { }

    public Supplier(String companyName, String street, String city) {
        this.companyName = companyName;
        this.street = street;
        this.city = city;
        this.suppliedProducts = new HashSet<>();
    }

    public Set<Product> getSuppliedProducts() {
        return suppliedProducts;
    }
}