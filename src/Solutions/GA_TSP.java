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
            startPos = random.nextInt(p.getChromosome().length);
            endPos = random.nextInt(p.getChromosome().length);
        } while (startPos == endPos);
        if (startPos > endPos) {
            int t = startPos;
            startPos = endPos;
            endPos = t;
        }
        int[] portionOfParent = new int[p.getChromosome().length];
        Arrays.fill(portionOfParent, -1);
        int index = startPos;
        for(int i = 0; i < (endPos - startPos) + 1; i++){
            portionOfParent[index] = p.getChromosome()[index];
            index++;
        }
        int[] childChromosome = Arrays.copyOf(portionOfParent, p.getChromosome().length);
        for (int i = 0; i < childChromosome.length; i++){
            if(childChromosome[i] != -1){
                childChromosome[i] = -2;
            }
        }
        int posIndex = 0;
        for(int i = 0; i < q.getChromosome().length; i++) {
            int elementFound = 0;
            int childIndex = startPos;
            for (int j = 0; j < (endPos - startPos) + 1; j++) {
                if (portionOfParent[childIndex] == q.getChromosome()[i]) {
                    elementFound = 1;
                    break;
                }
                childIndex++;
            }
            if(elementFound != 1){
                if(childChromosome[posIndex] == -2){
                    while (childChromosome[posIndex] == -2){
                        posIndex++;
                    }
                }
                childChromosome[posIndex] = q.getChromosome()[i];
                posIndex++;
            }
        }
        int otherChildIndex = startPos;
        for(int i = 0; i < q.getChromosome().length; i++){
            if (childChromosome[i] == -2){
                childChromosome[i] = portionOfParent[otherChildIndex];
                otherChildIndex++;
            }
        }
        return new Individual<>(childChromosome, calculateFitnessScore(childChromosome));
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

    public double calculateFitnessScore(int[] chromosome) {
        return (1 / problem.cost(chromosome));
    }

    public static void main(String[] args) {
        int MAX_GEN = 230;
        double MUTATION_RATE = 0.1;
        int POPULATION_SIZE = 600;
//        int POPULATION_SIZE = 176;
        double ELITISM = 0.2;
        int MAP_SIZE = 6;
        TSP problem = new TSP(MAP_SIZE);
        GA_TSP agent = new GA_TSP(MAX_GEN, MUTATION_RATE, ELITISM, problem);
        Individual<int[]> best = agent.evolve(agent.generateInitPopulation(POPULATION_SIZE));
        System.out.println("BEST SOLUTION: ");
        problem.printState(best.getChromosome());
        System.out.printf("\nTOTAL DISTANCE IS: %f", problem.cost(best.getChromosome()));
    }
}
