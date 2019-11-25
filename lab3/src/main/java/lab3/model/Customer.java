package lab3.model;

import javax.persistence.Entity;

@Entity(name = "customers")
public class Customer extends Company {

    public Customer() {
        super();
    }

    public Customer(String companyName, String passwrod, String street, String city, String zipCode) {
        super(companyName, passwrod, street, city, zipCode);
    }
}
