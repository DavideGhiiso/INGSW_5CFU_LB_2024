package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;

import java.util.Arrays;

public class ScoreEvent extends BaseEvent {
    private final String[] fistTeamNames;
    private final String[] secondTeamNames;
    private final int firstTeamPoints;
    private final int secondTeamPoints;

    public ScoreEvent(String[] fistTeamNames, String[] secondTeamNames, int firstTeamPoints, int secondTeamPoints) {
        ID = "SCORE_EVENT";
        this.fistTeamNames = fistTeamNames;
        this.secondTeamNames = secondTeamNames;
        this.firstTeamPoints = firstTeamPoints;
        this.secondTeamPoints = secondTeamPoints;
    }

    public String[] getFistTeamNames() {
        return fistTeamNames;
    }

    public String[] getSecondTeamNames() {
        return secondTeamNames;
    }

    public int getFirstTeamPoints() {
        return firstTeamPoints;
    }

    public int getSecondTeamPoints() {
        return secondTeamPoints;
    }
}
