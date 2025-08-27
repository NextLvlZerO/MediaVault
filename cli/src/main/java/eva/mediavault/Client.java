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
        commands.add("[PERFORMANCE TEST CLI]");
        commands.add("______________________\n");
        commands.add("Commands:");
        commands.add("| USER <amount> LEND <amount> REVIEW <amount> FILTER_MEDIA UPDATE_SUBSCRIPTION PARALLEL");
        commands.add("__________________________________________________________");
        commands.add("| USER <amount> to select user amount [required] ");
        commands.add("| LEND <amount> to measure Time [optional] ");
        commands.add("| REVIEW <amount> to write amount x reviews [optional] ");
        commands.add("| UPDATE_SUBSCRIPTION to update subscription [optional] ");
        commands.add("| FILTER_MEDIA to filter media [optional] ");
        commands.add("| PARALLEL to use Threads for performance test subscription [optional] ");
        commands.add("| [EXAMPLE] USER 500 LEND 10 PARALLEL");
    }
}
