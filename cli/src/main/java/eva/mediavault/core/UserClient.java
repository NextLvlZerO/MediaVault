package eva.mediavault.core;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.http.HttpClient;
import java.util.List;

public class UserClient {
    private final int userId;
    private final HttpClient client;
    private final CookieManager cookieManager;
    private final CookieStore cookieStore;

    public UserClient(int id) {
        this.userId = id;
        this.cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
        this.cookieStore = this.cookieManager.getCookieStore();
        this.client = HttpClient.newBuilder().cookieHandler(cookieManager).build();
    }

    public HttpClient getClient() {
        return client;
    }

    public String getCookie(String name) {
        List<HttpCookie> cookies = this.cookieStore.getCookies();
        return cookies.stream().filter(item -> item.getName().equals(name)).findFirst().map(HttpCookie::getValue).orElse(null);
    }
}
