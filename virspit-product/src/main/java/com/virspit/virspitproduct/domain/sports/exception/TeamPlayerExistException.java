package com.virspit.virspitproduct.domain.sports.exception;

import com.virspit.virspitproduct.error.ErrorCode;
import com.virspit.virspitproduct.error.exception.BusinessException;

public class TeamPlayerExistException extends BusinessException {
    public TeamPlayerExistException() {
        super(ErrorCode.TEAM_PLAYER_EXIST);
    }
}
