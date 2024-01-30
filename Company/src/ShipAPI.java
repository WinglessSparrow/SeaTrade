import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ShipAPI extends Thread {

    private ShipController controller;

    private ServerSocket socket;


    ShipAPI(ShipController controller, int port) {
        this.controller = controller;

        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try (var connection = socket.accept()) {

                //TODO getting and parsing

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
