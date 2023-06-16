import java.util.Random;

public class FIFO {

    public static void main(String[] args) {

        //Declare n number of processes
        int n = 25;

        //Generate array of n random numbers 1 - 300 these will represent burst times
        Random rd = new Random(); // creating Random object
        int[] burstTimes = new int[n];
        for (int i = 0; i < burstTimes.length; i++) {
            burstTimes[i] = rd.nextInt(300 - 1) + 1;; // storing random integers in an array
            //System.out.println(burstTimes[i]); // printing each array element
        }

        //Create array to store process ids 1 to n
        int[] processes = new int[n];
        for (int i = 0; i < processes.length; i++){
            processes[i] = i+1;
        }

        //Generate array to store wait times and then call method to find wait times
        int[] waitTimes = new int[n];
        findWaitingTimes(processes, burstTimes, waitTimes);
        System.out.println("Wait times:");
        for(int i = 0; i < waitTimes.length; i++){
            System.out.println(waitTimes[i]);
        }

        //Generate array to store turnaround times and then call method to find turnaround
        int[] turnAroundTimes = new int[n];
        findTurnAroundTimes(processes, burstTimes, waitTimes, turnAroundTimes);
        for(int i = 0; i < waitTimes.length; i++){
            System.out.println(turnAroundTimes[i]);
        }


        findAverageTime(processes, burstTimes, waitTimes, turnAroundTimes);

    }

    public static void findWaitingTimes(int[] processes, int[] burstTimes,
                                        int[] waitTimes) {

        // First process has a waiting time of 0
        waitTimes[0] = 0;

        // Find the waiting time for each process
        for(int i = 1; i < processes.length; i++) {
            waitTimes[i] = burstTimes[i - 1] + waitTimes[i - 1];
        }


    }

    public static void findTurnAroundTimes(int[] processes, int[] burstTimes,
                                           int[] waitTimes, int[] turnAroundTimes) {

        //Find turn around time for each process
        for(int i = 0; i < processes.length; i++) {
            turnAroundTimes[i] = burstTimes[i] + waitTimes[i];
        }

    }

    public static void findAverageTime(int[] processes, int[] burstTimes, int[] waitTimes, int[] turnAroundTimes) {

        int num = processes.length;

        // Find waiting times
        findWaitingTimes(processes, burstTimes, waitTimes);

        // Find turn around times
        findTurnAroundTimes(processes, burstTimes, waitTimes, turnAroundTimes);

        // Find total waiting time and total turn around time
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;

        for(int i = 0; i < num; i++) {
            totalWaitingTime = totalWaitingTime + waitTimes[i];
            totalTurnAroundTime = totalTurnAroundTime + turnAroundTimes[i];
        }

        // Find average waiting time
        float avgWaitTime = (float)totalWaitingTime / (float)num;

        // Find average turn around time
        float avgTurnAroundTime = (float)totalTurnAroundTime / (float)num;

        System.out.println("Average wait time: " + avgWaitTime + "\nAverage turnaround time: "+ avgTurnAroundTime);

    }
}
