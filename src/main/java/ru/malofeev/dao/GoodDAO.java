package ru.malofeev.dao;

import com.mongodb.client.model.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.malofeev.models.Good;
import rx.Observable;

@Component
public class GoodDAO {
    private final CatalogDataBase database;

    @Autowired
    public GoodDAO(CatalogDataBase database) {
        this.database = database;
    }

    public Observable<Good> getGoods() {
        return database.getGoods()
                .find()
                .toObservable()
                .map(Good::new);
    }

    public Observable<Good> getGood(int id) {
        return database.getGoods()
                .find(Filters.eq("id", id))
                .toObservable()
                .map(Good::new);
    }

    public Observable<String> createGood(int id, int price) {
        Good good = new Good(id, price);
        return UtilDAO.createModel(database.getGoods(), good);
    }
}
