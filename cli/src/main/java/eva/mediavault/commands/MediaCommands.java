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
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MediaCommands {

    private static final ObjectMapper mapper = new ObjectMapper();



    public static String getAllMedia(UserClient user, String baseUrl, int amount) {
        System.out.println("[GET ALL MEDIA]");

        try {
            HttpRequest getMediaRequest = HttpRequestUtil.createRequest(HttpMethod.GET, baseUrl + "/media/movie/all?page=0&page-size=" + amount, null);
            HttpResponse<String> response = user.getClient().send(getMediaRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() > 299 ) {
                System.out.println(response.body());
                return null;
            }
            return response.body();
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void lendMedia(List<UserClient> users,List<Integer> mediaIds, String baseUrl, int amount, boolean parallel) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        System.out.println("[LEND MEDIA]");

        if (parallel) {
            for (int i = 0; i < users.size(); i++) {
                int finalI = i;
                executor.submit(() -> {
                        lendMediaBody(users, mediaIds, baseUrl, amount, finalI);
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
                lendMediaBody(users, mediaIds, baseUrl, amount, i);
            }
        }
    }



    private static void lendMediaBody(List<UserClient> users, List<Integer> mediaIds, String baseUrl, int amount, int i) {
        try {
            UserClient currentUser = users.get(i);
            String currentId = currentUser.getCookie("userId");

            if (currentId == null) {
                System.out.println("User " + i + " has no user id cookie");
                return;
            }

            Map<String, Integer> lendBodyMap = new HashMap<>();
            lendBodyMap.put("days", 10);
            String lendBody = mapper.writeValueAsString(lendBodyMap);

            for (int j = 0; j < amount; j++) {
                int randomId = mediaIds.get(ThreadLocalRandom.current().nextInt(mediaIds.size()));
                HttpRequest lendRequest = HttpRequestUtil.createRequest(HttpMethod.POST, baseUrl + "/user/" + currentId + "/lend/media/" + randomId, lendBody);
                HttpResponse<String> response = currentUser.getClient().send(lendRequest, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() < 200 || response.statusCode() > 299 ) {
                    System.out.println(response.body());
                }
            }
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




    public static void writeReview(List<UserClient> users,List<Integer> mediaIds, String baseUrl, int amount, boolean parallel) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        System.out.println("[WRITE REVIEW]");

        if (parallel) {
            for (int i = 0; i < users.size(); i++) {
                int finalI = i;
                executor.submit(() -> {
                    writeReviewBody(users, mediaIds, baseUrl, amount, finalI);
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
                writeReviewBody(users, mediaIds, baseUrl, amount, i);
            }
        }
    }



    private static void writeReviewBody(List<UserClient> users, List<Integer> mediaIds, String baseUrl, int amount, int i) {
        try {
            UserClient currentUser = users.get(i);

            Map<String, Object> writeReviewMap = new HashMap<>();
            writeReviewMap.put("title", "Really enjoyed it!");
            writeReviewMap.put("rating", ((int) (Math.random() * 5) ) + 1);
            writeReviewMap.put("details", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod " +
                    "tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo");

            String reviewBody = mapper.writeValueAsString(writeReviewMap);

            for (int j = 0; j < amount; j++) {
                int randomId = mediaIds.get(ThreadLocalRandom.current().nextInt(mediaIds.size()));
                HttpRequest reviewRequest = HttpRequestUtil.createRequest(HttpMethod.POST, baseUrl + "/media/" + randomId + "/review/create", reviewBody);
                HttpResponse<String> response = currentUser.getClient().send(reviewRequest, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() < 200 || response.statusCode() > 299 ) {
                    System.out.println(response.body());
                }
            }
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

