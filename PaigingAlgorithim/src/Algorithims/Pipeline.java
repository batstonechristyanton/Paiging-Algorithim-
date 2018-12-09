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
 * Subroutines/Libraries Required : jobs from the csv file is setup to the properies of pipeline as a job number
 * job unique refference numbers then througout the program i set the clock as jobtime.
  *
 * Assumptions: Property classes of job numbers and job time
 */
public class Pipeline {

    private int Jobnum;
    private int JobUnRef;
    private int jobtime;

    public Pipeline() { // default constructor of my pipeline
        Jobnum = 0;     // all values set to 0
        JobUnRef = 0;
        jobtime = 0;
    }

    public Pipeline(int jobnum, int jobUnRef) { // equalling the private job unnreff and job num to eachother
        Jobnum = jobnum;
        JobUnRef = jobUnRef;
    }

    public int getJobnum() {
        return Jobnum;
    }  // getter for job time

    public void setJobnum(int jobnum) {
        Jobnum = jobnum;
    }  // setter for jpb num

    public int getJobUnRef() {
        return JobUnRef;
    }           // gettter for job unique refference

    public void setJobUnRef(int jobUnRef) {
        JobUnRef = jobUnRef;
    } // setter for job uniqe reffernce number


    public int getJobtime() {
        return jobtime;
    }               // getter for job time

    public void setJobtime(int jobtime) {
        this.jobtime = jobtime;
    } // setter for job time

    @Override
    public String toString() {
        return String.valueOf(this.Jobnum) + "," + String.valueOf(this.JobUnRef); // to string method to retrive the exact job number and job unique reffrence number

    }


}

