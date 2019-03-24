package student;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This is the entry point of the program.
 *
 * This structure is to help you with programming. If you need to slightly change some definitions, don't be afraid to do so.
 *
 */
public class CSPMain {
    public static void main(String[] args) {
        // load the input
        try {
            student.InputLoader loader = new student.InputLoader();
            student.Problem problem = loader.load(System.in);
            problem.computeArraySize();
            problem.createCombinations();
            problem.deleteUnconsistentCombs();
            student.Solver solver = new student.Solver(problem.combinationsRows,problem.combinationsColumn,problem.cntRows,problem.cntCols,problem.rulesRow);
            solver.solve();

            //ArrayList<ArrayList<HashSet<Integer>>> rowsCached = new ArrayList<>(problem.cntRows);
            //ArrayList<ArrayList<HashSet<Integer>>> colsCached = new ArrayList<>(problem.cntCols);

            /*for (int i = 0; i < problem.cntRows; i ++){
                ArrayList<HashSet<Integer>> columnForRow = new ArrayList<>(problem.cntCols);
                rowsCached.add(columnForRow);
                for (int j = 0; j < problem.cntCols; j ++) {
                    columnForRow.add(new HashSet<>());
                }
            }
            for (int i = 0; i < problem.cntCols; i ++){
                ArrayList<HashSet<Integer>> rowForColumn = new ArrayList<>(problem.cntRows);
                colsCached.add(rowForColumn);
                for (int j = 0; j < problem.cntRows; j ++) {
                    rowForColumn.add(new HashSet<>());
                }
            }


            for (int i = 0; i < problem.combinationsRows.size(); i++){
                for (int j = 0 ; j < problem.combinationsRows.get(i).size(); j++){
                    for (int k = 0; k < problem.combinationsRows.get(i).get(j).size(); k++){
                        Integer index = problem.combinationsRows.get(i).get(j).get(k);
                        rowsCached.get(i).get(index-1).add(j);
                    }
                }
            }

            for (int i = 0; i < problem.combinationsColumn.size(); i++){
                for (int j = 0 ; j < problem.combinationsColumn.get(i).size(); j++){
                    for (int k = 0; k < problem.combinationsColumn.get(i).get(j).size(); k++){
                        Integer index = problem.combinationsColumn.get(i).get(j).get(k);
                        colsCached.get(i).get(index-1).add(j);
                    }
                }
            }*/

            }
        catch(IOException exception) {
            exception.printStackTrace();
            return;
        }



        /*

        // run the solver
        Solver solver = new Solver();
        ... result = solver.solve(problem);

        if(result == null) {
            System.out.println("null");
        }
        else {
            // print out your solution
        }*/


    }
}
