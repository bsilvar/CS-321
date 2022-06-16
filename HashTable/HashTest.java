import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class HashTest {

    public static void usage() {
        System.out.println("usage: java HashTest <input type> <load factor> [<debug level>]");
    }

    public static void main(String[] args) {
        try {
            if(args.length < 2 || args.length > 3) {
                throw new IllegalArgumentException("invalid number of arguments");
            }

            int source = Integer.parseInt(args[0]);
            if(!(source == 1 || source == 2 || source == 3)) {
                throw new NumberFormatException("invalid -- source type should be 1, 2, or 3");
            }

            double loadFactor = Double.parseDouble(args[1]);
            if(loadFactor < 0 || loadFactor > 1) {
                throw new IllegalArgumentException("invalid -- load factor should be between 0 and 1");
            }

            int debug = 0;
            if(args.length == 3) {
                debug = Integer.parseInt(args[2]);
                if(!(debug == 0 || debug == 1)) {
                    throw new IllegalArgumentException("invalid -- valid debug levels are 0 or 1");
                }
            }

            TwinPrimes twins = new TwinPrimes(95500,96000);
            int tableSize = twins.getSecondTwin();
            System.out.println("A good table size is found: " + tableSize);

            HashTable table1 = new HashTable(tableSize, "linear");
            HashTable table2 = new HashTable(tableSize, "double");

            if(source == 1) {
                System.out.println("Data source type: java.util.Random\n\n");
                Random random = new Random();
                int integer;

                int inputCount = 0;
                while(table1.getLoadFactor() < loadFactor) {
                    integer = random.nextInt();
                    HashObject<Integer> object1 = new HashObject<>(integer);
                    HashObject<Integer> object2 = new HashObject<>(integer);
                    table1.insert(object1);
                    table2.insert(object2);
                    inputCount++;
                }

                System.out.println("Using Linear Hashing....\nInput " +
                        inputCount + " elements, of which " +
                        table1.getTotalDuplicates() + " duplicates\nload factor = " +
                        loadFactor + ", Avg. no. of probes " + table1.getAvgProbeCount());

                System.out.println("\nUsing Double Hashing....\nInput " +
                        inputCount + " elements, of which " +
                        table2.getTotalDuplicates() + " duplicates\nload factor = " +
                        loadFactor + ", Avg. no. of probes " + table2.getAvgProbeCount());

                if(debug == 1) {
                    table1.dump("linear-dump");
                    table2.dump("double-dump");
                }
            }

            if(source == 2) {
                System.out.println("Data source type: System.currentTimeMillis()\n\n");
                long longValue;

                int inputCount = 0;
                while(table1.getLoadFactor() < loadFactor) {
                    longValue = System.currentTimeMillis();
                    HashObject<Long> object1 = new HashObject<>(longValue);
                    HashObject<Long> object2 = new HashObject<>(longValue);
                    table1.insert(object1);
                    table2.insert(object2);
                    inputCount++;
                }

                System.out.println("Using Linear Hashing....\nInput " +
                        inputCount + " elements, of which " +
                        table1.getTotalDuplicates() + " duplicates\nload factor = " +
                        loadFactor + ", Avg. no. of probes " + table1.getAvgProbeCount());

                System.out.println("\nUsing Double Hashing....\nInput " +
                        inputCount + " elements, of which " +
                        table2.getTotalDuplicates() + " duplicates\nload factor = " +
                        loadFactor + ", Avg. no. of probes " + table2.getAvgProbeCount());

                if(debug == 1) {
                    table1.dump("linear-dump");
                    table2.dump("double-dump");
                }
            }

            if(source == 3) {
                Scanner scan = new Scanner(new File("word-list"));
                System.out.println("Data source type: word-list\n\n");
                String word;

                int inputCount = 0;
                while(table1.getLoadFactor() < loadFactor && scan.hasNext()) {
                    word = scan.nextLine();
                    HashObject<String> object1 = new HashObject<>(word);
                    HashObject<String> object2 = new HashObject<>(word);
                    table1.insert(object1);
                    table2.insert(object2);
                    inputCount++;
                }

                System.out.println("Using Linear Hashing....\nInput " +
                        inputCount + " elements, of which " +
                        table1.getTotalDuplicates() + " duplicates\nload factor = " +
                        loadFactor + ", Avg. no. of probes " + table1.getAvgProbeCount());

                System.out.println("\nUsing Double Hashing....\nInput " +
                        inputCount + " elements, of which " +
                        table2.getTotalDuplicates() + " duplicates\nload factor = " +
                        loadFactor + ", Avg. no. of probes " + table2.getAvgProbeCount());

                if(debug == 1) {
                    table1.dump("linear-dump");
                    table2.dump("double-dump");
                }
            }
        } catch(NumberFormatException e) {
            System.out.println(e.getMessage());
            usage();
            System.exit(1);
        } catch(IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
            usage();
            System.exit(1);
        }

    }

}

