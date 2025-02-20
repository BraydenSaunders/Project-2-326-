package Solutions;

import Core_Algorithms.GeneticAlgorithm;
import Core_Algorithms.Individual;
import Problems.NQueens;

import java.util.Arrays;
import java.util.Random;

public abstract class GA_NQueens extends GeneticAlgorithm<int[]> {
    private final NQueens problem;
    public GA_NQueens(int maximumGeneration, double maxRate, double elitism, NQueens problem){
        super(maximumGeneration, maxRate, elitism);
        this.problem = problem;
    }

    public Individual<int[]> reproduce(Individual<int[]> p, Individual<int[]> q){
        Random random = new Random();
        int startPosition = random.nextInt(p.getChromosome().length);
        int endPosition = random.nextInt(p.getChromosome().length);
        if (startPosition > endPosition){
            int temp = startPosition;
            startPosition = endPosition;
            endPosition = temp;
        }

        int[] childChromosome = Arrays.copyOf(p.getChromosome(), p.getChromosome().length);
        for (int i = 0; i < startPosition; i++){

        }
    }
}
