import controlers.GameController;
import models.*;
import services.GameStrategies.STRATEGYTYPE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        boolean isGameOn = true;

        while (isGameOn) {
            GameController gameController = new GameController();
            int id = 1;
            List<Player> players = new ArrayList<>();
            Scanner sc = new Scanner(System.in);


            System.out.println("Welcome to TicTacToe");

            System.out.println("Enter the Board dimension");
            int dimension = sc.nextInt();

            System.out.println("Do you want a bot in your game?  Y/N");
            String botStatus = sc.next();



            if (botStatus.equalsIgnoreCase("Y")) {
                Player bot = new Bot(id++, BOTTYPE.EASY);
                players.add(bot);
                System.out.println("Bot added to the game");
            }

            while (id < dimension){
                System.out.println("Enter the player"+ id+" name");
                String name = sc.next();
                System.out.println("Enter player"+ id+" Unique symbol");
                char symbol = sc.next().charAt(0);
                Player player = new Player(name,id++,PLAYERTYPE.HUMAN,symbol);
                players.add(player);
            }

            Collections.shuffle(players);

            Game game = gameController.createGame(dimension,players,STRATEGYTYPE.ORDERONE);
            int playerIndex = -1;
            int no_of_moves = 0;
            while(game.getGamestatus().equals(GAMESTATUS.IN_PROGRESS)){

                System.out.println("Current Board:");
                gameController.displayBoard(game.getCurrentBoard());

                playerIndex +=1;
                playerIndex %= players.size();

                Player currentPlayer = players.get(playerIndex);
                System.out.println(currentPlayer.getPlayerName() +"'s Move:");
                Move move = gameController.executeMove(game,currentPlayer);

                //set current player, and moves list and boards list for UNDO and Replay purpose
                game.setCurrentPlayer(currentPlayer);
                game.getPlayedMoves().add(move);
                game.getBoardList().add(game.cloneCurrentBoard(game.getCurrentBoard()));

                Player isWinner = gameController.checkWinner(game,move);

                no_of_moves ++;
                if(move.getPlayer().getPlayertype().equals(PLAYERTYPE.HUMAN) && no_of_moves > 1 ||
                        (move.getPlayer().getPlayertype().equals(PLAYERTYPE.HUMAN))){
                    System.out.println("Do you want to UNDO the move? Y/N");
                    String isUndo = sc.next();

                    if(isUndo.equalsIgnoreCase("Y")){
                        game = gameController.UNDO(game,move);
                        System.out.println(currentPlayer.getPlayerName() +"'s Move:");
                        Move newMove = gameController.executeMove(game,currentPlayer);

                        game.getPlayedMoves().add(newMove);
                        game.getBoardList().add(game.cloneCurrentBoard(game.getCurrentBoard()));
                        isWinner = gameController.checkWinner(game,newMove);
                    }
                }



                if(isWinner != null){
                    System.out.println("Winner of the game is: " + isWinner.getPlayerName());
                    break;
                } else if (no_of_moves == dimension * dimension) {
                    System.out.println("Game is a draw");
                    break;
                }

            }


            gameController.displayBoard(game.getCurrentBoard());

            System.out.println(" DO you want to See Replay of the Game ? Y/N");
            String isReplay = sc.next();

            if (isReplay.equalsIgnoreCase("Y")){
                gameController.replay(game);
            }

            System.out.println(" DO you want to continue playing ? Y/N");
            String isContinue = sc.next();

            if(! isContinue.equalsIgnoreCase("Y")){
                isGameOn = false;
            }
        }
    }
}
