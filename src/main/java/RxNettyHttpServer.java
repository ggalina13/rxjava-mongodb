import io.reactivex.netty.protocol.http.server.HttpServer;

public class RxNettyHttpServer {

    public static void main(final String[] args) {
        ReactiveMongoDriver reactiveMongoDriver = new ReactiveMongoDriver();
        RxNettyController rxNettyController = new RxNettyController(reactiveMongoDriver);
        HttpServer
                .newServer(8080)
                .start(rxNettyController::processRequest)
                .awaitShutdown();
    }
}