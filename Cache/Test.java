import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {

	private static void usage() {
		System.out.println("usage:\njava Test 1 <cache size> <input textfile name> or\n" +
				"java Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>");
	}

	public static void main(String[] args) {
		try {
			long start = System.currentTimeMillis();

			if(args.length < 3 || args.length > 4) {
				throw new IllegalArgumentException("invalid number of arguments");
			}

			int level = Integer.parseInt(args[0]);
			if(!(level == 1 || level == 2)) {
				throw new IllegalArgumentException("invalid level");
			}

			String filename;
			int cache1Size = 0, cache2Size = 0;
			if(level == 1) {
				if(args.length != 3) {
					throw new IllegalArgumentException("invalid number of arguments for level " 
							+ level);
				}
				cache1Size = Integer.parseInt(args[1]);
				if(cache1Size < 1) {
					throw new IllegalArgumentException("invalid Cache size");
				}
				filename = args[2];
			} else {
				if(args.length != 4) {
					throw new IllegalArgumentException("invalid number of arguments for level " 
							+ level);
				}
				cache1Size = Integer.parseInt(args[1]);
				cache2Size = Integer.parseInt(args[2]);
				if(cache1Size < 1 || cache2Size < 1) {
					throw new IllegalArgumentException("invalid Cache size");
				}
				if(cache1Size >= cache2Size) {
					throw new IllegalArgumentException("invalid Cache 1 size");
				}
				filename = args[3];
			}

			Scanner scan = new Scanner(new File(filename));
			int cache1hits = 0, cache2hits = 0, totalHits;
			int cache1refs = 0, cache2refs = 0;
			double HR1, HR2, HRg;

			if(level == 1) {
				Cache<String> cache1 = new Cache<>(cache1Size);
				System.out.println("First level cache with "
						+ cache1Size +" entries has been created");
				while(scan.hasNext()) {
					String object = scan.next();
					cache1refs++;
					if(cache1.contains(object)) {
						cache1hits++;
						String removed = cache1.removeObject(object);
						cache1.addObject(removed);
					} else {
						if(cache1.isFull()) {
							cache1.removeLast();
						}
						cache1.addObject(object);
					}
				}

				HR1 = (double)cache1hits/cache1refs; // 1st-level hit ratio
				System.out.println("\nThe number of 1st-level references: "+ cache1refs);
				System.out.println("The number of 1st-level cache hits: "+ cache1hits);
				System.out.println("The 1st-level cache hit ratio:      "+ HR1);
			}

			if(level == 2) {
				Cache<String> cache1 = new Cache<>(cache1Size);
				System.out.println("First level cache with "
						+ cache1Size +" entries has been created");
				Cache<String> cache2 = new Cache<>(cache2Size);
				System.out.println("Second level cache with "
						+ cache2Size +" entries has been created");
				while(scan.hasNext()) {
					String object = scan.next();
					cache1refs++;
					if(cache1.contains(object)) {
						cache1hits++;
						cache1.removeObject(object);
						cache1.addObject(object);
						cache2.removeObject(object);
						cache2.addObject(object);
					} else {
						cache2refs++;
						if(cache2.contains(object)) {
							cache2hits++;
							cache2.removeObject(object);
							cache2.addObject(object);
							if(cache1.isFull()) { 
								cache1.removeLast();
							}
							cache1.addObject(object);
						} else {
							if(cache1.isFull()) { 
								cache1.removeLast();
							}
							if(cache2.isFull()) { 
								cache2.removeLast();
							}
							cache1.addObject(object);
							cache2.addObject(object);
						}
					}
				}

				totalHits = cache1hits + cache2hits; // total number of cache hits
				HRg = (double) totalHits/cache1refs; // global hit ratio
				HR1 = (double)cache1hits/cache1refs; // 1st-level hit ratio
				HR2 = (double)cache2hits/cache2refs; // 2nd-level hit ratio

				System.out.println("\nThe number of global references: " + cache1refs);
				System.out.println("The number of global cache hits: " + totalHits);
				System.out.println("The global hit ratio:            " + HRg);

				System.out.println("\nThe number of 1st-level references: "+ cache1refs);
				System.out.println("The number of 1st-level cache hits: "+ cache1hits);
				System.out.println("The 1st-level cache hit ratio:      "+ HR1);

				System.out.println("\nThe number of 2nd-level references: "+ cache2refs);
				System.out.println("The number of 2nd-level cache hits: "+ cache2hits);
				System.out.println("The 2st-level cache hit ratio:      "+ HR2);
			}

			scan.close();
			long end = System.currentTimeMillis() - start;
			System.out.println("\nelapsed time(secs): "+ end/1000);

		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			usage();
			System.exit(1);
		} catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			usage();
			System.exit(1);
		}

	}

}

