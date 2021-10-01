package com.virspit.virspitproduct.domain.teamplayer.exception;

import com.virspit.virspitproduct.error.exception.EntityNotFoundException;

public class TeamPlayerNotFoundException extends EntityNotFoundException {
    public TeamPlayerNotFoundException(final Long teamPlayerId) {
        super(String.format("ID가 %d인 종목", teamPlayerId));
    }
}
