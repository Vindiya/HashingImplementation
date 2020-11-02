import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DataSet {
	List<BigInteger> randomNumbers = new ArrayList<BigInteger>();
	public DataSet(int range, int maxValue) {
		// creates an arraylist of n random numbers 
		for(int i=0; i<range; i++) {
			BigInteger randBigInt = GetRandomNumber.nextRandomBigInteger(BigInteger.valueOf(maxValue));
			 randomNumbers.add(randBigInt);
		}
	}
	public void printData() {
		for(BigInteger bd: randomNumbers) {
			System.out.println(bd);
		}
	}
}
