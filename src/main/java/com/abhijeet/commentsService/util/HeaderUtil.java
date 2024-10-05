package com.abhijeet.commentsService.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.abhijeet.commentsService.constant.AppConstants.USER_ID;

@Component
public class HeaderUtil {

    @Autowired
    HttpServletRequest request;

    public Long getUserId() {
        return Long.valueOf(request.getHeader(USER_ID));
    }
}
