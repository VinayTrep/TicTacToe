package controlers;

import models.*;
import services.GameStrategies.STRATEGYTYPE;
import services.GameStrategies.WinningStrategy;
import services.GameStrategies.WinningStrategyFactory;

import java.util.List;

public class GameController {

    public Game createGame(int dimension, List<Player> players, STRATEGYTYPE strategytype){
        return Game.builder().setDimension(dimension)
                .setPlayerList(players)
                .setWinningStrategy(WinningStrategyFactory.getWinningStrategy(dimension,strategytype))
                .build();
    }

    public Move executeMove(Game game, Player player){
        return player.makeMove(game.getCurrentBoard());
    }

    public boolean checkDraw(Game game){
        return game.getWinningStrategy().checkDraw();
    }


    public Player checkWinner(Game game, Move lastMove){
       return game.getWinningStrategy().checkWinner(game,lastMove);
    }

    public void displayBoard(Board board){
        board.displayBoard();
    }

    public Game undo(Game game, Move lastMove){

        // logic to remove the last Move and Board from the List<Board> and List<Move> in  game class

        List<Board> boardList = game.getBoardList();
        boardList.remove(boardList.size()-1);

        List<Move> moveList = game.getPlayedMoves();
        moveList.remove(moveList.get(moveList.size()-1));

        game.setBoardList(boardList);
        game.setPlayedMoves(moveList);

        Cell lastCellPlayed = lastMove.getCell();

        lastCellPlayed.setCellstate(CELLSTATE.EMPTY);
        lastCellPlayed.setPlayer(null);

        //Undo the moves in the hashmaps
        unExecuteMove(game,lastMove);

        return game;
    }
    public void unExecuteMove(Game game,Move move){
        game.getWinningStrategy().undoMove(game,move);
    }

    public void replay(Game game){
        List<Move> moveList = game.getPlayedMoves();
        List<Board> boardList = game.getBoardList();

        for (int i = 0; i < moveList.size(); i++) {

            System.out.println("Player = " + moveList.get(i).getPlayer().getPlayerName());
            System.out.println("Move = " + "Col: " + moveList.get(i).getCell().getRow()
                    + " Row: " + moveList.get(i).getCell().getCol());
            System.out.println("Board State : ");
            displayBoard(boardList.get(i));
            System.out.println();
        }
    }
}
