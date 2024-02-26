package models;

import services.BotPlayingStrategy.BotPlayingStrategyFactory;

public class Bot extends Player{
    private final BOTTYPE bottype;

    public Bot( int playerId, BOTTYPE bottype) {
        super("Siri", playerId,PLAYERTYPE.BOT, '$');
        this.bottype = bottype;

    }

    @Override
    public Move makeMove(Board board){
        return BotPlayingStrategyFactory.getInstance(this.bottype).makeMove(board,this);
    }
}
