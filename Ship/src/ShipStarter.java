import DataClasses.Harbour;
import DataClasses.Ship;
import API.ShipController;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ShipStarter {

    public static void main(String[] args) throws UnknownHostException, IOException {

        Ship ship = new Ship();
        Scanner sc = new Scanner(System.in);
//        System.out.println("SeaTrade-Port eingeben: ");
//        int sPort = Integer.parseInt(sc.nextLine());
//        System.out.println("SeaTrade-Host eingeben: ");
//        String sHost = sc.nextLine();
//        System.out.println("Company-Port eingeben: ");
//        int cPort = Integer.parseInt(sc.nextLine());
//        System.out.println("Company-Host eingeben: ");
//        String cHost = sc.nextLine();
//        System.out.println("Harbour: ");
//        String harbourName = sc.nextLine();
//        Harbour harbour = new Harbour(-1, harbourName, null);
//        System.out.println("Ship Name: ");
//        String name = sc.nextLine();
        int cPort = 8080;
        int sPort = 8000;
        String cHost = "localhost";
        String sHost = "localhost";

        var harbour = new Harbour();
        harbour.setName("halifax");

        String name = "titanic";
        System.out.println("Company Name: ");
        String company = sc.nextLine();


        ship.createApi(cPort, cHost, sPort, sHost);
        ShipController controller = ship.getController();

        ship.setName(name);
        ship.setHarbour(harbour);

        controller.launch(harbour, name, company);


        System.out.println("Anfrage wurde an Server gesendet.");

        System.out.println("Commands: getHarbours | moveTo | load | unload | exit");

        while (true) {

            String input = sc.nextLine();

            switch (input) {

                case "getHarbours":
                    controller.getHarbours();
                    System.out.println("Anfrage wurde an Server gesendet.");
                    break;
                case "moveTo":
                    System.out.println("Harbour: ");
                    String harbourMove = sc.nextLine();
                    controller.moveTo(harbourMove);
                    System.out.println("Anfrage wurde an Server gesendet.");
                    break;
                case "load":
                    controller.load();
                    System.out.println("Anfrage wurde an Server gesendet.");
                    break;
                case "unload":
                    controller.unload();
                    System.out.println("Anfrage wurde an Server gesendet.");
                    break;
                case "exit":
                    controller.exit();
                    System.out.println("Anfrage wurde an Server gesendet.");
                    break;
                default:
                    System.out.println("Command fehlerhaft.");
                    break;

            }

        }

    }

}
