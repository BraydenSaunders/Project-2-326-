package Solutions;

import Core_Algorithms.GeneticAlgorithm;
import Core_Algorithms.Individual;
import Problems.TSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GA_TSP extends GeneticAlgorithm<int[]> {
    private final TSP problem;
    public GA_TSP(int maxGen, double mRate, double elitism, TSP problem){
        super(maxGen, mRate, elitism);
        this.problem = problem;
    }

    public Individual<int[]> mutate(Individual<int[]> individual){
        int[] chromosome = problem.generateNewState(individual.getChromosome());
        return new Individual<>(chromosome, calculateFitnessScore(chromosome));
    }
    public List<Individual<int[]>> generateInitPopulation(int popSize){
        List<Individual<int[]>> population = new ArrayList<>(popSize);
        for(int i=0; i<popSize; i++){
            int[] chromosome = problem.getInitialState();
            population.add(new Individual<>(chromosome, calculateFitnessScore(chromosome)));
        }
        return population;
    }
    public double calculateFitnessScore(int[] chromosome){
        return 1 / problem.cost(chromosome);
    }

    public static void main(String[] args) {
        int MAX_GEN = 200;
        double MUTATION_RATE = 0.1;
        int POPULATION_SIZE = 5000;
        double ELITISM = 0.2;
        int MAP_SIZE = 32;
        TSP problem = new TSP(MAP_SIZE);
        GA_TSP agent = new GA_TSP(MAX_GEN, MUTATION_RATE, ELITISM, problem);
        Individual<int[]> best = agent.evolve(agent.generateInitPopulation(POPULATION_SIZE));
        System.out.printf("BEST SOLUTION: %s", Arrays.toString(best.getChromosome()));
        System.out.printf("TOTAL DISTANCE IS: %f", problem.cost(best.getChromosome()));
    }
}
