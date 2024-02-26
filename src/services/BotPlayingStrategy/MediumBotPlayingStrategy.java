package services.BotPlayingStrategy;

import exceptions.NoMoreValidMovesLeft;
import models.*;

import java.util.ArrayList;
import java.util.List;

public class MediumBotPlayingStrategy implements BotPlayingStrategy{
    @Override
    public Move makeMove(Board board, Player player) {

        List<Cell> emptyCellList = new ArrayList<>();

        for (List<Cell> row :
                board.getMatrix()) {
            for (Cell cell :
                    row) {
                if (cell.getCellstate().equals(CELLSTATE.EMPTY)) {
                    emptyCellList.add(cell);
                }
                }
        }
        if(!emptyCellList.isEmpty()){
            int min = 0;
            int max = emptyCellList.size()-1;
            int range = max - min +1;

            int rand = (int)(Math.random() * range) + min;

            Cell cell = emptyCellList.get(rand);

            cell.setPlayer(player);
            cell.setCellstate(CELLSTATE.FILLED);

            return new Move(player,cell);
        }
        throw new NoMoreValidMovesLeft("There are no more moves left");


    }
}
