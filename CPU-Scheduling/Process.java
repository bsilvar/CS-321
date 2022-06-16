public class Process<T> implements Comparable<Process> {

    private int timeRemaining, arrivalTime, priority, timeNotProcessed;

    public Process(int currentTime, int maxProcessTime, int maxLevel) {
        arrivalTime = currentTime;
        timeRemaining = maxProcessTime;
        priority = maxLevel;
        timeNotProcessed = 0;
    }

    public void resetTimeNotProcessed() {
        timeNotProcessed = 0;
    }

    public void upTimeNotProcessed() {
        timeNotProcessed++;
    }

    public void reduceTimeRemaining() {
        timeRemaining--;
    }

    public void upPriority() {
        priority++;
    }

    public int getPriority() {
        return priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public int getTimeNotProcessed() {
        return timeNotProcessed;
    }

    public boolean finish() {
        return (timeRemaining == 0);
    }

    public int compareTo(Process other) {
        if(this.priority > other.priority) {
            return 1;
        }
        if(this.priority < other.priority) {
            return -1;
        }
        if(this.priority == other.priority) {
            // other process arrived later
            if(this.arrivalTime < other.arrivalTime) {
                return 1;
            }
            // other process arrived earlier
            if(this.arrivalTime > other.arrivalTime) {
                return -1;
            }
        }
        return 0;
    }

}

