package it.polimi.ingsw.events.data;

import it.polimi.ingsw.model.GameResult;

import java.util.Optional;

public class EndGameResultsEvent extends BaseEvent {
    private final GameResult firstTeamResult;
    private final GameResult secondTeamResult;

    public EndGameResultsEvent(GameResult firstTeamResult, GameResult secondTeamResult) {
        this.firstTeamResult = firstTeamResult;
        this.secondTeamResult = secondTeamResult;

    }
    public GameResult getFirstTeamResult() {
        return firstTeamResult;
    }

    public GameResult getSecondTeamResult() {
        return secondTeamResult;
    }
}
