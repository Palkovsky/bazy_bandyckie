package lab3.model;

import javax.persistence.Entity;

@Entity(name = "customers")
public class Customer extends Company {
    private int discount;

    public Customer() {
        super();
        this.discount = 0;
    }

    public Customer(String companyName, int discount, String street, String city, String zipCode) {
        super(companyName, street, city, zipCode);
        this.discount = discount;
    }
}
