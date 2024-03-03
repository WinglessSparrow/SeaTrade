import Central.CentralController;
import Central.UI;

public class ShipStarter {
    public static void main(String[] args) {
        var ctr = new CentralController();
        var ui = new UI(ctr);


        ui.start();
    }
}