public class HashObject<T> {

    private T object;
    private int probes; // # of entries checked
    private int duplicates; // frequency

    public HashObject(T object) {
        this.object = object;
        probes = duplicates = 0;
    }

    public int getKey() {
        return object.hashCode();
    }

    public int getProbes() {
        return probes;
    }

    public int getDuplicates() {
        return duplicates;
    }

    public void upProbes() {
        probes++;
    }

    public void upDuplicates() {
        duplicates++;
    }

    public boolean equals(Object other) {
        return other.equals(object);
    }

    public String toString() {
        return String.format("%s %d %d", object.toString(), duplicates, probes);
    }

}

