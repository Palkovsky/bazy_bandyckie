package lab3.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int categoryId;
    private String name;

    @OneToMany
    private List<Product> products;

    public Category(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public Category() { }

    public List<Product> getProducts() {
        return products;
    }
}
