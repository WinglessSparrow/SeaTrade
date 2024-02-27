package Logger;

public class Logger {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private static Out out = new Out() {
        @Override
        public void log(String msg) {
            System.out.println(ANSI_YELLOW + " | LOG | " + msg + ANSI_RESET);
        }
    };

    private static Out greenOut = new Out() {
        @Override
        public void log(String msg) {
            System.out.println(ANSI_GREEN + " | LOG | " + msg + ANSI_RESET);
        }
    };

    private static Out errOut = new Out() {
        @Override
        public void log(String msg) {
            System.err.println(ANSI_RED + " | ERROR LOG | " + msg + ANSI_RESET);
        }
    };

    private static void setNewOuts(Out newOut, Out newGreenOut, Out newErrOut) {
        out = newOut;
        greenOut = newGreenOut;
        errOut = newErrOut;
    }

    public static void log(String message) {
        out.log(message);
    }

    public static void logGreen(String message) {
        greenOut.log(message);
    }

    public static void logErr(String message) {
        errOut.log(message);
    }
}
