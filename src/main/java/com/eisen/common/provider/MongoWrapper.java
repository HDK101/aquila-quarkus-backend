package com.eisen.common.provider;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class MongoWrapper {
    private static final String DATABASE_NAME = "aquila";

    private MongoDatabase mongoDatabase;

    @Inject
    ObjectMapper mapper;

    @Inject
    public void mongoInject(MongoClient mongoClient) {
        mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
    }

    public MongoDatabase getDatabase() {
        return mongoDatabase;
    }

    public Document insertOneToCollection(String collection, Object object) throws JsonProcessingException {
        Document document = Document.parse(mapper.writeValueAsString(object));
        mongoDatabase.getCollection(collection).insertOne(document);
        return document;
    }

    public <T> List<T> findInCollection(String collection, String field, Object value, Class<T> clazz)
            throws JsonMappingException, JsonProcessingException {
        MongoCursor<Document> cursor = mongoDatabase.getCollection(collection).find(eq(field, value)).iterator();

        List<T> listOfTs = new ArrayList<>();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                listOfTs.add(documentMapTo(document, clazz));
            }
        } finally {
            cursor.close();
        }

        return listOfTs;
    }

    public void findAndReplaceTo(String collection, String field, Object value, Object updateTo) throws JsonProcessingException {
        mongoDatabase.getCollection(collection).replaceOne(eq(field, value), Document.parse(mapper.writeValueAsString(updateTo)));
    }

    public <T> List<T> collectionMapTo(String collection, Class<T> clazz)
            throws JsonMappingException, JsonProcessingException {
        List<T> listOfTs = new ArrayList<>();
        MongoCursor<Document> cursor = mongoDatabase.getCollection(collection).find().iterator();
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                listOfTs.add(documentMapTo(document, clazz));
            }
        } finally {
            cursor.close();
        }

        return listOfTs;
    }

    private <T> T documentMapTo(Document document, Class<T> clazz)
            throws JsonMappingException, JsonProcessingException {
        T t = mapper.readValue(document.toJson(), clazz);

        return t;
    }
}