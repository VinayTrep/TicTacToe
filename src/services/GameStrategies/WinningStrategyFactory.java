package services.GameStrategies;

public class WinningStrategyFactory {

    public static WinningStrategy getWinningStrategy(int dimension,STRATEGYTYPE strategytype){

       return switch (strategytype){
            case ORDERONE -> new OrderOneWinningStrategy(dimension);
            case ORDERN -> new OrderNWinningStrategy();
        };
    }
}
