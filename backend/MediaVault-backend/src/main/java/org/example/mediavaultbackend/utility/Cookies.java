package org.example.mediavaultbackend.utility;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.AccountRequestDto;
import org.example.mediavaultbackend.dtos.SubscriptionResponseDto;
import org.example.mediavaultbackend.services.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Cookies {

    private final SubscriptionService subscriptionService;

    public void setUserCookies(String accountId, String username, HttpServletResponse response) {

        Cookie nameCookie = new Cookie("username", username);
        nameCookie.setPath("/");
        nameCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(nameCookie);

        if (accountId != null) {
            Cookie idCookie = new Cookie("userId", accountId);
            idCookie.setPath("/");
            idCookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(idCookie);
        }
    }

    /*
    public void setSubscriptionCookies(HttpServletResponse response, Long accountId) {
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.getSubscription(accountId);
        Cookie subscriptionName = new Cookie("")
    }

     */
}
