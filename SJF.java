import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;

public class SJF {


	public static void main(String[] args) {

		//Prompt for number of processes and arrival time increment
		Scanner scan = new Scanner(System.in);
		System.out.println ("Enter no of process:");
		int n = scan.nextInt();
		System.out.println("Enter arrival time increment:");
		int increment = scan.nextInt();

		//Generate the 2D array
		//Column 1 = ProcessID, Column 2 = Burst time, Column 3 = Arrival time,
		//Column 4 = Wait time, Column 5 = Turnaround time,
		//Column 6 = Completion time, Column 7 is a flag to check if the process is finished
		int[][] processes = new int[n][7];
		generateProcesses(processes, n, increment);
		//Copy processes into new array. One for SJF and one for FIFO
		int[][] processes2 = processes.clone();
		System.out.println("Processes before completion: " + Arrays.deepToString(processes));
		executeSJF(processes, n);
		executeFIFO(processes2, n);

		
	}
	


	public static void generateProcesses(int[][] processes, int num, int increment){

		//Generate process IDs and burst times, burst times are random 1-100ms
		Random rand = new Random();
		for(int i = 0; i < num; i++) {
			processes[i][0] = i + 1;
			processes[i][1] = rand.nextInt(100-1)+1;
		}

		//Generate arrival times at the given increment. The first task arrives at 0.
		processes[0][2] = 0;
		for(int i = 1; i < num; i++){
			processes[i][2] = processes[i-1][2] + increment;
		}

	}


	public static void executeSJF(int[][] processes, int n){

		int sysTime = 0, total = 0; //st = System time, tot = total number of processes completed
		while(true)
		{
			int c=0, min=9999; //c represents the current process being completed, min is the lowest waiting burst time
			if (total == n) //if the total number of processes = completed processes then loop will be terminated
				break;
			for (int i=0; i<n; i++)
			{
				 //Loop through processes. If process arrival time <= system time, its flag=0, and burst<min
				 //That process will be executed first
				if ((processes[i][2] <= sysTime) && (processes[i][6] == 0) && (processes[i][1]<min)) {
					min=processes[i][1];
					c=i;
				}
			}
			// If c==n, c value cannot update because process arrival time < system time so we increase the system time
			if (c==n)
				sysTime++;
			else
			{
				processes[c][5]=sysTime+processes[c][1]; //Completion time = system time + burst time
				sysTime+=processes[c][1]; //New system time += burst time
				processes[c][4]=processes[c][5]-processes[c][2]; //Turnaround time = completion time - arrival time
				processes[c][3]=processes[c][4]-processes[c][1]; //Wait time = turnaround time - burst time
				processes[c][6]=1; //Flag process[c] as complete
				total++; //increment total complete processes
			}
		}

		//Now display process info and calculate avg wait and turnaround times
		int avgWait = 0, avgTurnAround = 0;
		for(int i=0;i<n;i++) {
			System.out.println("\n-----Process " + (i+1) + "-----");
			System.out.println("Arrival time: " + processes[i][2] + "ms");
			System.out.println("Burst time: " + processes[i][1] + "ms");
			System.out.println("Wait time: " + processes[i][3] + "ms");
			System.out.println("Turnaround time: " + processes[i][4] + "ms");
			System.out.println("Completion time: " + processes[i][5] + "ms");
			avgWait += processes[i][3];
			avgTurnAround += processes[i][4];
		}
		avgWait= avgWait/n;
		avgTurnAround = avgTurnAround/n;

		System.out.println("\nTotal time: " + sysTime + "ms");
		System.out.println("Average wait time: " + avgWait + "ms" + "\nAverage turnaround time: " + avgTurnAround + "ms");

	}

	public static void executeFIFO(int processes[][], int n){

		int sysTime = 0, total = 0; //st = System time, tot = total number of processes completed

		while(true)
		{
			int c=0, min=9999; //c represents the current process being completed, min is the lowest waiting burst time
			if (total == n) //if the total number of processes = completed processes then loop will be terminated
				break;

				for(int i = 0; i < n; i++){
					c = i;
					if(c == n){
						sysTime++;
					}else{
						processes[i][5]=sysTime+processes[i][1]; //Completion time = system time + burst time
						sysTime+=processes[i][1]; //New system time += burst time
						processes[i][4]=processes[i][5]-processes[i][2]; //Turnaround time = completion time - arrival time
						processes[i][3]=processes[i][4]-processes[i][1]; //Wait time = turnaround time - burst time
						processes[i][6]=1; //Flag process as complete
						total++; //increment total complete processes
					}

				}
		}
		//Now display process info and calculate avg wait and turnaround times
		int avgWait = 0, avgTurnAround = 0;
		for(int i=0;i<n;i++) {
			System.out.println("\n-----Process " + (i+1) + "-----");
			System.out.println("Arrival time: " + processes[i][2] + "ms");
			System.out.println("Burst time: " + processes[i][1] + "ms");
			System.out.println("Wait time: " + processes[i][3] + "ms");
			System.out.println("Turnaround time: " + processes[i][4] + "ms");
			System.out.println("Completion time: " + processes[i][5] + "ms");
			avgWait += processes[i][3];
			avgTurnAround += processes[i][4];
		}
		avgWait= avgWait/n;
		avgTurnAround = avgTurnAround/n;

		System.out.println("\nTotal time: " + sysTime + "ms");
		System.out.println("Average wait time: " + avgWait + "ms" + "\nAverage turnaround time: " + avgTurnAround + "ms");
	}

}
