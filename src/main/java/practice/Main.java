package practice;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import practice.model.Contact;

import java.util.List;

public class Main {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args) {
        Contact contact = new Contact();
        contact.setName("John");
        contact.getTags().add("tag1");
        contact.getTags().add("tag2");
        long id = save(contact);
        
        fetchAllContacts().stream().forEach(System.out::println);


        Contact c = findContactById(id);
        c.setName("Boba");
        c.getTags().add("floppity");
        c.getTags().remove("tag1");
        update(c);
        
        fetchAllContacts().stream().forEach(System.out::println);
        
        
        c = findContactById(id);
        c.getTags().clear();
        update(c);
        
        fetchAllContacts().stream().forEach(System.out::println);

        
        
        
        System.out.println("done");
}

    private static Contact findContactById(long id) {
        Session session = sessionFactory.openSession();
        Contact contact = session.get(Contact.class,id);
        session.close();
        return contact;
    }

    private static void delete(Contact contact) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to update the contact
        session.delete(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    private static void update(Contact contact) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to update the contact
        session.update(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    @SuppressWarnings("unchecked")
    private static List<Contact> fetchAllContacts() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Create Criteria
        Criteria criteria = session.createCriteria(Contact.class);

        // Get a list of Contact objects according to the Criteria object
        List<Contact> contacts = criteria.list();

        // Close the session
        session.close();

        return contacts;
    }

    private static long save(Contact contact) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to save the contact
        long id = (long )session.save(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();

        return id;
    }
}