package ru.malofeev.dao;


import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import org.bson.Document;
import ru.malofeev.models.Model;
import rx.Observable;

public class UtilDAO {
    public static Observable<String> createModel(MongoCollection<Document> collection, Model model) {
        Observable<Success> success = collection
                .insertOne(model.toDocument());
        return success.map(Enum::toString).onErrorReturn(t-> "Couldn't create the model");

    }

}
