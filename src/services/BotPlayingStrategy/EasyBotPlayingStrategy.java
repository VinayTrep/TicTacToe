package services.BotPlayingStrategy;

import exceptions.NoMoreValidMovesLeft;
import models.*;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy{
    @Override
    public Move makeMove(Board board, Player player) {
        List<List<Cell>> matrix = board.getMatrix();

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                if(matrix.get(i).get(j).getCellstate().equals(CELLSTATE.EMPTY)){
                    Cell cell = matrix.get(i).get(j);
                    cell.setCellstate(CELLSTATE.FILLED);
                    cell.setPlayer(player);
                    return new Move(player,cell);
                }
            }
        }
        throw new NoMoreValidMovesLeft("There are no more moves left");
    }
}
