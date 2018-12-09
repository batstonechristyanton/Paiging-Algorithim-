/**
 * Paiging replacment policies implementation of Least Recently used and Random
 * <p>
 * Name : LeastRecentlyused & Random Algoritms implemented
 * <p>
 * Written by: Batstone Christyanton November 28 2018
 * <p>
 * Purpose :Calculate the page hits and faults of the algorithm understanding how memory works when fixed sizes exist
 * <p>
 * Usage: Main program to run the the different simulation
 * <p>
 * Subroutines/Libraries Required : Uses pipeline class and Runformulation class the program is mostly built on the
 * Runimulation and the rest of it is supporting classes for the program.
 *
 * Assumptions: Least recently used method would be a efficent paiging algorithim policy
 */
package Algorithims;
import java.util.Scanner;
class Main {

    public static void main(String args[]) {
        /**
         * choice is intialzed for the switchstatment
         * exit is intialized at letter n because it would be the ending case
         * scanner in new used to take input from user.
         * */
        int choice = 0;
        char exit = 'n';
        String dataFile = "";
        Scanner in = new Scanner(System.in);
        /**
         * While loop to run the options of the switch stament and end when the user inputs a y
         * */
        while (exit != 'y') {
            System.out.println("\n \033[36m PAIGING ALGORITHIM SIMULATION\033[0m\n");
            System.out.println("\033[32m CSV FILE OPTIONS\033[0m\n");
            System.out.println("\033[34m Input option 1 = CSV File 1 \033[0m");
            System.out.println("\033[34m Input option 2 = CSV File 2 \033[0m");
            System.out.println("\033[34m Input option 3 = CSV File 4 \033[0m");
            System.out.println("\033[34m Input option 4 = CSV File 4 \033[0m");
            System.out.println("\033[34m Input Option 5 = CSV File 5\033[0m");
            System.out.println("\033[34m Input optiion 6 = CSV File 6\033[0m\n");

            System.out.print("\033[31m Please Enter Which Csv File You Would Like To Run\033[0m \n");
            String input;
            /**
             * Switch statment to run the different pages of csv
             * */
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    dataFile = "job_data_1.csv";
                    break;
                case 2:
                    dataFile = "job_data_2.csv";
                    break;
                case 3:
                    dataFile = "job_data_3.csv";
                    break;
                case 4:
                    dataFile = "job_data_4.csv";
                    break;
                case 5:
                    dataFile = "job_data_5.csv";
                    break;
                case 6:
                    dataFile = "job_data_6.csv";
                default:
                    System.out.println("THE PROGRAM ENDED PLEASE RUN AGAIN ");
                    break;

            }
            /**
             * Run simulation instanizated to run the simulation method for the random number generation
             * instering the diffrent data files throught the run simulation class
             * pring method to bring whats left in the spaces of the array
             * */
            RunSimulation RandomSimulation = new RunSimulation();
            System.out.println("\033[34m RANDOM PAIGING ALGORITHIM \033[0m\n");
            RandomSimulation.addDatatocsv(dataFile);
            RandomSimulation.RandomSim();
            RandomSimulation.print(dataFile);
            /**
             * Run simulation to run the Least recently used method in my runsimulation class
             * instering the diffrent data files through tthe run simulation method class
             * print method to display whats left in the spaces of the array
             * */
            RunSimulation LruSimulation = new RunSimulation();
            System.out.println("\nLEAST RECENTLY USED ALGORITHIM\n");
            LruSimulation.addDatatocsv(dataFile);
            LruSimulation.LruSim();
            LruSimulation.print(dataFile);

            System.out.print("Would you like to quit please enter y/n \n");
            input = in.next().toLowerCase();
            exit = input.charAt(0);

        }
    }
}


