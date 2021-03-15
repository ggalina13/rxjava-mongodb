package model;
import org.bson.Document;

public class Product {
    public final int id;
    public final String name;
    public final double price_dollars;


    public Product(Document doc) {
        this(doc.getInteger("_id"), doc.getString("name"), doc.getDouble("price_dollars"));
    }

    public Product(int id, String name, double price_dollars) {
        this.id = id;
        this.name = name;
        this.price_dollars = price_dollars;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price_dollars='" + price_dollars + '\'' +
                '}' + '\n';
    }

    public String display(Currency currency) {
        return "id: " + id +
                ", name: " + name +
                ", price: " + currency.dollarExchangeRate() * price_dollars +
                " " + currency.name()  + '\n';
    }
}
