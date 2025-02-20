package Problems;
import java.util.Arrays;
import java.util.Random;
public class NQueens implements Problem<int[]>{
    //# of queens
    private final int N;
    public NQueens(int n){
        N = n;
    }
    public int[] generateNewState(int[] current){
        Random rand = new Random();
        int column = rand.nextInt(N);
        int currentRow = current[column];
        int newRow;
        do{
            newRow = rand.nextInt(N);
        }while(newRow==currentRow);
        int[] newState = Arrays.copyOf(current, current.length);
        newState[column] = newRow;
        return newState;
    }
    public double cost(int[] state){
        int conflicts = 0;
        for(int i=0; i<N-1; i++){
            for(int j=i+1; j<N; j++){
                //checking all the row conflicts
                if(state[i] == state[j]){
                    conflicts++;
                }
                //check all the diagonal conflicts
                if(Math.abs(state[i]-state[j]) == Math.abs(i-j)){
                    conflicts++;
                }
            }
        }
        return conflicts;
    }

    public double goalCost(){
        return 0;
    }
    public int[] getInitialState(){
        Random rand = new Random();
        int[] state = new int[N];
        for(int i=0; i<N; i++){
            state[i] = rand.nextInt(N);
        }
        return state;
    }
    public void printState(int[] state){
        for(int row=0; row<N; row++){
            for(int col=0; col<N; col++){
                if(state[col] == row){
                    System.out.print(" Q ");
                }else{
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }
    public int getN(){
        return N;
    }
}
