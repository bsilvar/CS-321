import java.util.NoSuchElementException;

public class MaxHeap {

    private final int DEFAULT_SIZE = 10;
    private Process[] array;
    private int heapSize;

    public MaxHeap() {
        array = new Process[DEFAULT_SIZE];
        heapSize = 0;
    }

    public void maxHeapInsert(Process process) {
        if(heapSize == array.length-1) {
            increaseCapacity();
        }
        heapSize++;
        array[heapSize] = new Process(-1,-1,-1);
        heapIncreaseKey(heapSize, process);
    }

    // increases the priority of the new process
    public void heapIncreaseKey(int index, Process process) {
        if(process.compareTo(array[index]) < 0) {
            throw new IllegalStateException("new priority is smaller than current priority");
        }
        array[index] = process;
        while(index > 1 && array[parent(index)].compareTo(array[index]) < 0) {
            Process temp = array[index];
            array[index] = array[parent(index)];
            array[parent(index)] = temp;
            index = parent(index);
        }
    }

    // removes & returns Process w/ the biggest priority
    public Process heapExtractMax() {
        if(heapSize < 1) {
            throw new NoSuchElementException();
        }
        Process max = array[1];
        array[1] = array[heapSize];
        heapSize--;
        maxHeapify(1);
        return max;
    }

    // updates all processes following the one being processed
    public void update(int timeToIncrementLevel, int maxLevel, int index) {
        array[index].upTimeNotProcessed();
        if(array[index].getTimeNotProcessed() >= timeToIncrementLevel) {
            array[index].resetTimeNotProcessed();
            if(array[index].getPriority() < maxLevel) {
                array[index].upPriority();
                heapIncreaseKey(index, array[index]);
            }
        }
    }

    // helper methods
    private void maxHeapify(int index) {
        int largest;
        int left = left(index);
        int right = right(index);
        if(left <= heapSize && array[left].compareTo(array[index]) > 0) {
            largest = left;
        } else {
            largest = index;
        }
        if(right <= heapSize && array[right].compareTo(array[largest]) > 0) {
            largest = right;
        }
        if(largest != index) {
            Process temp = array[index];
            array[index] = array[largest];
            array[largest] = temp;
            maxHeapify(largest);
        }
    }

    private int parent(int index) {
        return (index/2);
    }

    private int left(int index) {
        return (2*index);
    }

    private int right(int index) {
        return (2*index + 1);
    }

    private void increaseCapacity() {
        Process[] newArray = new Process[array.length*2];
        for(int i = 1; i < array.length+1; i++) {
           newArray[i] = array[i];
        }
        array = newArray;
    }

}

