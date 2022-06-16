import java.util.Random;

public class ProcessGenerator {

    private Random rand;
    private double probability;

    public ProcessGenerator(double probability) {
        this.rand = new Random();
        this.probability = probability;
    }

    public Process getNewProcess(int currentTime, int maxProcessTime, int maxLevel) {
        int processTime = rand.nextInt(maxProcessTime) + 1;
        int priority = rand.nextInt(maxLevel) + 1;
        return new Process(currentTime, processTime, priority);
    }

    public boolean query() {
        return (rand.nextDouble() < probability);
    }

}

