package Web.Server;

import Logger.Log;
import Web.Controller.WebController;
import Web.Handlers.DataHandler;
import Web.Handlers.ScriptHandler;
import Web.Handlers.SiteHandler;
import Web.Handlers.StyleHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer implements Closeable {

    private final HttpServer server;

    public WebServer(WebController controller, InetSocketAddress httpAddr) {

        try {
            server = HttpServerProvider.provider().createHttpServer(httpAddr, 0);

            server.createContext("/data", new DataHandler(controller));
            server.createContext("/", new SiteHandler());
            server.createContext("/style", new StyleHandler());
            server.createContext("/script", new ScriptHandler());

            server.setExecutor(null);
        } catch (IOException e) {
            Log.logErr("Cannot create HttpServer " + e);
            throw new RuntimeException();
        }
    }

    public void launch() {
        server.start();
    }

    @Override
    public void close() throws IOException {
        server.stop(0);
        Log.log("Server shutdown");
    }
}
