package ru.malofeev.http;

import io.reactivex.netty.protocol.http.server.HttpServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.malofeev.configuration.Config;

public class RxNettyHttpServer {

    private static final int PORT = 8080;

    public static void run() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        HttpHandler handler = context.getBean("httpHandler", HttpHandler.class);
        HttpServer
                .newServer(PORT)
                .start((req, resp) -> {

                    return resp.writeString(handler.getResponse(req));

                })
                .awaitShutdown();
    }
}
