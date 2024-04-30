package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;

import java.util.ArrayList;
import java.util.List;

public class StartGameEvent extends BaseEvent {
    private String[] fistTeamNames;
    private String[] secondTeamNames;
    public StartGameEvent() {
        ID = "START_GAME_EVENT";
        fistTeamNames = new String[0];
        secondTeamNames = new String[0];
    }

    public StartGameEvent(String[] fistTeamNames, String[] secondTeamNames) {
        this();
        this.fistTeamNames = fistTeamNames;
        this.secondTeamNames = secondTeamNames;
    }

    public String[] getFistTeamNames() {
        return fistTeamNames;
    }

    public String[] getSecondTeamNames() {
        return secondTeamNames;
    }
}
