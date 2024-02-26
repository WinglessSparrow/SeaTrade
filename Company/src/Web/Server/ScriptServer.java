package Web.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScriptServer implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var absPath = new File("Company/resources/script.js").getAbsolutePath();
        var path = Path.of(absPath);
        byte[] fileBytes = Files.readAllBytes(path);

        exchange.sendResponseHeaders(200, fileBytes.length);
        exchange.getResponseHeaders().set("Content-Type", "text/javascript");

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(fileBytes);
        }

        exchange.close();
    }
}
