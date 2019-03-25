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

        public void deleteUnconsistentCombs(){
            for (int i = 0;i<combinationsRows.size();i++){
                for(int j =0; j<combinationsRows.get(i).size();j++){  //vsetky mozne kombinacie
                    int numberofBlock = 0;  //index bloku v rules
                    int sizeOfBlock = rulesRow.get(i).get(numberofBlock).getValue();
                    String valueOfBlock = rulesRow.get(i).get(numberofBlock).getKey();
                    int tempSize =0;
                    for(int k = 0; k<cntCols;k++){  //cez vsetky policka v riadku
                          //velkost policka v danej vygenerovanej kombinacii
                        if(!combinationsRows.get(i).get(j).contains(k+1)&&tempSize<sizeOfBlock){
                            tempSize = tempSize +1;
                            if(tempSize==sizeOfBlock && numberofBlock +1 == rulesRow.get(i).size()){ //if i am at the last block
                                break;  //i've found combination which fits, leave loop
                            }else if(tempSize ==sizeOfBlock && numberofBlock + 1 < rulesRow.get(i).size()){
                                if(!combinationsRows.get(i).get(j).contains(k+2)&&valueOfBlock.equals(rulesRow.get(i).get(numberofBlock+1).getKey())){
                                    combinationsRows.get(i).remove(j);
                                    j=j-1;
                                    break;
                                }
                                numberofBlock = numberofBlock + 1;
                                sizeOfBlock =rulesRow.get(i).get(numberofBlock).getValue();
                                valueOfBlock=rulesRow.get(i).get(numberofBlock).getKey();
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
                    String valueOfBlock = rulesColumn.get(i).get(numberofBlock).getKey();
                    for(int k = 0; k<cntRows;k++){  //cez vsetky policka v riadku
                        //velkost policka v danej vygenerovanej kombinacii
                        if(!combinationsColumn.get(i).get(j).contains(k+1)&&tempSize<sizeOfBlock){
                            tempSize = tempSize +1;
                            if(tempSize==sizeOfBlock && numberofBlock +1 == rulesColumn.get(i).size()){ //if i am at the last block
                                break;  //i've found combination which fits, leave loop
                            }else if(tempSize ==sizeOfBlock && numberofBlock + 1 < rulesColumn.get(i).size()){
                                if(!combinationsColumn.get(i).get(j).contains(k+2)&&valueOfBlock.equals(rulesColumn.get(i).get(numberofBlock+1).getKey())){
                                    combinationsColumn.get(i).remove(j);
                                    j=j-1;
                                    break;
                                }
                                numberofBlock = numberofBlock + 1;
                                valueOfBlock = rulesColumn.get(i).get(numberofBlock).getKey();
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



}
