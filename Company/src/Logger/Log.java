package Logger;

public class Log {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private static Out out = msg -> System.out.println(ANSI_YELLOW + " | LOG | " + msg + ANSI_RESET);

    private static Out greenOut = msg -> System.out.println(ANSI_GREEN + " | LOG | " + msg + ANSI_RESET);

    private static Out errOut = msg -> System.err.println(ANSI_RED + " | ERROR LOG | " + msg + ANSI_RESET);

    public static void setOut(Out out) {
        Log.out = out;
    }

    public static void setGreenOut(Out greenOut) {
        Log.greenOut = greenOut;
    }

    public static void setErrOut(Out errOut) {
        Log.errOut = errOut;
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
