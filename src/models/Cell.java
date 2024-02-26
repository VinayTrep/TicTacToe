package models;

public class Cell implements Clonable<Cell>{
    private int row;
    private int col;
    private Player player;
    private CELLSTATE cellstate;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.player = null;
        this.cellstate = CELLSTATE.EMPTY;
    }

    @Override
    public Cell clone() {
        return new Cell(0,0);
    }

    public void printCell() {
        if (this.cellstate.equals(CELLSTATE.EMPTY)) {
            System.out.print("| |");
        } else {
            System.out.print("|" + player.getSymbol() + "|");
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public CELLSTATE getCellstate() {
        return cellstate;
    }

    public void setCellstate(CELLSTATE cellstate) {
        this.cellstate = cellstate;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
