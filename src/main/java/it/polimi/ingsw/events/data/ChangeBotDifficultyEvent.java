package it.polimi.ingsw.events.data;

import it.polimi.ingsw.model.bot.Difficulties;

/**
 * Event that notifies a host of a bot difficulty change
 */
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
