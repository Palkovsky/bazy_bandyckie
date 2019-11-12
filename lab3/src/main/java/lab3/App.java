package lab3;


import lab3.model.Invoice;
import lab3.model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class App {
    public static void main(String[] args) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Product p1 = new Product("Czipsy", 100);
            Product p2 = new Product("Kola", 10);
            Product p3 = new Product("Ryby", 5);
            Product p4 = new Product("Klocki", 5);

            Invoice i1 = new Invoice();
            Invoice i2 = new Invoice();

            i1.addProduct(p1);
            i1.addProduct(p2);
            i1.addProduct(p3);

            i2.addProduct(p2);
            i2.addProduct(p4);

            session.save(p1);
            session.save(p2);
            session.save(p3);
            session.save(p4);
            session.save(i1);
            session.save(i2);

            tx.commit();
        }
    }
}