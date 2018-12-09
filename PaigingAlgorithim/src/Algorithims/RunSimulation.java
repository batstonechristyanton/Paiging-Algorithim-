package Algorithims;

/**
 * Paiging replacment policies implementation of Least Recently used and Random
 * <p>
 * Name : LeastRecentlyused & Random
 * <p>
 * Written by: Batstone Christyanton November 28 2018
 * <p>
 * Purpose :Calculate the page hits and faults of the algorithm understanding how memory works when fixed sizes exist
 * <p>
 * Usage: finding page hits and faults based off the algorithims behaviour of physical memory and swapspace m
 * <p>
 * Subroutines/Libraries Required : Run Simulation is responsible for all
 * the sub methods to run everything being passed in from the main method into this routine
 * in this subroutine i used a data file buffer reader and also an arraylist with
 * ioexception try catch method to check how well the try catch method is.
 *
 * Assumptions: It can be said that Least recently used algorithim may preform a bit better than the Random algorithim.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class RunSimulation {

    public final String COMMA_DELIMITER = ",";// comma delimiter to implement a string seperation to csv file
    public int swapSP = 15;                   /*Allocated swap memory space */
    public int numOfSpace = 10;                  //Allacoated number of space for physical memory defined so it is not fixed
    public int clock = 0;                        // clock counter to be updated through the program
    public boolean done = false;                               // done bool check to see when something has completed or not.
    public int jobTerminatingvalue = -999;          // job terminating value at -999 to be used to take out jobs based on this value
    public ArrayList deleteSwap = new ArrayList();          // arraylist so it can adjust dynamically used for the deleted jobs.
    public ArrayList<Pipeline> job = new <Pipeline>ArrayList();         // pipeline of jobs first used to feed the jobs in
    public Pipeline physicalMemory[] = new Pipeline[numOfSpace];// pipline array of physical memory set to the num of space
    public Pipeline swapSpace[] = new Pipeline[swapSP];         // pipline array of swapspace memory set to the swapspace
    public int pagehit = 0;                                     // Page hit counter to track the number of hits happening
    public int pageFaultCounter= 0;                             // page fault counter to track the number of hits happening
    public int firstLoadcounter = 0;                            // page first load counter to track the number of pages first loaded
    private Pipeline newjob;
    public int insufficentMemCounter =0;
    public int completed = 0;

    /**
     * This method is used to Load the Data files from the
     * switchstatment into this method to seperate them line by line
     * */
    public void addDatatocsv(String output) {

        String csvFile = output;   // strng that takes in the ouput file from the main as datafile and places it into the buffer and file reader.
        BufferedReader br = null;  // buffer reader is set to null before the file is loaded it reads the texts from character inputstream.
        String line = "";          // takes the text in the excel file and seperates them using a comma delimiter after in the

        // Try catch method utilized to check the
        try {

            br = new BufferedReader(new FileReader(csvFile));// placing the file into the file reader and into the buffer reader
            while ((line = br.readLine()) != null) {

                String[] jobsCheck = line.split((COMMA_DELIMITER));
                // for eac job add the first job number to the first array spot of the arraylist
                // secondly add the  unique refference number to the second part of the array list that holds jobs
                job.add(new Pipeline(Integer.parseInt(jobsCheck[0]), Integer.parseInt(jobsCheck[1])));

            }

        } catch (FileNotFoundException e) { // try catch to check if the csv files contain the correct information for files to load
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    /**
     * Random Simulation Run Method
     * Which will be responsible for swapping the jobs in swap an memory based on random numbers generated in sub methdos     *
     * */
    public void RandomSim() {

        int swapIndex = 0;
        int physicalEmpty = 0;
        while (job.size() > 0) {

            done = false;   // done is initally set to be false and

            newjob = job.remove(0);    // for every newjob that was passed into my newjob arraylist remove from the left of the array and move it to the right and readjust the list


            if (deleteSwap.contains(newjob.getJobnum())) { // If the deleteswap list contains the job number then contineu

            } else if (newjob.getJobUnRef() == jobTerminatingvalue ) {   // if the job unique refference of bot swap and physical memory eqal the job terminating value please delete
                Delete(); // if found jobs delete theses jobs based on the job numbers in both swap and physical memory that equal to the job unique refference

            } else {
                if (FindPagehit(newjob.getJobUnRef())) { // if job unique refference is found to be repeadily added to the physical memory then we increase the page hit counter
                    pagehit++;

                } else if (FindPageFaults(newjob.getJobUnRef())) {// if the page fault is fould based on how many jobs with unique refference numbers
                                                                  // do swap when page fault happens

                    if (!CheckArrayFull(physicalMemory)) {        // Checking if physical memory have any space
                        pageFaultCounter++;

                        swapIndex = findindex(swapSpace);         // find the spot of swap space that needs to be moved to phyisical

                        physicalEmpty = findEmptySpot(physicalMemory);// find an empty spot in physical memory and keep that index

                        swapFaultEmptyPhysicalSpot(swapIndex, physicalEmpty);// since a fualtt has occured  move the the values from swap memory to physical memory



                    } else if (!CheckArrayFull(swapSpace)) {  // check if the array for swapspace is full
                        pageFaultCounter++;
                        swapIndex = findindex(swapSpace);     // get the index of the swap space that is empty

                        int RandomNumberIndex = RandomNumber(); // based on the random number generate grab that index of the random number generated job from swap

                        int swapEmpty = findEmptySpot(swapSpace); // find an empty spot in swap space memory

                        swapForFullPhysicalSpace(swapEmpty, swapIndex, RandomNumberIndex); // since physical memory is not free and there is no room to place newjobs, from swap into physical memory

                    } else  { // delete jobs when there is no reoom in swap memory or Physical memory to load any more jobs
                        System.out.println("Insufficient memory");
                        ++insufficentMemCounter;
                        Delete(); // delete method call to
                }

                } else {
                    /* for the first time if all the conditions are met above this else
                       find spot in the physical memory and load the jobs*/

                    for (int i = 0; i < physicalMemory.length; i++) {

                        if (physicalMemory[i] == null) {   // checking to see if there is space in physical memory

                            newjob.setJobtime(++clock);    // updating the clock with new job set time

                            physicalMemory[i] = newjob;

                            done = true;                  // setting the bool to be done

                            ++firstLoadcounter;

                            i = physicalMemory.length;    // hitting the size of memory and refference each index of i

                        }
                        //
                    }
                    if (!done) {  // if the jobs are not able to be placed into physical memory because of the bool being set to be full

                        if (!CheckArrayFull(swapSpace)) {                   // check to see if the swapspace has any space in the array

                            int swapEmptyIndex = findEmptySpot(swapSpace);  // find the index from swapspace  wher it is empty and then use it for later

                            int randomNumber = RandomNumber();              // based on the random number generatored

                            swapSpace[swapEmptyIndex] = physicalMemory[randomNumber]; //place the random index number job into swapspace

                            physicalMemory[randomNumber] = newjob;  // when we have completed swapping into swap space place a new job into physical memory spac
                            firstLoadcounter++;
                        } else {  //else all these jobs cannot be placed in swap or in physical memory delete the jobs

                            Delete();
                            deleteSwap.add(newjob.getJobnum());

                        }
                    }
                }

            }

        }


    }
    /**
     * Least Recently Used Simulation Method which holds and calls other sub methods to complete the the algorithim.
     * */
    public void LruSim() {

        int swapIndex = 0; // declaring two int variables to be used later in the program
        int physicalEmpty = 0;// declaring physical empty in the program to be used later.

        while (job.size() > 0) {
            done = false;

            newjob = job.remove(0);// removing the first job that has been parsed into the array list and moving over the array to the left and readjusting the list dynamically

            if (deleteSwap.contains(newjob.getJobnum())) { // after deleting jobs at the end of the program add it to the delete swap arraylist and we check against the job number

            } else if (newjob.getJobUnRef() == jobTerminatingvalue) {  // if the job unique refference number = job terminating v value of -999 then call delete ethod

                Delete(); // if jobs are found based on jobuniquerefference number = -999 we remove jobs from bot arrays swap or physical memory.
                completed++;
            } else {

                if (FindPagehit(newjob.getJobUnRef())) {  // after deleteing jobs if there are jobs already in physical memory and there is the same jobe trying to be placed into

                    ++pagehit;  // if jobs are found in physical memory
                } else if (FindPageFaults(newjob.getJobUnRef())) { // find page faults when the same jobs are trying to be placed into swap space memory



                    if (!CheckArrayFull(physicalMemory)) { // check to see if the array if its not full
                        ++pageFaultCounter; // increase the page fault counter

                        swapIndex = findindex(swapSpace);  // if found full then get the swap space index where there is an index

                        physicalEmpty = findEmptySpot(physicalMemory); // physicalempty index to find an empty spot in physical memory

                        swapFaultEmptyPhysicalSpot(swapIndex, physicalEmpty);// swap what is in the swap index with what is in the physical empty spot


                    } else if (!CheckArrayFull(swapSpace)) {  // check to see if the swap array is full
                        ++pageFaultCounter; // increase the page fault counter

                        swapIndex = findindex(swapSpace);    // find an index of swap array where it is empty

                        int lruIndex = LRU();               // based on finding the least recently used job that is loaded into physical memory

                        int swapEmpty = findEmptySpot(swapSpace);  //  find an empty spot that is in swapspace memory

                        swapForFullPhysicalSpace(swapEmpty, swapIndex, lruIndex);// swap what is in physical memory based on lru into swap space memory and let physical memory get a new job.

                    } else {
                        ++insufficentMemCounter;
                        System.out.println("Insufficient memory");  // if both memorys are full and new jobs are still in the array list
                                                                    // and cannot be placed anywhere indicate there is not enough memory
                        Delete();

                    }
                } else {  // if all the condints are met then do this


                    for (int i = 0; i < physicalMemory.length; i++) {  // for the index of physical memory spot

                        if (physicalMemory[i] == null) {    // physical memory is checked first if it is null

                            newjob.setJobtime(++clock);    // set the time to go up

                            physicalMemory[i] = newjob;    // phyical memory is asked to grab a newjob from arraylist

                            done = true;                   // done is set to true

                            i = physicalMemory.length;   // when you hit the size of the array or break at that index

                            ++firstLoadcounter;         // move the counter for the first load up
                        }
                    }
                    if (!done) {   // if the physical memory is full then
                        if (!CheckArrayFull(swapSpace)) {  // check if there swap space array is not full

                            int swapEmptyIndex = findEmptySpot(swapSpace); // grab the index of the swap space that is empty

                            int lruIndex = LRU();                           // call the least recently used method and grab the index of the least recently used job

                            swapSpace[swapEmptyIndex] = physicalMemory[lruIndex]; // let the swap space memory equal to the the least recently used job from physical memory

                            newjob.setJobtime(++clock);                          // update the clock since there has been a new job being placed into the physical mempry

                            physicalMemory[lruIndex] = newjob;                  // grab the new job for physical memory

                            ++firstLoadcounter;                                 // first load counter is then updated.




                        } else {
                            newjob.setJobtime(++clock);                      // else updadat the job time

                            Delete();                                       // delete the jobs since they cannot be placed in physical or swapspace memory.



                            deleteSwap.add(newjob.getJobnum());
                        }
                    }
                }

            }

        }


    }
    /**
     * Deletes the jobs based on job numbers and sets the spot in
     * both swap space and physical memory to null so the next job can be placed into the spot
     */

    public void Delete() {

        // getting rid of jobs based on termination value between both swapspace and memeory
        for (int i = 0; i < physicalMemory.length; i++) {
            // checking the physical mempry is// not empty and then we check the job number against the job number from the arraylist

            if (physicalMemory[i] != null && physicalMemory[i].getJobnum() == newjob.getJobnum()) {

                physicalMemory[i] = null;  // setting the physical space of memory to be null
            }
        }
        for (int i = 0; i < swapSpace.length; i++) {

            if (swapSpace[i] != null && swapSpace[i].getJobnum() == newjob.getJobnum()) {// swapspace is checking to be seen if it is null

                swapSpace[i] = null;                    // setting the spot of swap space memory to be nul,

            }
        }
    }

    /***
     * Finding page hit method passing in job reference number from the simulations based on that
     *
     */

    public boolean FindPagehit(int jobRefferncenum) {
        for (int i = 0; i < physicalMemory.length; i++) {  // page hit is found in physical mempry

            // if the physical memory is not free and physical memory unique refference number
            // is equal to the job reffrence number passed in from the simulations
            if (physicalMemory[i] != null && physicalMemory[i].getJobUnRef() == jobRefferncenum) {
                /// set the clock to go forward by 1;
                clock++;
                physicalMemory[i].setJobtime(clock);
                return true;
            }
        }
        return false;
    }
    /**
     * Find page faults based on job refference number
     * */

    public boolean FindPageFaults(int jobRefferncenum) {
        // page fault happens within the swap space when there is the same unique refference number present at the same time

        for (int j = 0; j < swapSpace.length; j++) {
           // if the swapspace is not empty and the the unique reffrence number in swap space and the next job trying to be moved int swap are the same
            // page fault has happened
            if (swapSpace[j] != null && swapSpace[j].getJobUnRef() == jobRefferncenum) {
                swapSpace[j].setJobtime(++clock);// set the clock to move forward.
                return true;
            }
        }
        return false;
    }

    /**
     * Random number method to find numbers betwween 1 through 10 used to find the index of the physical memory
     * */
    public int RandomNumber() {
        int number = 0;     // instantiating a  number at 0

        Random rand = new Random(); // instantianting a number at random

        number = rand.nextInt(9); // number = to the next random number of 1-10

        return number;                  // return this number that is found
    }

    /**
     * Least recently used method  to find the job based on job time that is before what was loaded last
     * */
    public int LRU() {
        Pipeline LRU = physicalMemory[0]; // find the first index of physical memory and equal it to the LRU piplein

        int index = 0; // index  is instantiated to 0

        for (int i = 0; i < physicalMemory.length; i++) {

            // find all the job time of physical memory and compare it to the least recently used time being anyting before where physical memory is
            if (physicalMemory[i].getJobtime() < LRU.getJobtime() ) {

                LRU = physicalMemory[i];  // set the lru of piplline to which ever job number that has the time that is not current

                index = i; // find the exact spot in the array that has the job number which has not used recently
            }
        }
        return index;
    }

   /**
    * Find index method which finds the
    * */
    public int findindex(Pipeline[] array) { // passing in the pipline array

        for (int i = 0; i < array.length; i++) {
            // check to see if the array is null  find the spot of the array that checks against if the
            if (array[i] != null && array[i].getJobnum() == newjob.getJobnum() && array[i].getJobUnRef() == newjob.getJobUnRef()) {

                return i;
            }
        }
        return -1;
    }
    /**
     * Method to find space in physical memory and replace it with a an existing job from swam memory
     * */
    public void swapFaultEmptyPhysicalSpot(int swapIndex, int physicalIndex) {
        physicalMemory[physicalIndex] = swapSpace[swapIndex]; // setting physical memory to equal to
                                              // the swap space swap index holding an existing job that was placed initially
        swapSpace[swapIndex]=null;// the swap space spot is set to null.

    }

    /**
     * Check array full method that finds if the array has space or not
     * */
    public boolean CheckArrayFull(Pipeline[] arrayChecker) {

        boolean empty = false; // bool initailized to figure out if it is empty or not
        for (int a = 0; a < arrayChecker.length; a++) { // for the size of array iterate through each spot of the array

            if (arrayChecker[a] == null) {  // if the array is null then
                empty = false;              // set it to be false

                return false;              // return if it is false
            }
        }
        return true;                    // if the array is not full the return true indicating array is full
    }

    /**
     * find an empty spot method
     * */
    public int findEmptySpot(Pipeline[] array) {  // with any array passed find the physical spot that is not full
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                return i;                       // return the exact spot of the array and use it towards figuring out
                                                // the status of the array.
            }
        }
        return -1;
    }

    /**
     * placing the jobs from the physical memeory into swapspace
     * */
    public void swapForFullPhysicalSpace(int empty, int swapIndex, int lruIndex) {
        swapSpace[empty] = physicalMemory[lruIndex]; // getting the job from physical memory when
        clock++;
        swapSpace[swapIndex].setJobtime(clock);
        physicalMemory[lruIndex] = swapSpace[swapIndex]; // placing it into empty swap spot within swapspacememory
        swapSpace[swapIndex] = null;

    }
    /**
     * Print method used to bring output.
     * */
    public void print(String output) { // passing in the file to print out the file that the paiging algorithim is running

        if (physicalMemory != null) {
            System.out.println("\033[35m   PHYSICAL MEMORY SPACE \033[0m" + Arrays.toString(physicalMemory));
        }

        if (swapSpace != null) {
            System.out.print("\033[32m      SWAP MEMORY SPACE \033[0m" + "\033[36m " + Arrays.toString(swapSpace) + "\033[0m\n");
        }
        System.out.println("PageFault:" + pageFaultCounter);
        System.out.println("PageHit:" + pagehit);
        System.out.println("First Load:" + firstLoadcounter);
        System.out.println("insufficent memory:" + insufficentMemCounter);
        System.out.println("Completed Jobs: " + completed);
        System.out.println("File: " + output);


    }
}




