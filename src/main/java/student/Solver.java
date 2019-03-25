package student;



import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement the backtracking algorithm here.
 */
public class Solver {

    public final ArrayList<List<ArrayList<Integer>>> combinationsRows;
    public final ArrayList<List<ArrayList<Integer>>> combinationsColumn;
    public final Integer cntCols;
    public final Integer cntRows;
    public final ArrayList<ArrayList<Pair<String, Integer>>> rulesRow ;
    Solver(ArrayList<List<ArrayList<Integer>>> combinationsRows,ArrayList<List<ArrayList<Integer>>> combinationsColumn,Integer cntRows,Integer cntCols,ArrayList<ArrayList<Pair<String, Integer>>> rulesRow){
        this.combinationsRows=combinationsRows;
        this.combinationsColumn=combinationsColumn;
        this.cntRows=cntRows;
        this.cntCols=cntCols;
        this.rulesRow=rulesRow;
    }



    //solution is arrayList with indices of picked combination
    ArrayList<Integer> solution = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<String>>> finalSolutionMatrix = new ArrayList<>();



    public void solve(){
        ArrayList<ArrayList<Integer>> unconsistentCombinations = new ArrayList<>(cntCols);
        for(int i = 0;i<cntRows;i++){solution.add(null);}
        for(int i=0;i<cntCols;i++){
            ArrayList<Integer> temp = new ArrayList<>();
            unconsistentCombinations.add(temp);
        }

        backtrack(solution,0,unconsistentCombinations);

        //return result;
    }

    /**
     * Recursive procedure. Take unassigned variable, fill it with a value and call backtrack() again. Check for termination.
     */


    public void backtrack(ArrayList<Integer> lastSolution,int indexOfRow, ArrayList<ArrayList<Integer>> unconsistentCombinations) {
        ArrayList<ArrayList<Integer>> changes = new ArrayList<>();
        for(int i =0;i<combinationsRows.get(indexOfRow).size();i++) {//for all possible combinations in row
            if(!changeEmpty(changes)){
            changeBack(changes,unconsistentCombinations);
            changes.clear();}
            lastSolution.set(indexOfRow, i);
            changes = findUnconsistent(indexOfRow,lastSolution,unconsistentCombinations);
            if(isConsistent(unconsistentCombinations)) {  //if there are still combinations for columns
                if(indexOfRow<cntRows-1) {
                    ArrayList<Integer> newLastSolution = new ArrayList<>(lastSolution);
                    ArrayList<ArrayList<Integer>> newUnconsistentCombinations = new ArrayList<>(unconsistentCombinations);
                    backtrack(newLastSolution, indexOfRow + 1, newUnconsistentCombinations);
                }else if(indexOfRow==cntRows-1){
                    reconstructSolution(lastSolution);
                }
            }else{
                changeBack(changes,unconsistentCombinations);
                changes.clear();
            }
        }

    }

    public void reconstructSolution(ArrayList<Integer> solution){
        ArrayList<ArrayList<String>> finalMatrix = new ArrayList<>();
        for (int i=0;i<cntRows;i++){
            ArrayList<String> tempRow = new ArrayList<>();
            int tempSize = 0;
            int indexOfBlock = 0;
            int sizeOfBlock = rulesRow.get(i).get(indexOfBlock).getValue();
            for (int j = 0;j<cntCols;j++){
                tempRow.add("_");
                if(!combinationsRows.get(i).get(solution.get(i)).contains(j+1) && tempSize<sizeOfBlock){
                    tempSize = tempSize + 1;
                    sizeOfBlock = rulesRow.get(i).get(indexOfBlock).getValue();
                    tempRow.set(j,rulesRow.get(i).get(indexOfBlock).getKey());
                }else if(!combinationsRows.get(i).get(solution.get(i)).contains(j+1) && tempSize == sizeOfBlock){
                    if(indexOfBlock+1<rulesRow.get(i).size()) {
                        indexOfBlock = indexOfBlock + 1;
                        sizeOfBlock = rulesRow.get(i).get(indexOfBlock).getValue();
                    }
                    tempRow.set(j,rulesRow.get(i).get(indexOfBlock).getKey());
                    tempSize = 1;

                }
            }
            finalMatrix.add(tempRow);

        }

        finalSolutionMatrix.add(finalMatrix);

        for(int k=0;k<finalSolutionMatrix.size();k++) {
            for (int i = 0; i < cntRows; i++) {
                for (int j = 0; j < cntCols; j++) {
                    if (j < cntCols - 1 ) {
                        System.out.print(finalSolutionMatrix.get(k).get(i).get(j));
                    } else if(j==cntCols-1 && i==cntRows-1 &&k!=finalSolutionMatrix.size()-1) {
                        System.out.println(finalSolutionMatrix.get(k).get(i).get(j)+"\n");
                    }
                    else {
                        System.out.println(finalSolutionMatrix.get(k).get(i).get(j));
                    }
                }
            }
        }

    }

