package Web.Server;

import Web.Controller.WebController;
import Web.DTO.DBDump;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class DataHandler implements HttpHandler {

    private final WebController controller;

    public DataHandler(WebController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        var json = parseToJson(controller.dumbDBData());
        var bytes = json.getBytes();

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }

        exchange.close();
    }

    private String parseToJson(DBDump dump) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.writeValueAsString(dump);
    }
}
