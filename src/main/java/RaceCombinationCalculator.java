import java.util.concurrent.Callable;

public class RaceCombinationCalculator implements Callable<Long> {

    private long D_Target;
    private long T;
    private long t_c;
    private int direction;

    private long combinations = 0;

    RaceCombinationCalculator(long D_Target, long T, long startTc, int direction) {
        this.D_Target = D_Target;
        this.T = T;
        this.t_c = startTc;
        this.direction = direction;
    }

    @Override
    public Long call() throws Exception {
        long D = t_c * (T - t_c);
        while(D > D_Target) {
            combinations += 1;
            t_c += direction;
            D = t_c * (T - t_c);

        }
        return combinations;
    }
}
