package OS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.completionTime = 0;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
    }
}

public class FIFOScheduling {

    public static void calculateTimes(List<Process> processes) {
        int currentTime = 0;
        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }
            process.completionTime = currentTime + process.burstTime;
            process.turnaroundTime = process.completionTime - process.arrivalTime;
            process.waitingTime = process.turnaroundTime - process.burstTime;
            currentTime += process.burstTime;
        }
    }

    public static double[] averageTimes(List<Process> processes) {
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        for (Process process : processes) {
            totalTurnaroundTime += process.turnaroundTime;
            totalWaitingTime += process.waitingTime;
        }

        double avgTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        double avgWaitingTime = (double) totalWaitingTime / processes.size();

        return new double[] { avgTurnaroundTime, avgWaitingTime };
    }

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 0, 4));
        processes.add(new Process(2, 1, 3));
        processes.add(new Process(3, 2, 2));
        processes.add(new Process(4, 3, 1));

        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        calculateTimes(processes);
        double[] averages = averageTimes(processes);

        for (Process process : processes) {
            System.out.println("Process " + process.pid + ": Waiting Time = " + process.waitingTime
                    + ", Turnaround Time = " + process.turnaroundTime);
        }

        System.out.println("Average Turnaround Time: " + averages[0]);
        System.out.println("Average Waiting Time: " + averages[1]);
    }
}