package Core_Algorithms;


import java.util.List;
/**
 *
 * G: the data type of a chromosome (typically this will be a list or an array)
 */
public class Individual<G> implements Comparable<Individual<G>>{
    private final G chromosome;
    private final double fitnessScore;
    public Individual (G chromosome, double fitnessScore){
        this.chromosome = chromosome;
        this.fitnessScore = fitnessScore;
    }
    public G getChromosome() {
        return chromosome;
    }
    public double getFitnessScore() {
        return fitnessScore;
    }
    @Override
    public String toString() {
        return "Individual{" + "chromosome=" + chromosome + ", fitnessScore=" + fitnessScore + '}';
    }
    public int compareTo(Individual<G> i) {
        return Double.compare(
                i.getFitnessScore(),
                this.getFitnessScore()
        );
    }
}
