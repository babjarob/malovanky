package student;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Use this class to hold your CSP definition: variables, domains, constraints. Pass this object wherever needed.
 */
public class Problem {

    public int cntRows;
    public int cntCols;
    public ArrayList<ArrayList<Pair<String, Integer>>> rulesColumn = new ArrayList<>(); //ArrayList ktory obsahuje array list s pravidlami pre konkretny column
    public ArrayList<ArrayList<Pair<String, Integer>>> rulesRow = new ArrayList<>();

    public ArrayList<List<ArrayList<Integer>>> combinationsColumn = new ArrayList<>();
    public ArrayList<List<ArrayList<Integer>>> combinationsRows = new ArrayList<>();

    ArrayList<Integer> sizeOfCols = new ArrayList<>();
    ArrayList<Integer> sizeOfRows = new ArrayList<>();

    public void computeArraySize(){
        for (int i = 0; i < cntRows; i++){
            int sum = 0;
            for (int j = 0; j < rulesRow.get(i).size(); j++){
                sum += rulesRow.get(i).get(j).getValue();
            }
            sizeOfRows.add(cntCols - sum);
        }

        for (int i = 0; i < cntCols; i++){
            int sum = 0;
            for (int j = 0; j < rulesColumn.get(i).size(); j++){
                sum += rulesColumn.get(i).get(j).getValue();
            }
            sizeOfCols.add(cntRows - sum);
        }
    }

    public void createCombinations(){
        for (int i = 0; i < cntCols; i++){
            List<ArrayList<Integer>> colsCombs = new ArrayList<>();
            colsCombs = combine(new ArrayList<Integer>(sizeOfCols.get(i)),1,cntRows, sizeOfCols.get(i), colsCombs);
            combinationsColumn.add(colsCombs);
        }

        for (int i = 0; i < cntRows; i++){
            List<ArrayList<Integer>> rowsCombs = new ArrayList<>();
            rowsCombs = combine(new ArrayList<Integer>(sizeOfRows.get(i)),1,cntCols, sizeOfRows.get(i), rowsCombs);
            combinationsRows.add(rowsCombs);
        }
    }

    public List<ArrayList<Integer>> combine(ArrayList<Integer> vector, int index, int maxValue, int size, List<ArrayList<Integer>> setOfCombs){
        if(vector.size() == size){
            setOfCombs.add(vector);
            return setOfCombs;
        }else{
            for(int i = index; i<= maxValue - size + index && i <= maxValue; i++ ){
                ArrayList<Integer> newVector = new ArrayList<>(vector);
                newVector.add(i);
                combine(newVector,i + 1, maxValue,size,setOfCombs);
            }
        }
        return setOfCombs;
    }

    public void addToSolution(ArrayList<Integer> gapsInColumn, int indexOfColumn, ArrayList<ArrayList<Integer>> solution){  //row,column
        for (int i = 0; i < cntRows; i++){
            if(gapsInColumn.contains(i+1)) {
                solution.get(i).set(indexOfColumn, 1);  //field is filled
            }else{
                solution.get(i).set(indexOfColumn,0);  //field is empty
            }
        }
    }

    public boolean checkRow(ArrayList<ArrayList<Integer>> solution){
        int numberOfBlock = 0;
        int counter = 0;
        for(int i = 0; i<cntRows; i++){
            for(int j = 0; j<cntCols;j++){
                int sizeOfBlock = rulesRow.get(i).get(numberOfBlock).getValue();  //how many fields block takes
                if(solution.get(i).get(j)==1){  //if you find filled field
                    counter = counter + 1;
                    if(counter == sizeOfBlock && solution.get(i).get(j+1)<=0 && j<cntCols-1){  //pozor na to, poriesit okraj
                        numberOfBlock = numberOfBlock+1;
                        counter = 0;
                        if(numberOfBlock  ==rulesRow.get(i).size()){
                            numberOfBlock = 0;
                            break;  //go to next row, this one is ok
                        }
                    }else{
                        //System.out.println("Breaking!!!");
                        return false;}
                }
            }

            }

        return true;
        }

