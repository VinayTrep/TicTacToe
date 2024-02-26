package controlers;

import models.Board;
import models.Game;
import models.Move;
import models.Player;
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




    public Player checkWinner(Game game, Move lastMove){
       return game.getWinningStrategy().checkWinner(game,lastMove);
    }

    public void displayBoard(Board board){
        board.displayBoard();
    }

    public Game UNDO(Game game, Move lastMove){

        // creating a new clone of the game class using prototype DP
        Game newGame = game.clone();

        // logic to remove the last Move and Board from the List<Board> and List<Move> in  game class
        int getMovesSize = game.getPlayedMoves().size();

        game.getBoardList().remove(getMovesSize-1);
        List<Board> boardList = game.getBoardList();

        game.getPlayedMoves().remove(getMovesSize-1);
        List<Move> moveList = game.getPlayedMoves();

        newGame.setCurrentBoard(game.getBoardList().get(getMovesSize-2));
        newGame.setBoardList(boardList);
        newGame.setPlayedMoves(moveList);

        //Undo the moves in the hashmaps
        unExecuteMove(game,lastMove);



        return newGame;
    }
    public void unExecuteMove(Game game,Move move){
        game.getWinningStrategy().undoMove(game,move);
    }

    public void replay(Game game){
        List<Move> moveList = game.getPlayedMoves();
        List<Board> boardList = game.getBoardList();

        for (int i = 0; i < moveList.size(); i++) {

            System.out.println(" Player = " + moveList.get(i).getPlayer().getPlayerName());
            System.out.println(" Move = " + "Col: " + moveList.get(i).getCell().getRow()
                    + " Row: " + moveList.get(i).getCell().getCol());
            System.out.println("Board State : ");
            displayBoard(boardList.get(i));
        }
    }
}
