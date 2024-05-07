package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.List;

public class GameResult implements Serializable {
    private final int scopa;
    private final List<Points> pointsMade;
    private final String firstPlayer;
    private final String secondPlayer;

    public GameResult(List<Points> points, int nOfScopa, String firstWinner, String secondWinner) {
        this.scopa = nOfScopa;
        this.pointsMade = points;
        this.firstPlayer = firstWinner;
        this.secondPlayer = secondWinner;
    }

    public int getScopa() {
        return scopa;
    }

    public List<Points> getPointsMade() {
        return pointsMade;
    }

    public int getTotalPoints() {
        return scopa + pointsMade.size();
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public String getSecondPlayer() {
        return secondPlayer;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                "scopa=" + scopa +
                ", pointsMade=" + pointsMade +
                ", firstPlayer='" + firstPlayer + '\'' +
                ", secondPlayer='" + secondPlayer + '\'' +
                '}';
    }
}
