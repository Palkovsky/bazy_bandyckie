package lab3;

import lab3.model.Customer;
import lab3.model.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class App {
    public static void main(String[] args) {
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