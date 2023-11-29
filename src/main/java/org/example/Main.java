package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.hibernate_persistence.PersistenceUnit1;
import org.example.models.Student;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String unitName = "PersistenceUnit1";

        Map<String, String> unitProperties = new HashMap<>();
        unitProperties.put("hibernate.show_sql", "true");
        unitProperties.put("hibernate.hbm2ddl.auto", "update"); // none = no changes, create = drops and creates over and over again, update = creates if not existent

        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new PersistenceUnit1(unitName), unitProperties);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            Student student = new Student();
            student.setId(1L);
            student.setName("Bob");

//            entityManager.persist(student);

//            Student s2 = entityManager.find(Student.class, 1L);
//            Student s3 = entityManager.getReference(Student.class, 1);
            Student s3 = entityManager.find(Student.class, 1L);
            System.out.println("Original: " + s3.getName());
            s3.setName("Marcel");
            entityManager.persist(s3);

            entityManager.refresh(s3);
            System.out.println("After refresh: " + s3.getName());

//            entityManager.remove(s2);

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}