        public void deleteUnconsistentCombs(){
            for (int i = 0;i<combinationsRows.size();i++){
                for(int j =0; j<combinationsRows.get(i).size();j++){  //vsetky mozne kombinacie
                    int numberofBlock = 0;  //index bloku v rules
                    int sizeOfBlock = rulesRow.get(i).get(numberofBlock).getValue();
                    int tempSize =0;
                    for(int k = 0; k<cntRows;k++){  //cez vsetky policka v riadku
                          //velkost policka v danej vygenerovanej kombinacii
                        if(!combinationsRows.get(i).get(j).contains(k+1)&&tempSize<sizeOfBlock){
                            tempSize = tempSize +1;
                            if(tempSize==sizeOfBlock && numberofBlock +1 == rulesRow.get(i).size()){ //if i am at the last block
                                break;  //i've found combination which fits, leave loop
                            }else if(tempSize ==sizeOfBlock && numberofBlock + 1 < rulesRow.get(i).size()){
                                numberofBlock = numberofBlock + 1;
                                sizeOfBlock =rulesRow.get(i).get(numberofBlock).getValue();
                                tempSize = 0;
                            }else if(tempSize < sizeOfBlock){continue;}
                            else{
                                combinationsRows.get(i).remove(j);
                                j=j-1;
                                break;
                            }

                            }else if(combinationsRows.get(i).get(j).contains(k+1)&&tempSize!=0){
                                combinationsRows.get(i).remove(j);
                                j=j-1;
                                break;
                        }
                        }

                    }
                }
            for (int i = 0;i<combinationsColumn.size();i++){
                for(int j =0; j<combinationsColumn.get(i).size();j++){  //vsetky mozne kombinacie
                    int numberofBlock = 0;  //index bloku v rules
                    int sizeOfBlock = rulesColumn.get(i).get(numberofBlock).getValue();
                    int tempSize =0;
                    for(int k = 0; k<cntCols;k++){  //cez vsetky policka v riadku
                        //velkost policka v danej vygenerovanej kombinacii
                        if(!combinationsColumn.get(i).get(j).contains(k+1)&&tempSize<sizeOfBlock){
                            tempSize = tempSize +1;
                            if(tempSize==sizeOfBlock && numberofBlock +1 == rulesColumn.get(i).size()){ //if i am at the last block
                                break;  //i've found combination which fits, leave loop
                            }else if(tempSize ==sizeOfBlock && numberofBlock + 1 < rulesColumn.get(i).size()){
                                numberofBlock = numberofBlock + 1;
                                sizeOfBlock =rulesColumn.get(i).get(numberofBlock).getValue();
                                tempSize = 0;
                            }else if(tempSize < sizeOfBlock){continue;}
                            else{
                                combinationsColumn.get(i).remove(j);
                                j=j-1;
                                break;
                            }

                        }else if(combinationsColumn.get(i).get(j).contains(k+1)&&tempSize!=0){
                            combinationsColumn.get(i).remove(j);
                            j=j-1;
                            break;
                        }
                    }

                }
            }
            }

     public ArrayList<ArrayList<Boolean>> initializeFinalSolution(){
         ArrayList<ArrayList<Boolean>> finalSolution = new ArrayList<>();
         for(int i = 0; i<cntRows ; i++){
             ArrayList<Boolean> row = new ArrayList<>();
             for(int j = 0; j<cntCols ; j++){

                 row.add(null);
                 if(j==cntCols - 1){
                     finalSolution.add(row);
                 }
             }

         }
         return finalSolution;
     }

     public void deleteSameColor(){
        for(int i=0;i<cntRows;i++){
            for(int j=0;j<combinationsRows.get(i).size();j++){

            }
        }
     }



}
