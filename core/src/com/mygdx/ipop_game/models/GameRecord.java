package com.mygdx.ipop_game.models;

import java.time.Duration;
import java.time.Instant;

public class GameRecord {

    public int correctTotems, totalTotems;
    public String aliasPlayer, playerOcupation;
    public Instant timeStart, timeEnd;

    public GameRecord(
            int correctTotems,
            int totalTotems,
            String playerOcupation,
            String aliasPlayer,
            Instant timeStart,
            Instant timeEnd
    ) {
        this.correctTotems = correctTotems;
        this.totalTotems = totalTotems;
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