import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CuckooHashing {
	private static List<BigInteger> keys1 = new ArrayList<BigInteger>();
	private static List<BigInteger> keys2 = new ArrayList<BigInteger>();
	private static int SIZE = 29, DATA_SIZE=28, DIVISOR1=11, DIVISOR2=29, MAX_VALUE=10000, KICK_COUNT=3;
	public static DataSet dataset = new DataSet(DATA_SIZE, MAX_VALUE);
	public static List<BigInteger> testData = dataset.randomNumbers;
	private static int currentLength = 0, countOfPasses=0;
	private static float loadFactor = 0;
	
	public static void main(String args[]) {
		// Print the test data - big random values
		System.out.println("Following is the random data:");
		for(BigInteger b: testData) {
			System.out.println(b);
		}
		System.out.println("Initializing both hash tables with default values");
		// Initialize the hashmap with default value of -1
		initializeHashTables(false);
		// Set the TEST DATA in Hash Tables
		System.out.println("Setting Key:Value pairs in hash tables via cuckoo hashing");
		setData();
		
		System.out.println("Key:value pair in hash table 1");
		for(int i=0; i<keys1.size(); i++) {
			System.out.println(""+i+": "+keys1.get(i));
		}
		System.out.println("Key:value pair in hash table 2");
		for(int i=0; i<keys2.size(); i++) {
			System.out.println(""+i+": "+keys2.get(i));
		}
	}
	
	public static void reHash() {
		System.out.println("*****************************************");
		System.out.println("Rehash!!!");
		System.out.println("*****************************************");
		System.out.println();
		currentLength=0;
		initializeHashTables(true);
		setData();
	}
	
	public static void setData() {
		Boolean isLoop = false;
		for(BigInteger bd: testData) {
			isLoop = set(bd, true, false, -1, -1);
			if(isLoop) break; // Infinite loop detected, hence rehash
			else {
				System.out.println(countOfPasses+", "+loadFactor);
			}
			countOfPasses=0;
		}
		if(isLoop) {
			reHash();
		}
	}
	
	public static void initializeHashTables(Boolean isReHash) {
		if(isReHash) {
			DIVISOR1++;
			System.out.println("New divisor for Hashing: "+DIVISOR1);
			System.out.println();
		}
		
		for(int i=0; i<SIZE; i++) {
			if(isReHash) keys1.set(i, BigInteger.valueOf(-1));
			else keys1.add(i, BigInteger.valueOf(-1));
		}
		for(int i=0; i<SIZE; i++) {
			if(isReHash) keys2.set(i, BigInteger.valueOf(-1));
			else keys2.add(i, BigInteger.valueOf(-1));
		}
	}
	
	public static Boolean set(BigInteger b, boolean isKeys1, boolean isKicked, int initialKey, int tableNumber) {
		if(isKicked) KICK_COUNT++;
		else if(KICK_COUNT>0) KICK_COUNT=0;
		System.out.println("Value to be hashed: "+b);
		if(isKeys1) {
			int key1 = HashFunction1(b);
			if(key1==initialKey && tableNumber==1) return true;
			System.out.println("Key to set in table 1: "+key1);
			if(keys1.get(key1)==BigInteger.valueOf(-1)) {
				countOfPasses++;
				keys1.set(key1, b);
				currentLength++;
				loadFactor = (float)currentLength/(float)(2*SIZE);
				System.out.println("Easily hashed in table 1 size: "+ keys1.size());
			} else {
				countOfPasses++;
				BigInteger kickOut = keys1.get(key1);
				System.out.println();
				System.out.println("Key to be kicked out from table 1: "+ kickOut);
				keys1.set(key1, b);
				currentLength++;
				loadFactor = (float)currentLength/(float)(2*SIZE);
				if(initialKey!= -1)return set(kickOut, false, true, initialKey, tableNumber);
				else return set(kickOut, false, true, key1, 1);
			}
		} else {
			int key2 = HashFunction2(b);
			if(key2==initialKey && tableNumber==2) return true;
			System.out.println("Key to set in table 2: "+ key2);
			if(keys2.get(key2)==BigInteger.valueOf(-1)) {
				countOfPasses++;
				System.out.println("Easily hashed in table 2 size: "+ keys2.size());
				keys2.set(key2, b);
				currentLength++;
				loadFactor = (float)currentLength/(float)(2*SIZE);
			} else {
				countOfPasses++;
				BigInteger kickOut = keys2.get(key2);
				System.out.println();
				System.out.println("Key to be kicked out from table 2: "+kickOut);
				keys2.set(key2, b);
				currentLength++;
				loadFactor = (float)currentLength/(float)(2*SIZE);
				if(initialKey!= -1) return set(kickOut, true, true, initialKey, tableNumber);
				else return set(kickOut, false, true, key2, 2);
			}
		}
		return false;
	}
	
	public static int HashFunction1(BigInteger b) {
		return Math.floorMod(b.intValue(), DIVISOR1);
	}

	public static int HashFunction2(BigInteger b) {
		return Math.floorMod((int)b.intValue()/DIVISOR1, DIVISOR2);
	}
	
}
