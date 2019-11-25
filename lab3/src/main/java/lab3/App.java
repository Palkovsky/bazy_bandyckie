package lab3;

import lab3.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class App {
    public static void main(String[] args) throws IOException {
        seed();

        Company c = logIn();
        if(c != null) {
            lines("Type 'help' to check available commands.");
            if(c.isSupplier()) {
                supplierMode(c.getCompanyName());
            } else {
                customerMode(c.getCompanyName());
            }
        }
    }

    private static void supplierMode(String companyName) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner =
                new Scanner(reader);
        EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();
        EntityTransaction etx;
        Supplier supplier = em.find(Supplier.class, companyName);

        switch (reader.readLine()) {
            case "?":
            case "help":
                lines(
                        "?/help - displays this message",
                        "whoami - info about current user",
                        "lsprod - lists products managed by current user",
                        "modprod - allows creating/restocking products",
                        "lsord - lists orders awaiting finalization",
                        "fin - order finalization menu",
                        "q - quit"
                );
                break;
            case "whoami":
                lines("Supplier " + supplier.getCompanyName(),
                        supplier.getAddress());
                break;
            case "lsprod":
                Set<Product> suppliedProducts = supplier.getSuppliedProducts();
                if(suppliedProducts.isEmpty()) {
                    lines("No products supplied by yourself.");
                    break;
                }
                for(Product product : suppliedProducts) {
                    lines(String.format("%s | %d",
                                    product.getName(),
                                    product.getUnitsOnStock()),
                            "---------");
                }
                break;
            case "modprod":
                lines("Product name: ");
                String prodName = reader.readLine().trim();
                if(prodName.isEmpty()) {
                    lines("Invalid name.");
                    break;
                }

                System.out.println("Units in stock: ");
                int unitsInStock = scanner.nextInt();
                if(unitsInStock < 0) {
                    lines("Value must be greater or equal to zero.");
                    break;
                }

                Product product = em.find(Product.class, prodName);
                boolean newProduct = (product == null);

                // When creating new.
                if(newProduct) {
                    product = new Product(prodName, unitsInStock);
                    supplier.getSuppliedProducts().add(product);
                    product.setSupplier(supplier);

                    lines("No such product in database. Are you sure you want to create one?(Y/N): ");
                    if(!reader.readLine().toLowerCase().equals("y")) {
                        lines("Aborting...");
                        break;
                    }
                }
                // When not owner
                else if(!product.getSupplier().getCompanyName().equals(supplier.getCompanyName())) {
                    lines("AUTHORIZATION ERROR",
                            "You cannot manage products of different suppliers.");
                    break;
                }
                // When updating existing
                else {
                    lines("UPDATED TO " + unitsInStock);
                    product.setUnitsOnStock(unitsInStock);
                }

                etx = em.getTransaction();
                etx.begin();
                em.persist(product);
                etx.commit();
                break;
            case "lsord":
                Set<SingleOrder> pending = supplier.getPendingOrders();
                if(pending.isEmpty()) {
                    lines("No pending orders.");
                    break;
                }
                System.out.println("Id | Product | Qty");
                for(SingleOrder order : pending) {
                    lines(String.format("%d | %s | %d",
                            order.getId(),
                            order.getProduct().getName(),
                            order.getQuantity()),
                            "---------");
                }
                break;
            case "fin":
                lines("Order Id: ");
                int orderId = scanner.nextInt();

                SingleOrder order = em.find(SingleOrder.class, orderId);
                if(order == null) {
                    lines("Unable to find order with this identifier.", "Aborting...");
                    break;
                }

                lines(String.format("%d | %s | %d",
                        order.getId(),
                        order.getProduct().getName(),
                        order.getQuantity()),
                        "Are you sure you want to finalize this order? [Y/N]");

                if(!reader.readLine().toLowerCase().equals("y")) {
                    lines("Aborting...");
                    break;
                }

                order.setFinalized(true);
                etx = em.getTransaction();
                etx.begin();
                em.persist(order);
                etx.commit();
                break;
            case "quit":
            case "q":
                em.close();
                return;
            default:
                lines("Unrecognized command.",
                        "Type 'help' to check available commands.");
                break;
        }

        em.close();
        supplierMode(companyName);
    }

    private static void customerMode(String companyName) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner =
                new Scanner(reader);
        EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();
        EntityTransaction etx;
        Customer customer = em.find(Customer.class, companyName);

        switch (reader.readLine()) {
            case "?":
            case "help":
                lines(
                        "?/help - displays this message",
                        "whoami - info about current user",
                        "lsprod - lists all available products",
                        "lsord - lists all orders made by this user and their statuses",
                        "cartls - list current cart contents",
                        "cartadd - adding new product to cart",
                        "cartpurge - remove contents of cart",
                        "cartfin - finalize cart",
                        "q - quit"
                );
                break;
            case "whoami":
                lines("Customer " + customer.getCompanyName(),
                        customer.getAddress());
                break;
            case "lsprod":
                Set<Product> availableProducts = Product.getAvailableProducts(em);
                if(availableProducts.isEmpty()) {
                    lines("No products at this moment.");
                    break;
                }
                lines("Name | Qty");
                for(Product product : availableProducts) {
                    lines(String.format("%s | %d",
                            product.getName(),
                            product.getUnitsOnStock()),
                            "---------");
                }
                break;
            case "lsord":
                List<BatchOrder> batchOrders = customer.getOrders();
                for(BatchOrder batchOrder : batchOrders) {
                    Set<SingleOrder> singleOrders = batchOrder.getSingleOrders();
                    lines(String.format("=== BATCH ORDER %d ===", batchOrder.getId()));
                    for(SingleOrder singleOrder : singleOrders) {
                        lines(String.format("%s | %d ---> %s",
                                singleOrder.getProduct().getName(),
                                singleOrder.getQuantity(),
                                (singleOrder.isFinalized()) ? "FINALIZED" : "PENDING"));
                    }
                }
                break;
            case "cartadd":
                break;
            case "cartpurge":
                break;
            case "cartfin":
                break;
            case "quit":
            case "q":
                em.close();
                return;
            default:
                lines("Unrecognized command.",
                        "Type 'help' to check available commands.");
                break;
        }

        em.close();
        customerMode(companyName);
    }

    private static void lines(String ... args) {
        for(String line : args) {
            System.out.println(line);
        }
    }

    private static Company logIn() throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Username: ");
        String username = reader.readLine().trim();
        System.out.println("Password: ");
        String password = reader.readLine().trim();

        EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();
        Company comp = Company.getByCredentials(em, username, password);
        em.close();

        if(comp != null) {
            return comp;
        }

        System.out.println("Invalid credentials. Try again.");
        return logIn();
    }

    private static void seed() {
        EntityManager em = JPAUtils.getEntityManagerFactory().createEntityManager();

        // Companies
        Customer c1 = new Customer("Januszpol", "123", "Jesionowa", "Białystok", "12-345");
        Customer c2 = new Customer("BIO", "abc", "Szkolna", "Białystok", "12-345");
        Supplier s1 = new Supplier("Coca-Cola", "qwerty", "1234 5678 9012 3456", "Krótka", "Warszawa", "21-376");

        // Products
        Product p1 = new Product("Czipsy", 10);
        Product p2 = new Product("Koka Kola", 100);
        p1.setSupplier(s1);
        p2.setSupplier(s1);

        // Orders
        BatchOrder batchOrder = new BatchOrder(c1);
        batchOrder.makeOrder(p1, 2);
        batchOrder.makeOrder(p2, 5);

        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.persist(c1);
        em.persist(c2);
        em.persist(s1);
        em.persist(p1);
        em.persist(p2);
        em.persist(batchOrder);
        etx.commit();
        em.close();
    }
}