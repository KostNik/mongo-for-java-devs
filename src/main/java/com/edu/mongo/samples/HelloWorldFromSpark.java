package com.edu.mongo.samples;

import spark.Spark;

/**
 * Created by Kostiuk Nikita
 */
public class HelloWorldFromSpark {

    public static void main(String[] args) {
        Spark.get("/", (request, response) -> "Hello World from Spark");
    }
}
