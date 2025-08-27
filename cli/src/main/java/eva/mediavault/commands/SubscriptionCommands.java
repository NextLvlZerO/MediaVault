package eva.mediavault.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import eva.mediavault.core.HttpMethod;
import eva.mediavault.core.UserClient;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class SubscriptionCommands {

    private static ObjectMapper mapper = new ObjectMapper();



    public static void getUserSubscription(List<UserClient> users, String baseUrl) {

        for (int i = 0; i < users.size(); i++) {
            try {
                UserClient currentUser = users.get(i);
                String currentId = currentUser.getCookie("userId");

                if (currentId == null) {
                    System.out.println("User " + i + " has no user id cookie");
                    continue;
                }

                HttpRequest subscriptionRequest = HttpRequestUtil.createRequest(HttpMethod.GET, baseUrl + "/subscription/user/" + currentId, null);

                HttpResponse<String> response = currentUser.getClient().send(subscriptionRequest, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.statusCode());
                System.out.println(response.body());
            }
            catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
