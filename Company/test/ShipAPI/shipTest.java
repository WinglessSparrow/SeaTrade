package ShipAPI;

import Types.Direction;
import Types.Ship;
import Ship.DTO.ShipMessageDTO;
import Ship.DTO.ShipMessageType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class shipTest {
    public static void main(String[] args) {

        /*
         * First start the company
         */

        try (var socket = new Socket("localhost", 8080)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            var s = new Ship("Titanic", -1, new Point(2, 2), Direction.DOWN, null, null);
            var getS = new Ship("", 5, new Point(0, 0), Direction.DOWN, null, null);

            var add = new ShipMessageDTO(ShipMessageType.ADD, null, null, null, s);
            var get = new ShipMessageDTO(ShipMessageType.UPDATE, null, null, null, getS);

            var json = new ObjectMapper().writeValueAsString(add);
//            var json = new ObjectMapper().writeValueAsString(get);

            System.out.println(json);
            r.readLine();

            writer.println(json);

            System.out.println(reader.readLine());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
