package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;

/**
 *
 */
public class ReplaceWithBotEvent extends BaseEvent {
    public ReplaceWithBotEvent() {
        ID = "REPLACE_WITH_BOT_EVENT";
        requiresConnection = true;
    }
}
