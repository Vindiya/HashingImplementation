import java.math.BigInteger;
import java.util.Random;

public class GetRandomNumber {

	public static BigInteger nextRandomBigInteger(BigInteger range) {
	    Random rand = new Random();
	    BigInteger result = new BigInteger(range.bitLength(), rand);
	    while( result.compareTo(range) >= 0 ) {
	        result = new BigInteger(range.bitLength(), rand);
	    }
	    return result;
	}
}
