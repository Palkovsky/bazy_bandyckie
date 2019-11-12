package lab3.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name = "companies")
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class Company {
    @Id
    private String companyName;
    private String street;
    private String city;
    private String zipCode;

    public Company() { }

    public Company(String companyName, String street, String city, String zipCode) {
        this.companyName = companyName;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }
}
