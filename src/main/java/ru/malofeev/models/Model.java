package ru.malofeev.models;

import org.bson.Document;

public interface Model {
    Document toDocument();
}
