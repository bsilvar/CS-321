
public class PQueue extends MaxHeap {

    private int size;

    public PQueue() {
        super();
        size = 0;
    }

    public void enPQueue(Process process) {
        size++;
        super.maxHeapInsert(process);
    }

    public Process dePQueue() {
        size--;
        return super.heapExtractMax();
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public void update(int timeToIncrementLevel, int maxLevel) {
        for(int i = 1; i <= size; i++) {
            super.update(timeToIncrementLevel, maxLevel, i);
        }
    }

}

