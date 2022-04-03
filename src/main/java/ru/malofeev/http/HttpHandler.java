package ru.malofeev.http;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.malofeev.dao.CurrencyDAO;
import ru.malofeev.dao.GoodDAO;
import ru.malofeev.dao.PersonDAO;
import ru.malofeev.models.Currency;
import ru.malofeev.models.Good;
import ru.malofeev.models.Person;
import rx.Observable;

import java.util.List;
import java.util.Map;

@Component
public class HttpHandler {
    private final PersonDAO personDAO;
    private final GoodDAO goodDAO;
    private final CurrencyDAO currencyDAO;

    @Autowired
    public HttpHandler(PersonDAO personDAO, GoodDAO goodDAO, CurrencyDAO currencyDAO) {
        this.personDAO = personDAO;
        this.goodDAO = goodDAO;
        this.currencyDAO = currencyDAO;
    }

    static <T> Observable<String> makeHTML(Observable<T> response) {
        return response.map(resp->String.format("<p>%s</p>\n", resp.toString()));
    }

    public <T> Observable<String> getResponse(HttpServerRequest<T> request) {
        String path = request.getDecodedPath().substring(1);
        Map<String, List<String>> query = request.getQueryParameters();
        switch (path) {
            case ("people"):
                return makeHTML(personDAO.getPersons());
            case ("goods"):
                return makeHTML(goodDAO.getGoods().map(Good::toString));
            case ("currency"):
                return makeHTML(currencyDAO.getCurrencyRate());
            case ("people/new"):
                return makeHTML(createPerson(query));
            case ("goods/new"):
                return makeHTML(createGood(query));
            case ("goods_for_person"):
                return makeHTML(getGoodsForPerson(query));
        }
        return Observable.just("Unknown path");
    }

    private Observable<Good> getGoodsInCurrency(int rate) {

        return goodDAO.getGoods().map(g->new Good(g.getId(), g.getPrice() * rate));
    }

    private Observable<String> getGoodsForPerson(Map<String, List<String>> query) {
        if (!query.containsKey("id") || query.get("id").isEmpty()) {
            return Observable.just("id is missing");
        }
        int id = Integer.parseInt(query.get("id").get(0));
        Observable<Person> person = personDAO.getPerson(id);
        Observable<Currency> currency = person.flatMap(p -> currencyDAO.getCurrencyRateByName(p.getCurrency()));
        Observable<Good> goods = currency.flatMap(c->getGoodsInCurrency(c.getRate()));
        return goods.map(Good::toString);
    }

    private Observable<String> createPerson(Map<String, List<String>> query) {
        if (!query.containsKey("id") || query.get("id").isEmpty()) {
            return Observable.just("id is missing");
        }
        if (!query.containsKey("currency") || query.get("currency").isEmpty()) {
            return Observable.just("currency is missing");
        }
        int id = Integer.parseInt(query.get("id").get(0));
        String currency = query.get("currency").get(0);

        return personDAO.createPerson(id, currency);
    }

    private Observable<String> createGood(Map<String, List<String>> query) {
        if (!query.containsKey("id") || query.get("id").isEmpty()) {
            return Observable.just("id is missing");
        }
        if (!query.containsKey("price") || query.get("price").isEmpty()) {
            return Observable.just("price is missing");
        }
        int id = Integer.parseInt(query.get("id").get(0));
        int price = Integer.parseInt(query.get("price").get(0));

        return goodDAO.createGood(id, price);
    }

}
