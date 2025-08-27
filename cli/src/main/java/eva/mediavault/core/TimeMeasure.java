package eva.mediavault.core;

import java.util.function.Supplier;

public class TimeMeasure {

    static String line = "---------------";

    public static <T> T measureTime(String command, Supplier<T> supplier) {
        long startTime = System.nanoTime();
        T result = supplier.get();
        long endTime = System.nanoTime();

        String elapsedTime = String.format("%.5f", ((endTime - startTime) / 1_000_000_000.0));

        handleResult(command, startTime, endTime);
        return result;
    }



    public static void measureTimeVoid(String command, Runnable action) {
        long startTime = System.nanoTime();
        action.run();
        long endTime = System.nanoTime();

        handleResult(command, startTime, endTime);
    }


    private static void handleResult (String command, Long startTime, Long endTime) {
        String elapsedTime = String.format("%.5f", ((endTime - startTime) / 1_000_000_000.0));

        System.out.println(line);
        System.out.println(command + " took: \n   " + elapsedTime + " seconds ");
        System.out.println(line);
    }
}
