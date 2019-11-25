package lab3.model;

import javax.persistence.*;

@Entity(name = "companies")
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class Company {
    @Id
    private String companyName;
    private String street;
    private String city;
    private String zipCode;
    private String password;

    public Company() { }

    public Company(String companyName, String password, String street, String city, String zipCode) {
        this.companyName = companyName;
        this.password = password;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public boolean isSupplier() {
        return getClass().equals(Supplier.class);
    }

    public boolean isCustomer() {
        return getClass().equals(Customer.class);
    }

    public static Company getByCredentials(EntityManager em, String companyName, String password) {
        TypedQuery<Company> q = em.createQuery(
                "SELECT c FROM companies c WHERE c.companyName = :username AND c.password = :pass",
                Company.class);

        q.setParameter("username", companyName);
        q.setParameter("pass", password);

        // .getSingleResults() throws Exception when result not found
        try {
            return q.getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return street + ", " + city + ", " + zipCode;
    }
}
