package lab3.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String string;
    private String city;
    private String zipCode;

    public Address() { }

    public Address(String string, String city, String zipCode) {
        this.string = string;
        this.city = city;
        this.zipCode = zipCode;
    }
}
