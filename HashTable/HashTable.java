import java.io.*;

public class HashTable {

    private int n; // # of keys
    private int m; // table size
    private int totalProbes, totalDuplicates;
    private boolean linearProbing, doubleHashing;
    private HashObject table[];

    public HashTable(int size, String probing) {
        if(!(probing.equalsIgnoreCase("linear") || probing.equalsIgnoreCase("double"))) {
            throw new IllegalArgumentException("invalid probing technique");
        }
        linearProbing = false;
        doubleHashing = false;
        if(probing.equals("linear")) {
            linearProbing = true;
        }
        if(probing.equals("double")) {
            doubleHashing = true;
        }
        n = 0;
        m = size;
        totalProbes = 0;
        totalDuplicates = 0;
        table = new HashObject[m];
    }

    public void insert(HashObject object) {
        int key = object.getKey();
        int index = 0;
        int i = 0;
        while(index != m) {
            object.upProbes();
            if(linearProbing) {
                i = linearProbing(key, index);
            }
            if(doubleHashing) {
                i = doubleHashing(key, index);
            }
            if(table[i] == null) {
                totalProbes += index+1;
                table[i] = object;
                n++;
                return;
            } else {
                if(table[i].equals(object)) {
                    totalDuplicates++;
                    table[i].upDuplicates();
                    return;
                }
                index++;
            }
        }
        throw new IllegalStateException("table is full");
    }

    public double getLoadFactor() {
        return (double) n/m;
    }

    public int getTotalDuplicates() {
        return totalDuplicates;
    }

    public double getAvgProbeCount() {
        return (double) totalProbes/n;
    }

    private int linearProbing(int key, int index) {
        int h1 = positiveMod(key, m);
        return ((h1 + index) % m);
    }

    private int doubleHashing(int key, int index) {
        int h1 = positiveMod(key, m);
        int h2 = 1 + positiveMod(key, m-2);
        return ((h1 + index*h2) % m);
    }

    // method for when hashCode() method returns negative integers
    private int positiveMod(int dividend, int divisor) {
        int value = dividend % divisor;
        if(value < 0) {
            value += divisor;
        }
        return value;
    }

    public void dump(String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter outFile = new PrintWriter(bw);

        String line;
        for(int i = 0; i < m; i++) {
            if(table[i] != null) {
                line = "table["+i+"]: " + table[i].toString();
                outFile.println(line);
            }
        }
        outFile.close();
    }

    public void dump2(String fileName) throws IOException {
        PrintStream ps = new PrintStream(fileName);
        PrintStream stdout = System.out;
        for(int i = 0; i < m; i++) {
            if(table[i] != null) {
                ps.append("table["+i+"]: " + table[i].toString() + "\n");
            }
        }
        System.setOut(ps);
        System.setOut(stdout);
    }

    public String toString() {
        String string = "";
        for(int i = 0; i < m; i++) {
            if(table[i] != null) {
                string += "table["+i+"]: " + table[i].toString() + "\n";
            }
        }
        return string;
    }

}

