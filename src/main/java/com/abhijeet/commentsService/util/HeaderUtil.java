package com.abhijeet.commentsService.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.abhijeet.commentsService.constant.AppConstants.USER_ID;

@Component
@RequiredArgsConstructor
public class HeaderUtil {
    private final HttpServletRequest request;

    public Long getUserId() {
        return Long.valueOf(request.getHeader(USER_ID));
    }
}
