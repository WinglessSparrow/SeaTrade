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
        System.out.println("SeaTrade-Port eingeben: ");
        int sPort = Integer.parseInt(sc.nextLine());
        System.out.println("SeaTrade-Host eingeben: ");
        String sHost = sc.nextLine();
        System.out.println("Company-Port eingeben: ");
        int cPort = Integer.parseInt(sc.nextLine());
        System.out.println("Company-Host eingeben: ");
        String cHost = sc.nextLine();
        System.out.println("Harbour: ");
        String harbourName = sc.nextLine();
        Harbour harbour = new Harbour();
        harbour.setName(harbourName);
        System.out.println("Ship Name: ");
        String name = sc.nextLine();
//        int cPort = 8080;
//        int sPort = 8000;
//        String cHost = "localhost";
//        String sHost = "localhost";
//
//        var harbour = new Harbour();
//        harbour.setName("halifax");
//
//        String name = "titanic";
        System.out.println("Company Name: ");
        String company = sc.nextLine();


        ship.createApi(cPort, cHost, sPort, sHost);
        ShipController controller = ship.getController();

        ship.setName(name);
        ship.setHarbour(harbour);

        controller.launch(harbour, name, company);
        controller.updateHarbours();

        boolean running = true;

        while (running) {
            System.out.println("Commands: harbours | moveTo | load | unload | exit");
            String input = sc.nextLine();

            switch (input) {

                case "harbours":
                    controller.updateHarbours();
                    for (var h : controller.getHarbours()) {
                        System.out.println(h);
                    }
                    break;
                case "moveTo":
                    for (var h : controller.getHarbours()) {
                        if (controller.getShip().getHeldCargo() != null && h.equals(controller.getShip().getHeldCargo().getDest().getName())) {
                            System.out.println("\033[0;33m" + h + " < cargo destination \033[0m");
                        } else {
                            System.out.println(h);
                        }
                    }
                    System.out.println("Choose Destination Harbour: ");
                    String harbourMove = sc.nextLine();

                    controller.getShip().setHarbour(null);

                    controller.moveTo(harbourMove);

                    break;
                case "load":
                    controller.load();
                    break;
                case "unload":
                    controller.unload();
                    break;
                case "exit":
                    controller.exit();
                    running = false;
                    break;
                default:
                    System.out.println("Command fehlerhaft.");
                    break;

            }

            while (controller.getShip().getHarbour() == null) {
                try {
                    System.out.print(".");

                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        controller.close();

        System.exit(0);
    }

}
