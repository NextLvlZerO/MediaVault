package org.example.mediavaultbackend.utility;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.mediavaultbackend.dtos.AccountRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Cookies {

    public void setUserCookies(ResponseEntity<String> responseData, AccountRequestDto accountRequestDto, HttpServletResponse response) {

        if (responseData.getStatusCode().is2xxSuccessful()) {
            Cookie nameCookie = new Cookie("username", accountRequestDto.getUsername());
            nameCookie.setPath("/");
            nameCookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(nameCookie);

            String accountId = responseData.getBody();
            if (accountId != null) {
                Cookie idCookie = new Cookie("userId", accountId);
                idCookie.setPath("/");
                idCookie.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(idCookie);
            }
        }
    }
}
