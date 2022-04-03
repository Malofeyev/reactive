package ru.malofeev.models;

import org.bson.Document;

public class Currency implements Model {
    private enum CurrencyType {usd, rub, eur};
    private final CurrencyType name;
    private final int rate;

    public Currency(String name, int rate) {
        this.name = CurrencyType.valueOf(name);
        this.rate = rate;
    }

    public Currency(Document document) {
        name = CurrencyType.valueOf(document.getString("name"));
        rate = document.getInteger("rate");
    }

    public String getName() {
        return name.toString();
    }

    public int getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "name='" + name + '\'' +
                ", rate=" + rate +
                '}';
    }

    @Override
    public Document toDocument() {
        return new Document()
                .append("name", name)
                .append("rate", rate);
    }
}
