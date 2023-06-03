import java.util.concurrent.*;

public class Main {

    private final ExecutorService executor
            = Executors.newFixedThreadPool(30);


    Future<Integer> thinkSomewhereElse(Integer theMeaningOfLife) {
        return executor.submit(() -> {
            Thread.sleep(1000L);
            return theMeaningOfLife + 6;
        });
    }

    CompletableFuture<Integer> thinkDifferent(Integer theMeaningOfLife) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return theMeaningOfLife + 10;
        });
    }

    static void tellTheMeaningOfLife(Integer theMeaningOfLife) {
        System.out.println(theMeaningOfLife);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Integer theMeaningOfLife = 34;

        Main meaningOfLife = new Main();

        Future<Integer> theMeaningOfLifeFromOtherPlace = meaningOfLife.thinkSomewhereElse(theMeaningOfLife);
        meaningOfLife.thinkDifferent(theMeaningOfLife).thenAccept(Main::tellTheMeaningOfLife);
        tellTheMeaningOfLife(theMeaningOfLifeFromOtherPlace.get());
        tellTheMeaningOfLife(theMeaningOfLife);
    }
}
