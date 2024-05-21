package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.model.bot.Difficulties;

public class ChangeBotDifficultyEvent extends BaseEvent {
    private final Difficulties difficulty;
    public ChangeBotDifficultyEvent(Difficulties difficulty) {
        ID = "CHANGE_BOT_DIFFICULTY_EVENT";
        this.difficulty = difficulty;
    }

    public Difficulties getDifficulty() {
        return difficulty;
    }
}
