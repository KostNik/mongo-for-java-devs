package com.edu.mongo.samples;

import com.google.common.collect.ImmutableList;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

/**
 * Created by Kostiuk Nikita
 */
@Slf4j
public class HelloWorldMongo {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));

        MongoIterable<String> databaseNames = mongoClient.listDatabaseNames();
        log.info("Existed dbs: {}", ImmutableList.copyOf(databaseNames));

        MongoDatabase database = mongoClient.getDatabase(databaseNames.first());
        log.info("Get db: {}", database.getName());

        log.info("Get collections: {}", ImmutableList.copyOf(database.listCollectionNames()));

        String basicCollection = "basicCollection";
        database.createCollection(basicCollection);
        log.info("Get collections after create one: {}", ImmutableList.copyOf(database.listCollectionNames()));


        MongoCollection<Document> mongoCollection = database.getCollection(basicCollection);

        Document document = new Document();
        document.append("Hello", "World!!!");

        log.info("Insert into : {} the document {}", basicCollection, document);
        mongoCollection.insertOne(document);

        MongoCollection<Document> received = database.getCollection(basicCollection);

        log.info("Show all: {} from : {}", ImmutableList.copyOf(received.find()), basicCollection);

        mongoCollection.drop();
    }
}
