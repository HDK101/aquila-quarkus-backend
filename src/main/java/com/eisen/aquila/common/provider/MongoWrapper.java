package com.eisen.aquila.common.provider;

import java.util.List;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.client.MongoDatabase;

public interface MongoWrapper {

    MongoDatabase getDatabase();

    Document insertOneToCollection(String collection, Object object) throws JsonProcessingException;

    <T> List<T> findInCollection(String collection, String field, Object value, Class<T> clazz)
            throws JsonMappingException, JsonProcessingException;

    void findAndReplaceTo(String collection, String field, Object value, Object updateTo)
            throws JsonProcessingException;

    <T> List<T> collectionMapTo(String collection, Class<T> clazz)
            throws JsonMappingException, JsonProcessingException;

}