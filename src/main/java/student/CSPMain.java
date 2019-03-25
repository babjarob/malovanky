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
