package uploadchecker;

public interface Info {
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String put(String key, String value);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String remove(String key);
	
	/**
	 * 
	 * @return
	 */
	public boolean isEmpty();
	
	/**
	 * 
	 * @return
	 */
	public String toFormatedString();
}
