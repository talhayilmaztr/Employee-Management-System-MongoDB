package org.example;

import com.mongodb.client.*;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import java.util.Arrays;
import java.util.List;

public class EmployeeManagementSystem {

    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("companyDB");
            MongoCollection<Document> employeeCollection = database.getCollection("employees");

            // Create
            System.out.println("=== CREATE ===");
            List<Document> employees = Arrays.asList(
                    new Document("name", "Alice Smith")
                            .append("position", "Software Engineer")
                            .append("salary", 75000)
                            .append("department", "Engineering"),
                    new Document("name", "Bob Johnson")
                            .append("position", "Project Manager")
                            .append("salary", 90000)
                            .append("department", "Management"),
                    new Document("name", "Catherine Lewis")
                            .append("position", "HR Specialist")
                            .append("salary", 60000)
                            .append("department", "Human Resources"),
                    new Document("name", "Daniel White")
                            .append("position", "Data Analyst")
                            .append("salary", 65000)
                            .append("department", "Data Science"),
                    new Document("name", "Eva Brown")
                            .append("position", "QA Engineer")
                            .append("salary", 62000)
                            .append("department", "Quality Assurance")
            );

            employeeCollection.insertMany(employees);
            System.out.println("Çalışanlar başarıyla eklendi.");

            // Read
            System.out.println("\n=== READ ===");
            FindIterable<Document> allEmployees = employeeCollection.find();
            for (Document employee : allEmployees) {
                System.out.println(employee.toJson());
            }

            System.out.println("\n=== READ Specific Employees ===");
            FindIterable<Document> engineers = employeeCollection.find(Filters.eq("position", "Software Engineer"));
            for (Document engineer : engineers) {
                System.out.println("Bulunan mühendis: " + engineer.toJson());
            }

            // Update
            System.out.println("\n=== UPDATE ===");
            employeeCollection.updateOne(
                    Filters.eq("name", "Bob Johnson"),
                    Updates.set("salary", 95000)
            );
            Document updatedEmployee = employeeCollection.find(Filters.eq("name", "Bob Johnson")).first();
            if (updatedEmployee != null) {
                System.out.println("Güncellenmiş çalışan: " + updatedEmployee.toJson());
            }


            employeeCollection.updateMany(
                    Filters.eq("department", "Engineering"),
                    Updates.inc("salary", 7500)
            );
            FindIterable<Document> updatedEngineers = employeeCollection.find(Filters.eq("department", "Engineering"));
            for (Document engineer : updatedEngineers) {
                System.out.println("Güncellenmiş mühendis: " + engineer.toJson());
            }

            // Delete
            System.out.println("\n=== DELETE ===");
            employeeCollection.deleteOne(Filters.eq("name", "Catherine Lewis"));
            Document deletedEmployee = employeeCollection.find(Filters.eq("name", "Catherine Lewis")).first();
            if (deletedEmployee == null) {
                System.out.println("Çalışan başarıyla silindi: Catherine Lewis.");
            }

            employeeCollection.deleteMany(Filters.eq("department", "Human Resources"));
            FindIterable<Document> hrEmployees = employeeCollection.find(Filters.eq("department", "Human Resources"));
            if (!hrEmployees.iterator().hasNext()) {
                System.out.println("Human Resources departmanındaki tüm çalışanlar silindi.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
