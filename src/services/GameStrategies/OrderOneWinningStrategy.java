package services.GameStrategies;

import models.Cell;
import models.Game;
import models.Move;
import models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderOneWinningStrategy implements WinningStrategy{

    private final int dimension;
    private final List<HashMap<Character,Integer>> rowList;
    private final List<HashMap<Character,Integer>> colList;
    private final HashMap<Character,Integer> rightDiagonal;
    private final HashMap<Character,Integer> leftDiagonal;
    private final HashMap<Character,Integer> corners;

    private final HashMap<String,HashMap<Character,Integer>> allHashMapCount;

    public OrderOneWinningStrategy(int dimension) {
        this.dimension = dimension;
        this.rowList = new ArrayList<>();
        this.colList = new ArrayList<>();
        this.rightDiagonal = new HashMap<>();
        this.leftDiagonal = new HashMap<>();
        corners = new HashMap<>();
        this.allHashMapCount = new HashMap<>();

        // create the rowList and colList with hashmap
        for(int i = 0;i<dimension;i++){
            rowList.add(new HashMap<>());
            colList.add(new HashMap<>());
            this.allHashMapCount.put(HashMapName.ROW.name()+i,rowList.get(i));
            this.allHashMapCount.put(HashMapName.COL.name()+i,colList.get(i));
        }
        allHashMapCount.put(HashMapName.RIGHTDIAGNOL.name(), rightDiagonal);
        allHashMapCount.put(HashMapName.LEFTDIAGNOL.name(), leftDiagonal);
        allHashMapCount.put(HashMapName.CORNER.name(),corners);

    }

    private boolean checkCorner(int row,int col){
        return  ((row == 0 && col==0)
                || (row == dimension-1 && col==0)
                || (row == 0 && col==dimension-1)
                || (row == dimension-1 && col==dimension-1)
        );
    }

    private boolean checkRightDiagonal(int row, int col){
        return ( row+col == dimension-1);
    }

    private boolean checkLeftDiagonal(int row, int col){
        return (row==col);
    }

    @Override
    public Player checkWinner(Game game, Move lastMove) {
        Player currentPlayer = lastMove.getPlayer();
        char symbol = lastMove.getPlayer().getSymbol();
        Cell cell = lastMove.getCell();
        int row = cell.getRow();
        int col = cell.getCol();

        boolean isWinner = (checkCorner(row,col) && checkCornerHashMap(symbol,this.corners,HashMapName.CORNER.name()))
                || (populateHashmap(symbol,rowList.get(row),HashMapName.ROW.name()+row))
                || (populateHashmap(symbol,colList.get(col),HashMapName.COL.name()+col))
                || (checkLeftDiagonal(row,col) && populateHashmap(symbol,this.leftDiagonal,HashMapName.LEFTDIAGNOL.name()))
                || (checkRightDiagonal(row,col) && populateHashmap(symbol,this.rightDiagonal,HashMapName.RIGHTDIAGNOL.name()));

        if(isWinner){
            return currentPlayer;
        }else{
            return null;
        }

    }

    @Override
    public boolean checkDraw() {
        return allHashMapCount.isEmpty();
    }

    @Override
    public void undoMove(Game game, Move lastMove) {
        char symbol = lastMove.getPlayer().getSymbol();
        Cell cell = lastMove.getCell();
        int row = cell.getRow();
        int col = cell.getCol();

        if(checkCorner(row,col)){
            dePopulateHashmap(symbol,this.corners,HashMapName.CORNER.name());
        }

        if(checkLeftDiagonal(row,col)){
            dePopulateHashmap(symbol,this.leftDiagonal, HashMapName.LEFTDIAGNOL.name());
        }

        if(checkRightDiagonal(row,col)){
            dePopulateHashmap(symbol,this.rightDiagonal,HashMapName.RIGHTDIAGNOL.name());
        }

        dePopulateHashmap(symbol,rowList.get(row),HashMapName.ROW.name()+row);
        dePopulateHashmap(symbol,colList.get(col),HashMapName.COL.name()+col);
    }

    private boolean populateHashmap(char symbol, HashMap<Character,Integer> HM,String key){

        if (HM.containsKey(symbol)){
           HM.put(symbol,HM.get(symbol)+1);
           checkAHMSizeForDrawAndDepopulate(key);
           return HM.get(symbol) == dimension;
        }else{
            HM.put(symbol,1);
            checkAHMSizeForDrawAndDepopulate(key);
        }
        return false;
    }

    private void dePopulateHashmap(char symbol, HashMap<Character,Integer> HM,String key){

        if (HM.containsKey(symbol)){
            HM.put(symbol,HM.get(symbol)-1);

            if (HM.get(symbol)==0){
                HM.remove(symbol);
            }
            allHashMapCount.put(key,HM);
        }
    }
    private void checkAHMSizeForDrawAndDepopulate(String key){
        if (allHashMapCount.containsKey(key)){

            HashMap<Character,Integer> hm = allHashMapCount.get(key);
            if (hm.size() > 1){
                allHashMapCount.remove(key);
            }
        }
    }
    private boolean checkCornerHashMap(char symbol, HashMap<Character,Integer> HM, String key){
        if (HM.containsKey(symbol)){
            HM.put(symbol,HM.get(symbol)+1);
            checkAHMSizeForDrawAndDepopulate(key);
            return HM.get(symbol) == 4;
        }else{
            HM.put(symbol,1);
            checkAHMSizeForDrawAndDepopulate(key);
        }
        return false;
    }
}
