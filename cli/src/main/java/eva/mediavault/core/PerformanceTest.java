package eva.mediavault.core;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eva.mediavault.commands.MediaCommands;
import eva.mediavault.commands.SubscriptionCommands;
import eva.mediavault.commands.UserCommands;


public class PerformanceTest {

    private final String baseUrl = "http://localhost:8080/api/v1";
    private final ObjectMapper mapper = new ObjectMapper();

    private final int userAmount;
    private final int lendAmount;
    private final boolean parallel;
    private final boolean updateSubscription;



    public PerformanceTest(int userAmount, int lendAmount, boolean parallel, boolean updateSubscription) {
        this.userAmount = userAmount;
        this.lendAmount = lendAmount;
        this.parallel = parallel;
        this.updateSubscription = updateSubscription;
    }


    public void startTest() throws JsonProcessingException {

        List<UserClient> users = new ArrayList<>();

        // add users
        for (int i = 0; i < userAmount; i++) {
            users.add(new UserClient(i));
        }

        TimeMeasure.measureTimeVoid("Register user", () -> UserCommands.registerUsers(users, baseUrl, parallel));

        //TimeMeasure.measureTimeVoid("Get user subscriptions", () -> SubscriptionCommands.getUserSubscription(users, baseUrl));

        if (updateSubscription) {
            TimeMeasure.measureTimeVoid("Update subscription",() -> SubscriptionCommands.updateUserSubscription(users, baseUrl, parallel));
        }


        if (lendAmount > 0) {
            String mediaResult = TimeMeasure.measureTime("Get all media", () -> MediaCommands.getAllMedia(users.getFirst(), baseUrl, 100));

            List<Integer> mediaIds = getMediaIds(mediaResult);

            TimeMeasure.measureTimeVoid("Lend media", () ->MediaCommands.lendMedia(users, mediaIds, baseUrl, lendAmount, parallel));
        }
    }




    private List<Integer> getMediaIds(String mediaResult) throws JsonProcessingException {
        List<Map<String, Object>> mediaItems = mapper.readValue(
                mediaResult,
                mapper.getTypeFactory().constructCollectionType(List.class, Map.class)
        );

        return mediaItems.stream().map(item -> (Integer) item.get("id")).toList();
    }
}

