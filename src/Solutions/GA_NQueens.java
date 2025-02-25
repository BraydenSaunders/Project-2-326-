package Solutions;

import Core_Algorithms.GeneticAlgorithm;
import Core_Algorithms.Individual;
import Problems.NQueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GA_NQueens extends GeneticAlgorithm<int[]> {
    private final NQueens problem;
    public GA_NQueens(int maxGen, double mRate, double elitism, NQueens problem){
        super(maxGen, mRate, elitism);
        this.problem = problem;
    }
    public Individual<int[]> reproduce(Individual<int[]> p, Individual<int[]> q) {
        // Select a random portion of parent1's chromosome by
        // randomly generating the start & the end positions.
        Random r = new Random();
        int startPos = r.nextInt(p.getChromosome().length);
        int endPos = r.nextInt(p.getChromosome().length);
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
    public Individual<int[]> mutate(Individual<int[]> indiv){
        int[] chromosome = problem.generateNewState(indiv.getChromosome());
        return new Individual<>(chromosome, calculateFitnessScore(chromosome));
    }
    //The fitness score for NQueens problem is the number of pairs of non-attacking queens,
    //which is the total number of pairs minus the number of attacking pairs.
    //The total number of pairs of queens is N choose two, which is N*(N-1)/2
    public double calculateFitnessScore(int[] chromosome){
        return problem.getN()*(problem.getN()-1)/(double)2 - problem.cost(chromosome);
    }
    public List<Individual<int[]>> generateInitPopulation(int popSize){
        List<Individual<int[]>> population = new ArrayList<>(popSize);
        for(int i=0; i<popSize; i++){
            int[] chromosome = problem.getInitialState();
            population.add(new Individual<>(chromosome, calculateFitnessScore(chromosome)));
        }
        return population;
    }
    public static void main(String[] args) {
        int MAX_GEN = 200;
        double MUTATION_RATE = 0.1;
        int POPULATION_SIZE = 5000;
        double ELITISM = 0.2;
        int SIZE = 32; //number of queens
        NQueens problem = new NQueens(SIZE);
        GA_NQueens agent = new GA_NQueens(MAX_GEN,MUTATION_RATE,ELITISM,problem);
        Individual<int[]> best = agent.evolve(agent.generateInitPopulation(POPULATION_SIZE));
        System.out.println("Best solution:");
        problem.printState(best.getChromosome());
        System.out.println("Best cost is: "+problem.cost(best.getChromosome()));
    }
}