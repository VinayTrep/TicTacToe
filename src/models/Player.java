package models;

import exceptions.CellNotEmptyException;

import java.util.Scanner;

public class Player {
    private String playerName;
    private int playerId;
    private PLAYERTYPE playertype;
    private char symbol;

    public Player() {
    }

    public Player(String playerName, int playerId,PLAYERTYPE playertype, char symbol) {
        this.playerName = playerName;
        this.playerId = playerId;
        this.playertype = playertype;
        this.symbol = symbol;
    }

    public Move makeMove(Board board){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the row number");
        int row = sc.nextInt();
        System.out.println("Enter the Column number");
        int col = sc.nextInt();

        inBoundCheck(board,row,col);
        Cell cell = board.getMatrix().get(row).get(col);
        validateCell(cell);

        cell.setPlayer(this);
        cell.setCellstate(CELLSTATE.FILLED);

        return new Move(this,cell);
    }

    private void validateCell(Cell cell){
        if (cell.getCellstate().equals(CELLSTATE.FILLED)){
            throw new CellNotEmptyException("The cell is not empty");
        }
    }

    private void inBoundCheck(Board board,int row,int col){
        if(row < 0 || col < 0 || row > board.getDimension()-1 || col > board.getDimension()-1){
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public PLAYERTYPE getPlayertype() {
        return playertype;
    }

    public void setPlayertype(PLAYERTYPE playertype) {
        this.playertype = playertype;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
