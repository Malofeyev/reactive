package ru.malofeev.dao;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;

@Component
public class CatalogDataBase implements Closeable {

    public final MongoClient client;
    private final MongoDatabase mongoDatabase;
    private final MongoCollection<Document> persons;
    private final MongoCollection<Document> goods;
    private final MongoCollection<Document> currencyRate;

    public CatalogDataBase(@Value("${reactive.database.name}") String databaseName,
                           @Value("${reactive.database.person}") String personTableName,
                           @Value("${reactive.database.good}") String goodTableName,
                           @Value("${reactive.database.currency}") String currencyTableName) {
        client = MongoClients.create();
        mongoDatabase = client.getDatabase(databaseName);
        persons = mongoDatabase.getCollection(personTableName);
        goods = mongoDatabase.getCollection(goodTableName);
        currencyRate = mongoDatabase.getCollection(currencyTableName);
    }


    public MongoCollection<Document> getPersons() {
        return persons;
    }

    public MongoCollection<Document> getGoods() {
        return goods;
    }

    public MongoCollection<Document> getCurrencyRate() {
        return currencyRate;
    }


    @Override
    public void close() throws IOException {
        client.close();
    }
}
