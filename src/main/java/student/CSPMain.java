package student;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Ako domeny su volene riadky, variables su kombinacie indexov stlpcov, v ktorych sa pre dany riadok nachadza
 * nevyplnene policko, tj. pre problem s 11 stlpcami, kde pre dany riadok mame vstup (1,X,2,X) generujem
 * 8 prvkove polia s hodnotami od 1-11. Constraints sú pocitane tak, ze pre kazde policko v riadku prechadzam
 * kombinacie pre stlpce a vyradzujem tie, ktore obsahuju/neobsahuju (podla vybraneho riesenia pre riadok) vyplnene
 * policko (solver.isConsistent()). Ukladam si indexy rieseni pre dany stlpec, ktore su s riesenim nekonzistentne
 * a ak sa nejaky stlpec "vyprazdni", prerusim prehladavanie v danej vetve.
 */
public class CSPMain {
    public static void main(String[] args) {
        try {
            student.InputLoader loader = new student.InputLoader();
            student.Problem problem = loader.load(System.in);
            System.out.println("Loaded");
            problem.computeArraySize();
            System.out.println("Size");
            problem.createCombinations();
            System.out.println("Combinations created");
            problem.deleteUnconsistentCombs();
            System.out.println("Unconsistent deleted");
            student.Solver solver = new student.Solver(problem.combinationsRows,problem.combinationsColumn,problem.cntRows,problem.cntCols,problem.rulesRow);
            solver.solve();
            solver.printSolution();
            if(solver.finalSolutionMatrix.isEmpty()){
                System.out.println("null");
            }
            }
        catch(IOException exception) {
            exception.printStackTrace();
            return;
        }

    }
}
