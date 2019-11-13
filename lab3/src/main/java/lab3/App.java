package lab3;

import lab3.model.BatchOrder;
import lab3.model.Customer;
import lab3.model.Product;
import lab3.model.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class App {
    public static void main(String[] args) {
        addManyCompanies();
        addManyProducts();

        EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();

        Customer customer = em.getReference(Customer.class, "Januszpol");
        Product p1 = em.getReference(Product.class, "Kola");
        Product p2 = em.getReference(Product.class, "Czipsy");

        System.out.println(customer);

        EntityTransaction etx = em.getTransaction();
        etx.begin();

        BatchOrder batchOrder = new BatchOrder(customer);
        batchOrder.makeOrder(p1, 21);
        em.persist(batchOrder);

        batchOrder.makeOrder(p2, 40);
        em.persist(batchOrder);

        etx.commit();
        em.close();

        batchOrder.summary();
    }

    private static void addManyProducts() {
        EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();

        Product p1 = new Product("Kola", 100);
        Product p2 = new Product("Czipsy", 50);

        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.persist(p1);
        em.persist(p2);
        etx.commit();
        em.close();
    }

    private static void addManyCompanies() {
        EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();

        Customer c1 = new Customer("Januszpol", 100, "Jesionowa", "Białystok", "12-345");
        Customer c2 = new Customer("Biuro Interwencji Obywatelskich", 0, "Szkolna", "Białystok", "12-345");
        Supplier s1 = new Supplier("Coca-Cola", "1234 5678 9012 3456", "Krótka", "Warszawa", "21-376");

        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.persist(c1);
        em.persist(c2);
        em.persist(s1);
        etx.commit();
        em.close();
    }
}