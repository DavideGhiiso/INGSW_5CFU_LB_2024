package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;

/**
 * Event used by the server to notify the clients that are waiting that the game is starting.
 * It contains the username of the players and the points totalized by the players
 */
public class StartGameEvent extends BaseEvent {
    private String[] fistTeamNames;
    private String[] secondTeamNames;
    private int team1Points;
    private int team2Points;
    public StartGameEvent() {
        ID = "START_GAME_EVENT";
        fistTeamNames = new String[0];
        secondTeamNames = new String[0];
    }

    public StartGameEvent(String[] fistTeamNames, String[] secondTeamNames) {
        this();
        this.fistTeamNames = fistTeamNames;
        this.secondTeamNames = secondTeamNames;
        team1Points = 0;
        team2Points = 0;
    }

    public StartGameEvent(String[] fistTeamNames, String[] secondTeamNames, int team1Points, int team2Points) {
        this(fistTeamNames, secondTeamNames);
        this.team1Points = team1Points;
        this.team2Points = team2Points;
    }

    public String[] getFistTeamNames() {
        return fistTeamNames;
    }

    public String[] getSecondTeamNames() {
        return secondTeamNames;
    }

    public int getTeam1Points() {
        return team1Points;
    }

    public int getTeam2Points() {
        return team2Points;
    }
}
