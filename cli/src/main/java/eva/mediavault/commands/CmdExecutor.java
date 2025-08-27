package eva.mediavault.commands;

import eva.mediavault.core.PerformanceTest;

import java.util.Arrays;

public class CmdExecutor {

    public CmdExecutor() {}


    public void execute(String command) {

        try {
            String[] commands = command.split(" ");

            boolean measureTime = Arrays.stream(commands).anyMatch(item -> item.equals("TIME"));
            boolean parallel = Arrays.stream(commands).anyMatch(item -> item.equals("PARALLEL"));

            // check for user amount
            int userIndex = Arrays.asList(commands).indexOf("USER");
            if (userIndex == -1 || userIndex == commands.length - 1) {
                System.out.println("Invalid user command");
                return;
            }
            int userAmount = Integer.parseInt(commands[userIndex + 1]);

            PerformanceTest performanceTest = new PerformanceTest(userAmount);
            performanceTest.startTest();
        }
        catch(Error e) {
            System.out.printf("Error executing command: %s\n", e.getMessage());
        }
    }
}
