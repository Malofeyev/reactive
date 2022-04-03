package ru.malofeev.dao;

import com.mongodb.client.model.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.malofeev.models.Currency;
import rx.Observable;

@Component
public class CurrencyDAO {
    private final CatalogDataBase database;

    @Autowired
    public CurrencyDAO(CatalogDataBase database) {
        this.database = database;
    }

    public Observable<Currency> getCurrencyRate() {
        return database.getCurrencyRate()
                .find()
                .toObservable()
                .map(Currency::new);
    }

    public Observable<Currency> getCurrencyRateByName(String name) {
        return database.getCurrencyRate()
                .find(Filters.eq("name", name))
                .toObservable()
                .map(Currency::new);
    }
}
