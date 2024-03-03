package Central;

import java.util.Scanner;

public class UI {
    private final CentralController ctr;

    private boolean running = false;

    public UI(CentralController ctr) {
        this.ctr = ctr;
    }

    public void start() {
        var scanner = new Scanner(System.in);
        var commands = UICommands.values();

        while (running) {
            System.out.println("Possible Commands");
            for (int i = 0; i < commands.length; i++) {
                System.out.println((i + 1) + " - " + commands[i]);
            }

            System.out.print("cmd:");
            String value = scanner.nextLine();

            try {
                UICommands cmd = UICommands.valueOf(value);

                handle(cmd);
            } catch (IllegalArgumentException e) {
                System.err.println("Unknown command");
            }

        }
    }

    private void handle(UICommands command) {
        switch (command) {
            case MOVE -> {
            }
            case LOAD -> {
            }
            case UNLOAD -> {
            }
            case EXIT -> {
                running = false;
                System.out.println("Bye - Bye");
            }
            case UPDATE -> {
            }
        }
    }

}
