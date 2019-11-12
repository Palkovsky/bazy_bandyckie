package lab3;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {

    private static final EntityManagerFactory emf;

    private JPAUtils() {}

    static {
        try {
            emf = Persistence.createEntityManagerFactory("myDatabaseConfig");
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
