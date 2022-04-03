package ru.malofeev.models;

import org.bson.Document;

public class Good implements Model {
    private final int id;
    private final int price;

    public Good(int id, int price) {
        this.id = id;
        this.price = price;
    }

    public Good(Document document) {
        id = document.getInteger("id");
        price = document.getInteger("price");
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", price=" + price +
                '}';
    }

    @Override
    public Document toDocument() {
        return new Document()
                .append("id", id)
                .append("price", price);
    }
}
