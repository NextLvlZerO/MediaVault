package eva.mediavault.core;

import java.util.function.Supplier;

public class TimeMeasure {

    private final static String camelLine = "_______________________________________";


    public static <T> T measureTime(String command, Integer amount, Supplier<T> supplier) {
        long startTime = System.nanoTime();
        T result = supplier.get();
        long endTime = System.nanoTime();

        handleResult(command, startTime, endTime, amount);
        return result;
    }


    public static void measureTimeVoid(String command, Integer amount, Runnable action) {
        long startTime = System.nanoTime();
        action.run();
        long endTime = System.nanoTime();

        handleResult(command, startTime, endTime, amount);
    }


    private static void handleResult (String command, Long startTime, Long endTime, Integer amount) {
        String elapsedTime = String.format("%.5f", ((endTime - startTime) / 1_000_000_000.0));

        System.out.println(camelLine);
        System.out.println(command + " took:  [ " + elapsedTime + " seconds ]");
        System.out.println(command + " took an average of:  [ " + (Float.parseFloat(elapsedTime)/amount) * 1000 + " milliseconds ]");
        System.out.println(camelLine + '\n');
    }
}