    public boolean changeEmpty(ArrayList<ArrayList<Integer>> changes){
        for(int i=0;i<changes.size();i++){
            if(changes.get(i).size()!=0){
                return false;
            }
        }
        return true;
    }

    public void changeBack(ArrayList<ArrayList<Integer>> changes,ArrayList<ArrayList<Integer>> combs){
        for(int i = 0; i< combs.size();i++){
            for(int j=0;j<combs.get(i).size();j++){
                for(int k =0;k<changes.get(i).size();k++){
                    if(combs.get(i).contains(changes.get(i).get(k))){
                        combs.get(i).remove(changes.get(i).get(k));
                    }
                }
            }
        }
    }

    public ArrayList<ArrayList<Integer>> findUnconsistent(int indexOfRow, ArrayList<Integer> rowSolution, ArrayList<ArrayList<Integer>> unconsistentCombinations ){ //if row solution contradicts columns solution, add index of column solution to unconsistent
        ArrayList<ArrayList<Integer>> changes = new ArrayList<>();
        for(int i=0;i<cntCols;i++){
            ArrayList<Integer> temp = new ArrayList<>();
            changes.add(temp);
        }
        for(int i=0;i<cntCols;i++){
            for(int j=0;j<combinationsColumn.get(i).size();j++){
                if(!unconsistentCombinations.get(i).contains(j)) {  //if this solution isn't already in unconsistent
                    if (combinationsColumn.get(i).get(j).contains(indexOfRow + 1) && combinationsRows.get(indexOfRow).get(rowSolution.get(indexOfRow)).contains(i + 1)) {
                        continue;  //there is gap at the same field
                    } else if (combinationsColumn.get(i).get(j).contains(indexOfRow + 1) && !combinationsRows.get(indexOfRow).get(rowSolution.get(indexOfRow)).contains(i + 1)) {
                        unconsistentCombinations.get(i).add(j);//not same for row and column, add index of solution to unconsistent
                        changes.get(i).add(j);
                    } else if (!combinationsColumn.get(i).get(j).contains(indexOfRow + 1) && combinationsRows.get(indexOfRow).get(rowSolution.get(indexOfRow)).contains(i + 1)) {
                        unconsistentCombinations.get(i).add(j);  //not same for row and column, add index of solution to unconsistent
                        changes.get(i).add(j);
                    } else {
                        continue;  //field is filled for both
                    }
                }
            }
        }
        return changes;
    }

    public boolean isConsistent(ArrayList<ArrayList<Integer>>unconsistentCombinations){
        for(int i=0;i<cntCols;i++){  //for each colummn
            if(unconsistentCombinations.get(i).size()==combinationsColumn.get(i).size()){  //if for some column is same amount of restricted combinations as combinations created, stop
                return false;
            }

        }
        return true;
    }

    /**
     * Do the AC3 magic here.
     */
/*    private ... ac3(...) {

    }*/

}
