package services.GameStrategies;

import models.Game;
import models.Move;
import models.Player;

public interface WinningStrategy {

    public Player checkWinner(Game game, Move lastMove);
    public boolean checkDraw();
    public void undoMove(Game game,Move lastMove);
}
