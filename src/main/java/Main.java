import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.concurrent.*;

public class Main {

    /*
    --- Variables ---
    T - total time (provided)
    D - total distance (provided)
    t_c - time charge
    t_t - time travel
    u - velocity

    --- Equations ---
    D = u * t_t
    u = t_c
    t_t = T-t_c

    D = t_c(T-t_c)
    D = Tt_c - t_c^2

    /// Look for inflection point
    D'(t_c) = T - 2t_c = 0
    inflection occurs at  t_c = 0.5T

    --- Method ---
    Start at values at inflection point eg. 16 -> 8,9 , 17 -> 8,9
    Iterate c_t forwards and backwards until D = t_c(T-t_c) less than target D value
    Thread for each direction
    Sum results
     */


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Game game = new Game(46807866L, 214117714021024L);

        long halfCt = game.time / 2;

        Callable<Long> backwardIterator = new RaceCombinationCalculator(game.distance, game.time, halfCt, -1);
        Callable<Long> forwardIterator = new RaceCombinationCalculator(game.distance, game.time, halfCt + 1, 1);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        List<Future<Long>> results = executor.invokeAll(List.of(backwardIterator, forwardIterator));

        long backwardCombinations = results.get(0).get();
        long forwardCombinations = results.get(1).get();

        System.out.println(backwardCombinations+forwardCombinations);

        executor.shutdown();

    }

    private static class Game {
        long time;
        long distance;

        Game(long time, long distance) {
            this.time = time;
            this.distance = distance;
        }
    }
}
