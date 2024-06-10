package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;

/**
 * Internal event thrown when the current player is a bot
 */
public class BotTurnEvent extends BaseEvent {
    public BotTurnEvent() {
        ID = "BOT_TURN_EVENT";
    }
}
