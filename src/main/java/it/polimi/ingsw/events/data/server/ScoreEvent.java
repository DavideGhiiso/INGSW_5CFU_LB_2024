package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.model.Player;

/**
 * Event used by the server to send the team points and the team names. Sent at the beginning of each round and every
 * time that a client replaces a bot or exits
 */
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

    public ScoreEvent(Player[] firstTeam, Player[] secondTeam, int firstTeamPoints, int secondTeamPoints) {
        this(
                new String[]{firstTeam[0].getName(), firstTeam[1].getName()},
                new String[]{secondTeam[0].getName(), secondTeam[1].getName()},
                firstTeamPoints,
                secondTeamPoints
        );
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
