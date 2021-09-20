package com.virspit.virspitorder.util;

import com.virspit.virspitorder.error.ErrorCode;
import com.virspit.virspitorder.error.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void validateInputDate(final String startDate, final String endDate) {
        if(startDate == null || endDate == null) return;

        LocalDateTime startDateTime = LocalDateTime.parse(startDate, FORMATTER);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, FORMATTER);

        if (!startDateTime.isBefore(endDateTime)) {
            throw new BusinessException(
                    String.format("startDate(%s) 가 endDate(%s) 보다 큽니다.", startDate, endDate),
                    ErrorCode.INVALID_INPUT_VALUE);
        }

    }
}
