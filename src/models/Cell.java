package models;

public class Cell implements Clonable<Cell>{
    private final int row;
    private final int col;
    private Player player;
    private CELLSTATE cellstate;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.player = null;
        this.cellstate = CELLSTATE.EMPTY;
    }

    private Cell(Cell oldCell){
        this.row = oldCell.getRow();
        this.col = oldCell.getCol();
        this.player = oldCell.getPlayer();
        this.cellstate = oldCell.cellstate;
    }


    @Override
    public Cell clone() {
        return new Cell(this);
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

}
