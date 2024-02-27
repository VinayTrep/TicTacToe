package services.GameStrategies;

import models.Cell;
import models.Game;
import models.Move;
import models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class OrderOneWinningStrategy implements WinningStrategy{

    private int dimension;
    private List<HashMap<Character,Integer>> rowList;
    private List<HashMap<Character,Integer>> colList;
    private HashMap<Character,Integer> rightDiagnol;
    private HashMap<Character,Integer> leftDiagnol;
    private HashMap<Character,Integer> corners;

    private HashSet<HashMap<Character,Integer>> allHashMapCount;

    public OrderOneWinningStrategy(int dimension) {
        this.dimension = dimension;
        this.rowList = new ArrayList<>();
        this.colList = new ArrayList<>();
        this.rightDiagnol = new HashMap<>();
        this.leftDiagnol = new HashMap<>();
        corners = new HashMap<>();
        this.allHashMapCount = new HashSet<>();

        // create the rowList and colList with hashmap
        for(int i = 0;i<dimension;i++){
            rowList.add(new HashMap<>());
            colList.add(new HashMap<>());
        }

        this.allHashMapCount.addAll(rowList);
        this.allHashMapCount.addAll(colList);
        this.allHashMapCount.add(rightDiagnol);
        this.allHashMapCount.add(leftDiagnol);
        this.allHashMapCount.add(corners);

        System.out.println("this is the all hashmap count" + allHashMapCount.size());
    }

    private boolean checkCorner(int row,int col){
        return  ((row == 0 && col==0)
                || (row == dimension-1 && col==0)
                || (row == 0 && col==dimension-1)
                || (row == dimension-1 && col==dimension-1)
        );
    }

    private boolean checkRightDiaganol(int row,int col){
        return (row==col);
    }

    private boolean checkLeftDiaganol(int row,int col){
        return ( row+col == dimension-1);
    }

    @Override
    public Player checkWinner(Game game, Move lastMove) {
        Player currentPlayer = lastMove.getPlayer();
        char symbol = lastMove.getPlayer().getSymbol();
        Cell cell = lastMove.getCell();
        int row = cell.getRow();
        int col = cell.getCol();

        boolean isWinner = (checkCorner(row,col) && checkCornerHashMap(symbol,this.corners))
                            ||(populateHashmap(symbol,rowList.get(row)))
                || (populateHashmap(symbol,colList.get(col)))
                || (checkLeftDiaganol(row,col) && populateHashmap(symbol,this.leftDiagnol))
                || (checkRightDiaganol(row,col) && populateHashmap(symbol,this.rightDiagnol));

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
            dePopulateHashmap(symbol,this.corners);
        }

        if(checkLeftDiaganol(row,col)){
            dePopulateHashmap(symbol,this.leftDiagnol);
        }

        if(checkRightDiaganol(row,col)){
            dePopulateHashmap(symbol,this.rightDiagnol);
        }

        dePopulateHashmap(symbol,rowList.get(row));
        dePopulateHashmap(symbol,colList.get(col));
    }

    private boolean populateHashmap(char symbol, HashMap<Character,Integer> HM){

        if (HM.containsKey(symbol)){
           HM.put(symbol,HM.get(symbol)+1);
           return HM.get(symbol) == dimension;
        }else{
            HM.put(symbol,1);
        }
        if (allHashMapCount.contains(HM)){
            if (HM.size() >1){
                allHashMapCount.remove(HM);
            }
        }
        return false;
    }

    private void dePopulateHashmap(char symbol, HashMap<Character,Integer> HM){

        if (HM.containsKey(symbol)){
            HM.put(symbol,HM.get(symbol)-1);

            if (HM.get(symbol)==0){
                HM.remove(symbol);
            }
        }

        if (!allHashMapCount.contains(HM)){
            if (HM.size() < 2){
                allHashMapCount.add(HM);
            }
        }
    }

    public boolean checkCornerHashMap(char symbol, HashMap<Character,Integer> HM){
        if (HM.containsKey(symbol)){
            HM.put(symbol,HM.get(symbol)+1);
            return HM.get(symbol) == 4;
        }else{
            HM.put(symbol,1);
        }
        if (allHashMapCount.contains(HM)){
            if (HM.size() >1){
                allHashMapCount.remove(HM);
            }
        }
        return false;
    }
}
