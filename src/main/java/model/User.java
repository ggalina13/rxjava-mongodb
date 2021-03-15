package model;
import org.bson.Document;

public class User {
    public final int id;
    public final String name;
    public final String login;
    public final Currency currency;

    public User(Document doc) {
        this(doc.getInteger("_id"),
                doc.getString("name"),
                doc.getString("login"),
                Currency.valueOf(doc.getString("currency")));
    }

    public User(int id, String name, String login, Currency currency) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", currency='" + currency + '\'' +
                '}' + '\n';
    }
}
