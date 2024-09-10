package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Main {

    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";


        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("firstData");
            MongoCollection<Document> collection = database.getCollection("mycollection");
            System.out.println("Connection Successful!");

            Document document = new Document("name", "talha");
            document.append("age", 20);
            document.append("gender", "male");
            collection.insertOne(document);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Bağlantı hatası: " + e.getMessage());
        }
    }
}
