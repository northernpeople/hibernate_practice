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
        contact.setId(save(contact));
        
        Address a = new Address("2345 Amsterdam ave.");
        a.setId(save(a));
        
        System.out.println("\n address before saving contact: "+contact.getAddress());
        
        contact.setAddress(a);
        save(contact);
        
        contact = findContactById(contact.getId());
        
        System.out.println("\n address after save and fetch of contact: "+contact.getAddress());

        
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

    private static <T> long save(T contact) {
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