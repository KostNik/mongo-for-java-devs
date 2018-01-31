package com.edu.mongo.samples;

import com.google.common.collect.ImmutableList;
import com.mongodb.ClientSessionOptions;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.GridFSUploadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.*;
import java.nio.channels.Channels;

/**
 * Created by Kostiuk Nikita
 */
@Slf4j
public class LargeDataPersistTest {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
        MongoDatabase database = mongoClient.getDatabase("course");

        GridFSBucket bucket = GridFSBuckets.create(database);
        bucket.drop();

        InputStream resourceAsStream = new BufferedInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/test-file.pdf"));
        Document document = new Document();
        document.append("vendor", "pivotal");
        GridFSUploadOptions options = new GridFSUploadOptions();
        options.metadata(document);

        ObjectId objectId = bucket.uploadFromStream("test-file.pdf", resourceAsStream, options);


        log.info("After upload: {}", objectId);
        GridFSFindIterable gridFSFiles = bucket.find();

        log.info("Files {}", ImmutableList.copyOf(gridFSFiles));
    }
}
