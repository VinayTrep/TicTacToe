package services.BotPlayingStrategy;

import models.BOTTYPE;

public class BotPlayingStrategyFactory {

    public static BotPlayingStrategy getInstance(BOTTYPE bottype){
        return switch (bottype){
            case EASY -> new EasyBotPlayingStrategy();
            case MEDIUM -> new MediumBotPlayingStrategy();
            case HARD -> new HardBotPlayingStrategy();
        };
    }
}
