package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.model.GameResult;

import java.util.Optional;

public class EndGameResultsEvent extends BaseEvent {
    private final EndGameResult result;
    private final GameResult firstTeamResult;
    private final GameResult secondTeamResult;
    private final int firstTeamTotalPoints;
    private final int secondTeamTotalPoints;

    public EndGameResultsEvent(GameResult firstTeamResult, GameResult secondTeamResult, EndGameResult result, int firstTeamTotalPoints, int secondTeamTotalPoints) {
        this.result = result;
        ID = "END_GAME_RESULTS_EVENT";
        this.firstTeamResult = firstTeamResult;
        this.secondTeamResult = secondTeamResult;
        this.firstTeamTotalPoints = firstTeamTotalPoints;
        this.secondTeamTotalPoints = secondTeamTotalPoints;
    }

    public EndGameResult getResult() {
        return result;
    }

    public GameResult getFirstTeamResult() {
        return firstTeamResult;
    }

    public GameResult getSecondTeamResult() {
        return secondTeamResult;
    }

    public int getFirstTeamTotalPoints() {
        return firstTeamTotalPoints;
    }

    public int getSecondTeamTotalPoints() {
        return secondTeamTotalPoints;
    }
}
