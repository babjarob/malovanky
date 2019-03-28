package student;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            colsCombs = combineCols(new ArrayList<Integer>(sizeOfCols.get(i)),1,cntRows, sizeOfCols.get(i), colsCombs,i);
            combinationsColumn.add(colsCombs);
        }

        for (int i = 0; i < cntRows; i++){
            List<ArrayList<Integer>> rowsCombs = new ArrayList<>();
            rowsCombs = combineRows(new ArrayList<Integer>(sizeOfRows.get(i)),1,cntCols, sizeOfRows.get(i), rowsCombs,i);
            combinationsRows.add(rowsCombs);
        }
    }

    public List<ArrayList<Integer>> combineRows(ArrayList<Integer> vector, int index, int maxValue, int size, List<ArrayList<Integer>> setOfCombs,int indexRow){
        if(vector.size() == size){
            setOfCombs.add(vector);
            return setOfCombs;
        }else{
            for(int i = index; i<= maxValue - size + index && i <= maxValue; i++ ){
                ArrayList<Integer> newVector = new ArrayList<>(vector);
                newVector.add(i);
                if(obeysRulesRows(indexRow,newVector)) {
                    combineRows(newVector, i + 1, maxValue, size, setOfCombs,indexRow);
                }
            }
        }
        return setOfCombs;
    }

    public List<ArrayList<Integer>> combineCols(ArrayList<Integer> vector, int index, int maxValue, int size, List<ArrayList<Integer>> setOfCombs,int indexColumn){
        if(vector.size() == size){
            setOfCombs.add(vector);
            return setOfCombs;
        }else{
            for(int i = index; i<= maxValue - size + index && i <= maxValue; i++ ){
                ArrayList<Integer> newVector = new ArrayList<>(vector);
                newVector.add(i);
                if(obeysRulesColumns(indexColumn,newVector)) {
                    combineCols(newVector, i + 1, maxValue, size, setOfCombs,indexColumn);
                }
            }
        }
        return setOfCombs;
    }

        public boolean obeysRulesRows(int i,ArrayList<Integer> vector){ //i=number of row
                    int numberofBlock = 0;  //index bloku v rules
                    int sizeOfBlock = rulesRow.get(i).get(numberofBlock).getValue();
                    int tempSize =0;
                    for(int k = 0; k<cntCols;k++){  //cez vsetky policka v riadku
                          //velkost policka v danej vygenerovanej kombinacii
                        if(!vector.contains(k+1)&&tempSize<sizeOfBlock){
                            tempSize = tempSize +1;
                            if(tempSize==sizeOfBlock && numberofBlock +1 == rulesRow.get(i).size()){ //if i am at the last block
                                return true;  //i've found combination which fits, leave loop
                            }else if(tempSize ==sizeOfBlock && numberofBlock + 1 < rulesRow.get(i).size()){
                                numberofBlock = numberofBlock + 1;
                                sizeOfBlock =rulesRow.get(i).get(numberofBlock).getValue();
                                tempSize = 0;
                            }else if(tempSize < sizeOfBlock){continue;}
                            else{
                                return false;
                            }

                            }else if(vector.contains(k+1)&&tempSize!=0){
                                return false;
                        }
                        }return false;}

              //vsetky mozne kombinacie
            public boolean obeysRulesColumns(int i,ArrayList<Integer> vector){
                    int numberofBlock = 0;  //index bloku v rules
                    int sizeOfBlock = rulesColumn.get(i).get(numberofBlock).getValue();
                    int tempSize =0;
                    for(int k = 0; k<cntRows;k++){  //cez vsetky policka v riadku
                        //velkost policka v danej vygenerovanej kombinacii
                        if(!vector.contains(k+1)&&tempSize<sizeOfBlock){
                            tempSize = tempSize +1;
                            if(tempSize==sizeOfBlock && numberofBlock +1 == rulesColumn.get(i).size()){ //if i am at the last block
                                return true;  //i've found combination which fits, leave loop
                            }else if(tempSize ==sizeOfBlock && numberofBlock + 1 < rulesColumn.get(i).size()){
                                numberofBlock = numberofBlock + 1;
                                sizeOfBlock =rulesColumn.get(i).get(numberofBlock).getValue();
                                tempSize = 0;
                            }else if(tempSize < sizeOfBlock){continue;}
                            else{
                                return false;
                            }

                        }else if(vector.contains(k+1)&&tempSize!=0){
                            return false;
                        }
                    }
                    return false;


            }


    public void deleteUnconsistentCombs(){ //vymaze kombinacie, v ktorych sa nenachadza medzera medzi blokmi rovnakej farby
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
                        }

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
                        }

                    }
                }

            }
        }
    }


}
