package Solutions;

import Core_Algorithms.GeneticAlgorithm;
import Core_Algorithms.Individual;
import Problems.TSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GA_TSP extends GeneticAlgorithm<int[]> {
    private final TSP problem;
    public GA_TSP(int maxGen, double mRate, double elitism, TSP problem){
        super(maxGen, mRate, elitism);
        this.problem = problem;
    }

    public Individual<int[]> reproduce(Individual<int[]> p, Individual<int[]> q) {
        Random random = new Random();
        int startPos;
        int endPos;
        do {
            startPos = random.nextInt(p.getChromosome().length );
            endPos = random.nextInt(p.getChromosome().length);
        } while (startPos == endPos);
        //If startPos is greater than endPos, swap them.
        if (startPos > endPos) {
            int t = startPos;
            startPos = endPos;
            endPos = t;
        }
        //First, copy the entire parent 1 chromosome to child.
        int[] childChromosome = Arrays.copyOf(p.getChromosome(), p.getChromosome().length);
        //Next, copy those genes before startPos and after endPos from parent 2 chromosome to child.
        for (int i = 0; i < startPos; i++) {
            childChromosome[i] = q.getChromosome()[i];
        }
        for (int i = endPos + 1; i<q.getChromosome().length; i++) {
            childChromosome[i] = q.getChromosome()[i];
        }
        return new Individual<>(childChromosome, calculateFitnessScore(childChromosome));
    }

    public Individual<int[]> mutate(Individual<int[]> individual){
//        Random random = new Random();
//        int[] chromosome = problem.generateNewState(individual.getChromosome());
//        int gene1;
//        int gene2;
//        do {
//            gene1 = chromosome[random.nextInt(chromosome.length)];
//            gene2 = chromosome[random.nextInt(chromosome.length)];
//        } while (gene1 == gene2);
//        int temp = chromosome[gene1];
//        chromosome[gene2] = chromosome[gene1];
//        chromosome[gene2] = temp;
//        System.out.println(Arrays.toString(chromosome));
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

    public double calculateFitnessScore(int[] chromosome) {
        return 1 / problem.cost(chromosome);
    }

    public static void main(String[] args) {
        int MAX_GEN = 300;
        double MUTATION_RATE = 0.1;
        int POPULATION_SIZE = 4000;
        double ELITISM = 0.2;
        int MAP_SIZE = 5;
        TSP problem = new TSP(MAP_SIZE);
        GA_TSP agent = new GA_TSP(MAX_GEN, MUTATION_RATE, ELITISM, problem);
        Individual<int[]> best = agent.evolve(agent.generateInitPopulation(POPULATION_SIZE));
        System.out.println("BEST SOLUTION: ");
        problem.printState(best.getChromosome());
        System.out.printf("\nTOTAL DISTANCE IS: %f", problem.cost(best.getChromosome()));
    }
}
