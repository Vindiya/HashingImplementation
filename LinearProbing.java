import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

// 1. Generate an array of random numbers

// 2. write a hash function

// 3. create a hash table using arraylist and linked list

// 4. execute hash function on each number of initial array

// such that if there is an already existing item on that key of hashtable,
// then insert the item on the next key

public class LinearProbing {
	private static List<BigInteger> keys = new ArrayList<BigInteger>();
	private static int SIZE = 150, DATA_SIZE=125, DIVISOR=11, MAX_VALUE=500000000;
	private static int currentLength = 0;
	
	public static void main(String args[]) {
		DataSet dataset = new DataSet(DATA_SIZE, MAX_VALUE);
		List<BigInteger> testData = dataset.randomNumbers;
		// Fill the hashmap with default value
		for(int i=0; i<SIZE; i++) {
			keys.add(BigInteger.valueOf(-1));
		}
		// Set the random data in hashmap
		for(BigInteger bd: testData) {
			set(HashFunction(bd), bd);
		}
	}
	
	public static int HashFunction(BigInteger b) {
		return Math.floorMod(b.intValue(), DIVISOR);
	}
	
	public static void set(int key, BigInteger value) {
		int count=1;
		float loadfactor;
		if(keys.get(key)==BigInteger.valueOf(-1)) {
			keys.set(key, value);
			currentLength++;
			loadfactor = (float)currentLength/(float)SIZE;
			System.out.println(count+", "+loadfactor);
		} else {
			int i=key;
			while(count<SIZE) {
				
				i= i == SIZE-1 ? 0:i+1;
				count++;
				if(keys.get(i)==BigInteger.valueOf(-1)) {
					keys.set(i, value);
					currentLength++;
					loadfactor = (float)currentLength/(float)SIZE;
					System.out.println(count + ", "+loadfactor);
					return;
				}
			}
		}
	}
}
