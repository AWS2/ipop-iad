package com.mygdx.ipop_game.models;

import java.time.Duration;
import java.time.Instant;

public class GameRecord {

    public int correctTotems, wrongTotems;
    public String aliasPlayer, playerOcupation;
    public Instant timeStart, timeEnd;

    public GameRecord(
            int correctTotems,
            int wrongTotems,
            String playerOcupation,
            String aliasPlayer,
            Instant timeStart,
            Instant timeEnd
    ) {
        this.correctTotems = correctTotems;
        this.wrongTotems = wrongTotems;
        this.playerOcupation = playerOcupation;
        this.aliasPlayer = aliasPlayer;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public long getDuration() {
        Duration duration = Duration.between(timeStart, timeEnd);
        return duration.getSeconds();
    }

    public int getScore() {
        return 0;
    }

}