package uploadchecker;


public class StringUtils {
	private StringUtils() {};
	
	public static String remove(String original, String[] reps) {
		if (original == null || reps == null) return null;
		else if (original.length() <= 0) return original;
		else {
			String res = original;
			for (String rep : reps)
				res = res.replace(rep, "");
			return res;
		}
	}
}
