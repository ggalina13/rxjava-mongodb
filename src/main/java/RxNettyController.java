import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import org.bson.Document;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class RxNettyController {
    private final ReactiveMongoDriver reactiveMongoDriver;

    public RxNettyController(ReactiveMongoDriver reactiveMongoDriver) {
        this.reactiveMongoDriver = reactiveMongoDriver;
    }

    public Observable<Void> processRequest(HttpServerRequest<ByteBuf> httpServerRequest, HttpServerResponse<ByteBuf> httpServerResponse) {
        Observable<String> result = null;
        if (httpServerRequest.getDecodedPath().contains("register")) {
            result = registerUser(httpServerRequest);
        }
        if (httpServerRequest.getDecodedPath().contains("add_product")) {
            result = addProduct(httpServerRequest);
        }
        if (httpServerRequest.getDecodedPath().contains("show_products")) {
            result = showProductsToUser(httpServerRequest);
        }
        if (httpServerRequest.getDecodedPath().contains("show_users")) {
            result = showUsers();
        }
        if (result == null) {
            return Observable.error(new NullPointerException("Path is incorrect"));
        }
        return httpServerResponse.writeStringAndFlushOnEach(result);
    }

    public Observable<String> registerUser(HttpServerRequest<ByteBuf> httpServerRequest) {
        Map<String, List<String>> queryParameters = httpServerRequest.getQueryParameters();
        Document userDocument = new Document("_id", Integer.parseInt(queryParameters.get("_id").get(0)))
                .append("name", queryParameters.get("name").get(0))
                .append("login", queryParameters.get("login").get(0))
                .append("currency", queryParameters.get("currency").get(0));
        return reactiveMongoDriver.addUser(userDocument).map(Object::toString);
    }

    private Observable<String> addProduct(HttpServerRequest<ByteBuf> httpServerRequest) {
        Map<String, List<String>> queryParameters = httpServerRequest.getQueryParameters();
        Document productDocument = new Document("_id", Integer.parseInt(queryParameters.get("_id").get(0)))
                .append("name", queryParameters.get("name").get(0))
                .append("price_dollars", Double.parseDouble(queryParameters.get("price_dollars").get(0)));
        return reactiveMongoDriver.addProduct(productDocument).map(Object::toString);
    }

    private Observable<String> showProductsToUser(HttpServerRequest<ByteBuf> httpServerRequest) {
        Map<String, List<String>> queryParameters = httpServerRequest.getQueryParameters();
        List<String> stringId = queryParameters.get("_id");
        int id = -1;
        if (stringId != null) {
            id = Integer.parseInt(stringId.get(0));
        }
        return reactiveMongoDriver.showProductsToUser(id).map(Object::toString);
    }

    private Observable<String> showUsers() {
        return reactiveMongoDriver.getAllUsers().map(Object::toString);
    }
}
