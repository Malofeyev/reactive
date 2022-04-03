package ru.malofeev.models;

import org.bson.Document;

public class Person implements Model{
    private final int id;
    private final String currency;

    public Person(int id, String currency) {
        this.id = id;
        this.currency = currency;
    }

    public Person(Document document) {
        id = document.getInteger("id");
        currency = document.getString("currency");
    }

    public int getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                '}';
    }

    @Override
    public Document toDocument() {
        return new Document()
                .append("id", id)
                .append("currency", currency);
    }
}
