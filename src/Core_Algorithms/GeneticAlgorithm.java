package Core_Algorithms;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 * This class assumes that higher fitness score means more optimal
 * @param <G> The data type of a "gene"
 */
public abstract class GeneticAlgorithm<G> {
    private final int MAX_GEN;
    private final double MUTATION;
    private final double ELITISM;
    public GeneticAlgorithm(int maxGen, double mutation, double elitism){
        this.MAX_GEN =maxGen;
        this.MUTATION = mutation;
        this.ELITISM = elitism;
    }
    /**
     * Select an individual who is NOT the same as the given individual "indiv"
     * The probability of an individual being selected
     * is proportional to their fitness score
     */
//    public Individual<G> select(List<Individual<G>> population, Individual<G> individual){
//        double sum = 0;
//        for (Individual<G> i : population) {
//            sum += i.getFitnessScore();
//        }
//        Individual<G> selected = null;
//        do {
//            double v = new Random().nextDouble(sum);
//            double cumulativeSum = 0;
//            for(Individual<G> i : population){
//                cumulativeSum += i.getFitnessScore();
//                if (v <= cumulativeSum){
//                    selected = i;
//                    break;
//                }
//            }
//        } while(selected == individual);
//        System.out.println(selected);
//        return selected;
//    }
    public Individual<G> select(List<Individual<G>> population, Individual<G> individual) {
        if (population == null || population.isEmpty()) {
            throw new IllegalArgumentException("Population cannot be null or empty!");
        }
        double sum = 0;
        for (Individual<G> i : population) {
            sum += i.getFitnessScore();
        }
        // Prevent sum = 0 issue
        if (sum == 0) {
            return population.get(new Random().nextInt(population.size()));
        }
        // Prevent infinite loop when only one individual exists
        if (population.size() == 1) {
            return population.get(0);
        }
        Individual<G> selected = null;
        do {
            double v = new Random().nextDouble(sum);
            double cumulativeSum = 0;

            for (Individual<G> i : population) {
                cumulativeSum += i.getFitnessScore();
                if (v <= cumulativeSum) {
                    selected = i;
                    break;
                }
            }
        } while (selected == individual);

        System.out.println("Selected individual: " + selected);
        return selected;
    }


    /**
     * Produce an offspring
     * The implementation must NOT modify either of the two parents,
     * as they may be used again in more crossovers.
     * @param p parent 1
     * @param q parent 2
     * @return the new offspring
     */
    public abstract Individual<G> reproduce(Individual<G> p, Individual<G> q);
    /**
     * Perform mutation on the given individual
     * @return the mutated new individual
     */
    public abstract Individual<G> mutate(Individual<G> indiv);
    public abstract double calculateFitnessScore (G chromosome);
    public Individual<G> evolve (List<Individual<G>> initPopulation){
        List<Individual<G>> population = initPopulation;
        Collections.sort(population);
        int bestGen = 0;
        Individual<G> best = population.get(0);
        for (int generation = 1; generation <= MAX_GEN; generation++) {
            List<Individual<G>> offspring = new ArrayList<>();
            for (int i=0; i<population.size(); i++) {
                Individual<G> p = select(population, null);
                Individual<G> q = select(population, p);
                Individual<G> child = reproduce(p, q);
                if (new Random().nextDouble() <= MUTATION) {
                    child = mutate(child);
                }
                offspring.add(child);
            }
            Collections.sort(offspring);
            List<Individual<G>> newPopulation = new ArrayList<>();
            int e = (int) (ELITISM * population.size());
            for (int i=0; i<e; i++) {
                newPopulation.add(population.get(i));
            }
            for (int i=0; i<population.size()-e; i++) {
                newPopulation.add(offspring.get(i));
            }
            population = newPopulation;
            Collections.sort(population);
            //keep track of the best solution so far
            if (population.get(0).getFitnessScore() > best.getFitnessScore()){
                best = population.get(0);
                bestGen = generation;
            }
        }
        System.out.println("best gen: "+bestGen);
        return population.get(0);
    }
}
