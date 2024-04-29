package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.model.GameResult;

import java.util.Optional;

public class EndGameResultsEvent extends BaseEvent {
    private final GameResult firstTeamResult;
    private final GameResult secondTeamResult;

    public EndGameResultsEvent(GameResult firstTeamResult, GameResult secondTeamResult) {
        ID = "END_GAME_RESULTS_EVENT";
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
