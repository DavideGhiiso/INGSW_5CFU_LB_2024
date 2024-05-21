package it.polimi.ingsw.model.bot;

public enum Difficulties {
    EASY,
    MEDIUM,
    HARD;
    public static Difficulty getDifficulty(Difficulties difficulty) {
        return switch (difficulty) {
            case EASY -> new EasyDifficulty();
            case MEDIUM -> new MediumDifficulty();
            case HARD -> new HardDifficulty();
        };
    }
}


