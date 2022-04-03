package ru.malofeev.dao;

import com.mongodb.client.model.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.malofeev.models.Person;

import rx.Observable;

@Component
public class PersonDAO {
    private final CatalogDataBase database;

    @Autowired
    public PersonDAO(CatalogDataBase database) {
        this.database = database;
    }

    public Observable<Person> getPersons() {
        return database.getPersons()
                .find()
                .toObservable()
                .map(Person::new);
    }

    public Observable<Person> getPerson(int id) {
        return database.getPersons()
                .find(Filters.eq("id", id))
                .toObservable()
                .map(Person::new);
    }

    public Observable<String> createPerson(int id, String currency) {
        Person person = new Person(id, currency);
        return UtilDAO.createModel(database.getPersons(), person);
    }

}
