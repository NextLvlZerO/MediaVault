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

public class UserCommands {

    private static ObjectMapper mapper = new ObjectMapper();



    public static void registerUsers(List<UserClient> users, String baseUrl) {

        for (int i = 0; i < users.size(); i++) {
            try {
                UserClient currentUser = users.get(i);

                Map<String, String> loginBodyMap = new HashMap<>();
                loginBodyMap.put("username", "user" + i);
                loginBodyMap.put("password", Integer.toString(i));

                String loginBody = mapper.writeValueAsString(loginBodyMap);
                HttpRequest registerRequest = HttpRequestUtil.createRequest(HttpMethod.POST, baseUrl + "/register", loginBody);

                HttpResponse<String> response = currentUser.getClient().send(registerRequest, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.statusCode());
                System.out.println(response.body());
            }
            catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
