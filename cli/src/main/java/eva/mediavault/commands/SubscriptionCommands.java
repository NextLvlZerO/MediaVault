package eva.mediavault.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import eva.mediavault.core.HttpMethod;
import eva.mediavault.core.UserClient;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SubscriptionCommands {

    private static final ObjectMapper mapper = new ObjectMapper();




    public static void getUserSubscription(List<UserClient> users, String baseUrl, boolean parallel) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        System.out.println("[GET USER SUBSCRIPTION]");

        if (parallel) {
            for (int i = 0; i < users.size(); i++) {
                int finalI = i;
                executor.submit(() -> {
                    getUserSubscriptionBody(users, baseUrl, finalI);
                });
            }
            try {
                executor.shutdown();
                executor.awaitTermination(2, TimeUnit.MINUTES);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        else {
            for (int i = 0; i < users.size(); i++) {
                getUserSubscriptionBody(users, baseUrl, i);
            }
        }
    }



    private static void getUserSubscriptionBody(List<UserClient> users, String baseUrl, int i) {
        try {
            UserClient currentUser = users.get(i);
            String currentId = currentUser.getCookie("userId");

            if (currentId == null) {
                System.out.println("User " + i + " has no user id cookie");
                return;
            }

            HttpRequest subscriptionRequest = HttpRequestUtil.createRequest(HttpMethod.GET, baseUrl + "/subscription/user/" + currentId, null);
            HttpResponse<String> response = currentUser.getClient().send(subscriptionRequest, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    public static void updateUserSubscription(List<UserClient> users, String baseUrl, boolean parallel) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        System.out.println("[UPDATE USER SUBSCRIPTION]");

        if (parallel) {
            for (int i = 0; i < users.size(); i++) {
                int finalI = i;
                executor.submit(() -> {
                    updateUserSubscriptionBody(users, baseUrl, finalI);
                });
            }
            try {
                executor.shutdown();
                executor.awaitTermination(2, TimeUnit.MINUTES);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        else {
            for (int i = 0; i < users.size(); i++) {
                updateUserSubscriptionBody(users, baseUrl, i);
            }
        }

    }


    public static void updateUserSubscriptionBody(List<UserClient> users, String baseUrl, int i) {
        try {
            UserClient currentUser = users.get(i);
            String currentId = currentUser.getCookie("userId");

            if (currentId == null) {
                System.out.println("User " + i + " has no user id cookie");
                return;
            }
            HttpRequest subscriptionRequest = HttpRequestUtil.createRequest(HttpMethod.PUT, baseUrl + "/subscription/update/Premium/user/" + currentId, "");
            HttpResponse<String> response = currentUser.getClient().send(subscriptionRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() > 299 ) {
                System.out.println(response.body());
            }
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
