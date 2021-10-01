package com.virspit.virspitproduct.domain.sports.exception;

import com.virspit.virspitproduct.error.exception.EntityNotFoundException;

public class SportsNotFoundException extends EntityNotFoundException {
    public SportsNotFoundException(final Long sportsId) {
        super(String.format("ID가 %d인 종목", sportsId));
    }
}
