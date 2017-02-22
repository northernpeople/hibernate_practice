package practice;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import practice.model.Address;
import practice.model.Contact;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args) {
        Contact contact = new Contact();
        contact.setName("John");
        Address a = new Address("2345 Amsterdam ave.");
        Address b = new Address("asdfas2123451234 m ave.");
        contact.getAddresses().add(a);
        contact.getAddresses().add(b);
        long id = save(contact);
        
        System.out.println("__________________________" +  fetchAllContacts().size());

        fetchAllContacts().stream().forEach(System.out::println);

        System.out.println("__________________________");

        Contact c = findContactById(id);
        c.getAddresses().stream().map( addr -> addr.getAddress()).forEach(System.out::println);

        c.setName("Boba");

        update(c);
        
        fetchAllContacts().stream().forEach(System.out::println);

        c = findContactById(1);

        delete(c);
      
        fetchAllContacts().stream().forEach(System.out::println);
        
        
System.out.println("karamba");
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
    private static Set<Contact> fetchAllContacts() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Create Criteria
        Criteria criteria = session.createCriteria(Contact.class);

        // Get a list of Contact objects according to the Criteria object
        Set<Contact> contacts = new HashSet<>(criteria.list());

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
        long id = (Long) session.save(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();

        return id;
    }
}