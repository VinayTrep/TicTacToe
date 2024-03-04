package models;

import exceptions.*;
import services.GameStrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Game{

    private Board currentBoard;
    private Player currentPlayer;

    private GAMESTATUS gamestatus;

    private WinningStrategy winningStrategy;

    private List<Player> playerList;
    private List<Move> playedMoves;
    private List<Board> boardList;

    private Game(Board currentBoard, WinningStrategy winningStrategy, List<Player> playerList) {
        this.currentBoard = currentBoard;
        this.currentPlayer = null;
        this.gamestatus = GAMESTATUS.IN_PROGRESS;
        this.winningStrategy = winningStrategy;
        this.playerList = playerList;
        this.playedMoves = new ArrayList<>();
        this.boardList = new ArrayList<>();
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public Board cloneCurrentBoard(){
        return currentBoard.clone();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GAMESTATUS getGamestatus() {
        return gamestatus;
    }

    public WinningStrategy getWinningStrategy() {
        return winningStrategy;
    }


    public List<Move> getPlayedMoves() {
        return playedMoves;
    }

    public List<Board> getBoardList() {
        return boardList;
    }


    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setGamestatus(GAMESTATUS gamestatus) {
        this.gamestatus = gamestatus;
    }


    public void setPlayedMoves(List<Move> playedMoves) {
        this.playedMoves = playedMoves;
    }

    public void setBoardList(List<Board> boardList) {
        this.boardList = boardList;
    }

    public static class GameBuilder {

        private int dimension;
        private WinningStrategy winningStrategy;
        private List<Player> playerList;

        public GameBuilder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public GameBuilder setWinningStrategy(WinningStrategy winningStrategy) {
            this.winningStrategy = winningStrategy;
            return this;
        }

        public GameBuilder setPlayerList(List<Player> playerList) {
            this.playerList = playerList;
            return this;
        }

        private void validateBotCount(){

            long botCount = playerList.stream().filter(player -> player.getPlayertype().equals(PLAYERTYPE.BOT)).count();

            if (botCount > 1){
                throw new InvalidBotCountException("the bot count is more than 1");
            }
        }
        private void validatePlayerSymbols(){

            Set<Character> symbolCount = playerList.stream().map(Player::getSymbol).collect(Collectors.toSet());

            if(symbolCount.size() < playerList.size()){
                throw new InvalidSymbolException("Symbols for each player has to be unique");
            }
        }

        private void validatePlayerCount(){

            if(playerList.size() < dimension-2 || playerList.size()>dimension || playerList == null){
                throw new InvalidPlayerCountException("No of players in your game is Invalid");
            }
        }

        private void validateDimension(){
            System.out.println("the dimension is " + dimension);
            if(dimension < 3 || dimension == 0 ){
                throw new InvalidDimensionException("your board dimension has to be atleast 3");
            }
        }

        private void validateWinningStrategy(){
            if(winningStrategy == null){
                throw new WinningStrategyNullException("You have to provide a winning strategy");
            }
        }

        private void validate(){
            validateDimension();
            validatePlayerCount();
            validatePlayerSymbols();
            validateBotCount();
            validateWinningStrategy();
            validateBotCount();
        }

        public Game build(){
            validate();
            return new Game(new Board(dimension),winningStrategy,playerList);
        }
    }
}
