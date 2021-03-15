import com.mongodb.rx.client.*;
import model.Currency;
import model.Product;
import model.User;
import org.bson.Document;
import rx.Observable;

public class ReactiveMongoDriver {

    private MongoClient client;

    public ReactiveMongoDriver() {
        this.client = createMongoClient();
    }

    private MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    private MongoCollection<Document> getUserCollection() {
        return client.getDatabase("market").getCollection("users");
    }

    private MongoCollection<Document> getProductCollection() {
        return client.getDatabase("market").getCollection("products");
    }

    public Observable<User> getAllUsers() {
        MongoCollection<Document> userCollection = this.getUserCollection();
        return userCollection.find().toObservable().map(User::new);
    }

    public Observable<Product> getAllProducts() {
        MongoCollection<Document> productCollection = this.getProductCollection();
        return productCollection.find().toObservable().map(Product::new);
    }

    public Observable<String> showProductsToUser(Integer _id) {
        return getAllUsers()
                .filter(user -> user.id == _id)
                .firstOrDefault(new User(-1, null, null, Currency.dollars))
                .flatMap(user -> this.getAllProducts()
                        .map(product -> product.display(user.currency)));

    }

    public Observable<Success> addUser(Document document) {
        return getUserCollection().insertOne(document);
    }

    public Observable<Success> addProduct(Document document) {
        return getProductCollection().insertOne(document);
    }
}
