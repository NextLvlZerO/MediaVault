package eva.mediavault;
import eva.mediavault.commands.CmdExecutor;

import java.util.*;


public class Client {

    List<String> commands = new ArrayList<>();
    Scanner scanner;
    CmdExecutor cmdExecutor;

    public Client() {
        scanner = new Scanner(System.in);
        cmdExecutor = new CmdExecutor();
        fillCommands();
    }

    public void run() {
        while(true) {
            System.out.println(listCommands());

            String command = scanner.nextLine();
            cmdExecutor.execute(command);
        }
    }

    public String listCommands() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n".repeat(10));
        commands.forEach(item -> sb.append(item).append("\n"));
        sb.append("----------------------");
        return sb.toString();
    }


    public void fillCommands() {
        commands.add("Performance test cli");
        commands.add("----------------------");
        commands.add("");
        commands.add("USER <amount> |LEND <amount>| |UPDATE_SUBSCRIPTION| |PARALLEL|");
        commands.add("TIME to measure Time | optional ");
        commands.add("USER to select user amount | required ");
    }
}
