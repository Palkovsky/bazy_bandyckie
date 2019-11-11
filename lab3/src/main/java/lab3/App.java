package lab3;


import lab3.model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Scanner;


public class App {

    public static void main(String[] args) {
        addNewProduct();
    }

    private static void addNewProduct() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Scanner inputScanner = new Scanner(System.in);

            System.out.println("Product Name:" );
            String prodName = inputScanner.nextLine();
            System.out.println("Units in Stock:");
            int prodUnits = inputScanner.nextInt();

            Product product = new Product(prodName, prodUnits);
            Transaction tx = session.beginTransaction();
            session.save(product);
            tx.commit();
        }
    }
}
