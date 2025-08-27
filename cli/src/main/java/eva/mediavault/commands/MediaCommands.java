package eva.mediavault.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import eva.mediavault.core.HttpMethod;
import eva.mediavault.core.UserClient;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class MediaCommands {

    private static ObjectMapper mapper = new ObjectMapper();



    public static void getAllMedia(UserClient user, String baseUrl, int amount) {

        try {

            HttpRequest subscriptionRequest = HttpRequestUtil.createRequest(HttpMethod.GET, baseUrl + "/media/movie/all?page=0&page-size=" + amount, null);

            HttpResponse<String> response = user.getClient().send(subscriptionRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

