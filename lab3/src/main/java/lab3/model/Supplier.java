package lab3.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name="suppliers")
public class Supplier extends Company{
    private String bankAccountNumber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "supplier")
    private Set<Product> suppliedProducts;

    public Supplier() {
        super();
    }

    public Supplier(String companyName, String password,  String bankAccountNumber, String street, String city, String zip) {
        super(companyName, password, street, city, zip);
        this.bankAccountNumber = bankAccountNumber;
        this.suppliedProducts = new HashSet<>();
    }

    public void addProduct(Product product) {
        suppliedProducts.add(product);
    }

    public Set<Product> getSuppliedProducts() {
        return suppliedProducts;
    }

    public Set<SingleOrder> getPendingOrders() {
        return suppliedProducts.stream()
                .flatMap((product) -> product.getOrders().stream())
                .filter((order) -> !order.isFinalized())
                .collect(Collectors.toSet());
    }
}