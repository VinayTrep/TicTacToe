package models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int dimension;
    private List<List<Cell>> matrix;

    public Board(int dimension) {
        this.dimension = dimension;
        this.matrix = new ArrayList<>();

        for (int i = 0; i < dimension; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < dimension; j++) {
                matrix.get(i).add(new Cell(i, j));
            }
        }
    }

    public Board(Board originalBoard){
        this.dimension = originalBoard.getDimension();
        this.matrix = new ArrayList<>();

        for (int i = 0; i < originalBoard.getDimension(); i++) {
            this.matrix.add(new ArrayList<>());
            for (int j = 0; j < originalBoard.getDimension(); j++) {
                Cell cell = originalBoard.getMatrix().get(i).get(j);
                Cell cloneCell = originalBoard.getMatrix().get(i).get(j).clone();
                cloneCell.setRow(cell.getRow());
                cloneCell.setCol(cell.getCol());
                cloneCell.setPlayer(cell.getPlayer());
                cloneCell.setCellstate(cell.getCellstate());

                this.matrix.get(i).add(cloneCell);
            }
        }
    }

    public void displayBoard() {

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                matrix.get(i).get(j).printCell();
            }
            System.out.println();
        }
    }

    public int getDimension() {
        return dimension;
    }

    public List<List<Cell>> getMatrix() {
        return matrix;
    }
}
