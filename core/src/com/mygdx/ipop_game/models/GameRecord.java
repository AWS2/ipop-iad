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

    public int getCorrectTotems() {
        return correctTotems;
    }

    public void setCorrectTotems(int correctTotems) {
        this.correctTotems = correctTotems;
    }

    public int getWrongTotems() {
        return wrongTotems;
    }

    public void setWrongTotems(int wrongTotems) {
        this.wrongTotems = wrongTotems;
    }

    public String getPlayerOcupation() {
        return playerOcupation;
    }

    public void setPlayerOcupation(String playerOcupation) {
        this.playerOcupation = playerOcupation;
    }

    public String getAliasPlayer() {
        return aliasPlayer;
    }

    public void setAliasPlayer(String aliasPlayer) {
        this.aliasPlayer = aliasPlayer;
    }

    public Instant getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Instant timeStart) {
        this.timeStart = timeStart;
    }

    public Instant getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Instant timeEnd) {
        this.timeEnd = timeEnd;
    }

}