package eva.mediavault.commands;

import eva.mediavault.core.HttpMethod;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Arrays;

public class HttpRequestUtil {


    public static HttpRequest createRequest(HttpMethod method, String url, String body) {
        if (!Arrays.asList(HttpMethod.values()).contains(method)) {
            throw new IllegalArgumentException("Invalid HTTP method: " + method);
        }

        switch (method) {
            case GET:
                return HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

            case POST:
                return HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();

            case PUT:
                return HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(body))
                        .build();

            case DELETE:
                return HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .DELETE()
                        .build();

            default:
                // sollte nicht passieren
                throw new UnsupportedOperationException("Unsupported HTTP method: " + method);
        }
    }

}
