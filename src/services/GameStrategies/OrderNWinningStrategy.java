package services.GameStrategies;

import models.Game;
import models.Move;
import models.Player;

public class OrderNWinningStrategy implements WinningStrategy{
    @Override
    public Player checkWinner(Game game, Move lastMove) {
        return null;
    }

    @Override
    public void undoMove(Game game, Move lastMove) {

    }
}
