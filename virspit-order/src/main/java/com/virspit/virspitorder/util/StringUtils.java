package com.virspit.virspitorder.util;

import com.virspit.virspitorder.response.error.ErrorCode;
import com.virspit.virspitorder.response.error.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

    private static final String TIME = " 00:00:00";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void validateInputDate(final String startDate, final String endDate) {
        if (startDate == null || endDate == null) return;

        LocalDateTime startDateTime = LocalDateTime.parse(startDate + TIME, FORMATTER);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + TIME, FORMATTER);

        if (!startDateTime.isBefore(endDateTime)) {
            throw new BusinessException(
                    String.format("startDate(%s) 가 endDate(%s) 보다 큽니다.", startDate, endDate),
                    ErrorCode.INVALID_INPUT_VALUE);
        }

    }

    public static LocalDateTime parse(String date) {
        return LocalDateTime.parse(date + TIME, FORMATTER);
    }
}
