package models;

import java.util.ArrayList;
import java.util.List;

public class Board implements Clonable<Board>{
    private final int dimension;
    private final List<List<Cell>> matrix;

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

    private Board(Board originalBoard){
        this.dimension = originalBoard.getDimension();
        this.matrix = new ArrayList<>();

        for (int i = 0; i < originalBoard.getDimension(); i++) {
            this.matrix.add(new ArrayList<>());
            for (int j = 0; j < originalBoard.getDimension(); j++) {
                Cell cell = originalBoard.getMatrix().get(i).get(j);
                this.matrix.get(i).add(cell.clone());
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

    @Override
    public Board clone() {
        return new Board(this);
    }

}
