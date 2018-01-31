package com.edu.mongo.samples;

import com.google.common.collect.ImmutableList;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * Created by Kostiuk Nikita
 */
@Slf4j
public class DuplicateInsertTest {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));

        MongoDatabase database = mongoClient.getDatabase("course");

        MongoCollection<Document> mongoCollection = database.getCollection("insertTest");
        mongoCollection.drop();

        Document document = new Document("name", "Olga").append("company", "10gen");
        log.info("Before first insert document: {}", document);
        mongoCollection.insertOne(document);
        log.info("After first insert {}", ImmutableList.copyOf(mongoCollection.find()));
        log.info("After first insert document: {}", document);
        document.remove("_id");

//        mongoCollection.updateOne(Filters.exists("_id"), Updates.set("_id", "empty"));
        log.info("After remove ID {}", ImmutableList.copyOf(mongoCollection.find()));
        log.info("After remove ID document: {}", document);
        mongoCollection.insertOne(document);
        log.info("After second insert {}", ImmutableList.copyOf(mongoCollection.find()));
        log.info("After second insert document: {}", document);
    }
}
