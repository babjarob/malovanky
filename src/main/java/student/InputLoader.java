package student;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Use this class to load the input and generate the CSP definition.
 */
public class InputLoader {
    public student.Problem load(InputStream in) throws IOException {
        // load the file
        Scanner sc = new Scanner(in);
        String file = sc.nextLine();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String[] cnts = reader.readLine().split(",");
        int cntRows = Integer.parseInt(cnts[0]);
        int cntCols = Integer.parseInt(cnts[1]);



        // create instance of Problem class, initialize if you like
        student.Problem problem = new student.Problem();
        problem.cntCols = cntCols;
        problem.cntRows = cntRows;

        System.out.println("Nonogram has " + cntCols + " columns and " + cntRows + " rows.");

        // read row constraints
        for( int i=0; i<cntRows; i++){
            String[] line = reader.readLine().split(",");
            ArrayList<Pair<String, Integer>> tempLine = new ArrayList();
            for (int j = 0; j < line.length/2;j++){
                tempLine.add(new Pair(line[j*2],Integer.parseInt(line[j*2+1])));
            }
            problem.rulesRow.add(tempLine);

            // parse here
        }

        // read column constraints
        for( int i=cntRows; i<cntCols + cntRows; i++){
            String[] line = reader.readLine().split(",");
            ArrayList<Pair<String, Integer>> tempLine = new ArrayList();
            for (int j = 0; j < line.length/2;j++){
                tempLine.add(new Pair(line[j*2], Integer.parseInt(line[j*2+1])));
            }
            problem.rulesColumn.add(tempLine);

            // parse here
        }

        return problem;
    }
}
