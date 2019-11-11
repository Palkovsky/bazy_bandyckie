package lab3;


import lab3.model.Category;
import lab3.model.Invoice;
import lab3.model.Product;
import lab3.model.Supplier;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;


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

            i1.getProducts().add(p1);
            p1.getInvoices().add(i1);
            i1.getProducts().add(p2);
            p2.getInvoices().add(i1);
            i1.getProducts().add(p3);
            p3.getInvoices().add(i1);

            i2.getProducts().add(p2);
            p2.getInvoices().add(i2);
            i2.getProducts().add(p4);
            p2.getInvoices().add(i2);

            session.save(p1);
            session.save(p2);
            session.save(p3);
            session.save(p4);
            session.save(i1);
            session.save(i2);

            tx.commit();
        }
    }

    private static Category getProductCategory(Product product) {
        return product.getCategory();
    }

    private static List<Product> getProductsFromCategory(Category cat) {
        return cat.getProducts();
    }

    private static List<Category> addCategoriesWithProducts() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            List<Category> categories = new ArrayList<>();

            Category cat1 = new Category("Picie");
            cat1.getProducts().add(new Product("Kola", 21));
            cat1.getProducts().add(new Product("Pepsi", 37));
            cat1.getProducts().add(new Product("Fanta", 666));

            Category cat2 = new Category("Jedzenie");
            cat2.getProducts().add(new Product("Czipsy", 10));
            cat2.getProducts().add(new Product("Lizaki", 20));
            cat2.getProducts().add(new Product("Ryby", 30));

            Category cat3 = new Category("Elektronika") ;
            cat3.getProducts().add(new Product("MP3", 3));
            cat3.getProducts().add(new Product("MP4", 4));
            cat3.getProducts().add(new Product("MP5", 5));

            categories.add(cat1);
            categories.add(cat2);
            categories.add(cat3);
            for(Category cat : categories) {
                for(Product product : cat.getProducts()) {
                    product.setCategory(cat);
                    session.save(product);
                }
                session.save(cat);
            }

            tx.commit();
            return categories;
        }
    }

    private static Set<Product> addManyProducts() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Set<Product> products = new HashSet<>();
            products.add(new Product("Czipsy", 100));
            products.add(new Product("Kola", 10));
            products.add(new Product("Ryby", 5));
            products.add(new Product("Lizaki", 50));
            for(Product product : products) {
                session.save(product);
            }
            tx.commit();
            return products;
        }
    }

    private static Supplier addNewSupplier() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Supplier supplier = new Supplier("Coca-Cola", "Jesionowa", "Warszawa");
            session.save(supplier);
            tx.commit();
            return supplier;
        }
    }

    private static Product addNewProduct() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Product product = new Product("Czipsy", 100);
            Transaction tx = session.beginTransaction();
            session.save(product);
            tx.commit();
            return product;
        }
        /*
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
        */

        /*
        public static void main(String[] args) {
        Set<Product> products = addManyProducts();
        Supplier s = addNewSupplier();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            for(Product p : products) {
                s.getSuppliedProducts().add(p);
                p.setSupplier(s);
                session.update(p);
            }
            session.update(s);
            tx.commit();
        }
    }
    */
        /*
            public static void main(String[] args) {
        Supplier supplier = addNewSupplier();
        List<Category> categories = addCategoriesWithProducts();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            for(Category cat : categories) {
                for (Product product : cat.getProducts()) {
                    product.setSupplier(supplier);
                    supplier.getSuppliedProducts().add(product);
                    session.update(product);
                }
            }
            session.update(supplier);
            tx.commit();
        }
    }
         */
    }
}
