package Web.Server;

import Logger.Logger;
import Web.Controller.WebController;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer implements Closeable {

    private final HttpServer server;

    public WebServer(WebController controller) {

        try {
            server = HttpServerProvider.provider().createHttpServer(new InetSocketAddress("localhost", 9000), 0);

            server.createContext("/", new SiteServer());
            server.createContext("/data", new DataHandler(controller));

            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            Logger.logErr("Cannot create HttpServer " + e, this);
            throw new RuntimeException();
        }
    }


    @Override
    public void close() throws IOException {
        server.stop(0);
        Logger.log("Server shutdown", this);
    }
}
