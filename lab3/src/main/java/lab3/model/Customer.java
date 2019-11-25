package lab3.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "customers")
public class Customer extends Company {

    @OneToMany(mappedBy = "customer")
    private List<BatchOrder> orders;

    public Customer() {
        super();
        this.orders = new ArrayList<>();
    }

    public Customer(String companyName, String passwrod, String street, String city, String zipCode) {
        super(companyName, passwrod, street, city, zipCode);
        this.orders = new ArrayList<>();
    }

    public List<BatchOrder> getOrders() {
        return orders;
    }
}
