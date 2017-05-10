package ac.uk.abdn.foobs.db;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
   private static final SessionFactory sessionFactory;

   static {
      try {
         StandardServiceRegistry standardRegistry = 
            new StandardServiceRegistryBuilder().configure().build();
         Metadata metadata = 
            new MetadataSources(standardRegistry).getMetadataBuilder().build();
         sessionFactory = metadata.getSessionFactoryBuilder().build();
      } catch (Throwable e) {
         throw new ExceptionInInitializerError(e);
      }
   }

   public static SessionFactory getSessionFactory() {
      return sessionFactory;
   }
}
