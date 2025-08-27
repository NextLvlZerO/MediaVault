package eva.mediavault.core;

import java.util.*;

import eva.mediavault.commands.MediaCommands;
import eva.mediavault.commands.SubscriptionCommands;
import eva.mediavault.commands.UserCommands;


public class PerformanceTest {

    String baseUrl = "http://localhost:8080/api/v1";
    int userAmount;


    public PerformanceTest(int userAmount) {
        this.userAmount = userAmount;
    }


    public void startTest() {
        List<UserClient> users = new ArrayList<>();

        // add users
        for (int i = 0; i < userAmount; i++) {
            users.add(new UserClient(i));
        }

        TimeMeasure.measureTimeVoid("Register user", () -> UserCommands.registerUsers(users, baseUrl));

        SubscriptionCommands.getUserSubscription(users, baseUrl);

        MediaCommands.getAllMedia(users.get(0), baseUrl, );
    }
}

