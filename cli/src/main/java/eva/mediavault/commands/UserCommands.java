package eva.mediavault.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import eva.mediavault.core.HttpMethod;
import eva.mediavault.core.UserClient;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UserCommands {

    private static final ObjectMapper mapper = new ObjectMapper();



    public static void registerUsers(List<UserClient> users, String baseUrl, boolean parallel) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        System.out.println("[REGISTER USERS]");

        if(parallel) {
            for (int i = 0; i < users.size(); i++) {
                int finalI = i;
                executor.submit(() -> {
                    registerUsersBody(users, baseUrl, finalI);
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
                registerUsersBody(users, baseUrl, i);
            }
        }
    }


    private static void registerUsersBody(List<UserClient> users, String baseUrl, int finalI) {
        try {
            UserClient currentUser = users.get(finalI);

            Map<String, String> loginBodyMap = new HashMap<>();
            loginBodyMap.put("username", "user" + finalI);
            loginBodyMap.put("password", Integer.toString(finalI));

            String loginBody = mapper.writeValueAsString(loginBodyMap);
            HttpRequest registerRequest = HttpRequestUtil.createRequest(HttpMethod.POST, baseUrl + "/register", loginBody);

            HttpResponse<String> response = currentUser.getClient().send(registerRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() > 299 ) {
                System.out.println(response.body());
            }
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
