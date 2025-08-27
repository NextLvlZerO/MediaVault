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
    private final int writeAmount;
    private final boolean parallel;
    private final boolean updateSubscription;
    private final boolean filterMedia;

    private final int mediaAmount = 300;



    public PerformanceTest(int userAmount, int lendAmount, int writeAmount, boolean parallel, boolean updateSubscription, boolean filterMedia) {
        this.userAmount = userAmount;
        this.lendAmount = lendAmount;
        this.writeAmount = writeAmount;
        this.parallel = parallel;
        this.updateSubscription = updateSubscription;
        this.filterMedia = filterMedia;
    }



    public void startTest() throws JsonProcessingException {

        List<UserClient> users = new ArrayList<>();

        // add users
        for (int i = 0; i < userAmount; i++) {
            users.add(new UserClient(i));
        }

        // get media for testing
        String mediaResult = TimeMeasure.measureTime("Get all media", mediaAmount, () -> MediaCommands.getAllMedia(users.getFirst(), baseUrl, mediaAmount));
        List<Integer> mediaIds = getMediaIds(mediaResult);


        try {TimeMeasure.measureTimeVoid("Register user", userAmount, () -> UserCommands.registerUsers(users, baseUrl, parallel));}
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //TimeMeasure.measureTimeVoid("Get user subscriptions", () -> SubscriptionCommands.getUserSubscription(users, baseUrl));

        if (filterMedia) {
            try {TimeMeasure.measureTimeVoid("Filter media", userAmount, () -> MediaCommands.filterMedia(users, baseUrl, parallel));}
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (updateSubscription) {
            try {TimeMeasure.measureTimeVoid("Update subscription", userAmount, () -> SubscriptionCommands.updateUserSubscription(users, baseUrl, parallel));}
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        if (lendAmount > 0) {
            try {TimeMeasure.measureTimeVoid("Lend media", lendAmount * userAmount, () -> MediaCommands.lendMedia(users, mediaIds, baseUrl, lendAmount, parallel));}
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        if (writeAmount > 0) {
            try{TimeMeasure.measureTimeVoid("Write review", writeAmount * userAmount, () -> MediaCommands.writeReview(users, mediaIds, baseUrl, lendAmount, parallel));}
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
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

