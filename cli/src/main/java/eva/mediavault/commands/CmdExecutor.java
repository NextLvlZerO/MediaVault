package eva.mediavault.commands;

import eva.mediavault.core.PerformanceTest;

import java.util.Arrays;

public class CmdExecutor {

    public CmdExecutor() {}


    public void execute(String command) {

        try {
            String[] commands = command.strip().split(" ");

            boolean updateSubscription = Arrays.stream(commands).anyMatch(item -> item.equals("UPDATE_SUBSCRIPTION"));
            boolean parallel = Arrays.stream(commands).anyMatch(item -> item.equals("PARALLEL"));

            int userAmount = getUserAmount(commands);
            int lendAmount = getLendAmount(commands);


            PerformanceTest performanceTest = new PerformanceTest(userAmount, lendAmount, parallel, updateSubscription);
            try {
                performanceTest.startTest();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch(Error e) {
            System.out.printf("Error executing command: %s\n", e.getMessage());
        }
    }

    public int getUserAmount(String[] commands) {
        // check for user amount
        int userIndex = Arrays.asList(commands).indexOf("USER");
        if (userIndex == -1 || userIndex == commands.length - 1) {
            System.out.println("Invalid user command");
            return -1;
        }
        try {
            return Integer.parseInt(commands[userIndex + 1]);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid user amount");
            return -1;
        }
    }


    public int getLendAmount(String[] commands) {
        // check for lend amount
        int lendIndex = Arrays.asList(commands).indexOf("LEND");

        if (lendIndex == -1) {return -1;}

        if (lendIndex == commands.length - 1) {
            System.out.println("Invalid lend command");
            return -1;
        }
        try {
            return Integer.parseInt(commands[lendIndex + 1]);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid user amount");
            return -1;
        }
    }
}
