package uploadchecker;

/**
 * This class represents Text information
 * of MediaInfo output.
 * @author Tiago Santos
 * 
 * @see AbstractInfo
 * @see Encoding
 * @version 1.0
 * @since JDK 1.6
 */
public class Video extends AbstractInfo {
    protected Encoding encodingSettings;

    public Video() {
        encodingSettings = new Encoding();
    }
    
    public String putEncoding(String key, String value) {
    	return encodingSettings.put(key, value);
    }
    
    public String removeEncoding(String key) {
    	return encodingSettings.remove(key.toLowerCase());
    }
    
    public String getEncoding(String key) {
    	return encodingSettings.get(key.toLowerCase());
    }

    public String get(String key, boolean encoding) {
        return encoding ? encodingSettings.get(key.toLowerCase())
                : get(key.toLowerCase());
    }

    @Override
    public String toFormatedString() {
        String res = super.toFormatedString() + "\n\n";
        res += encodingSettings.toFormatedString();
        return res;
    }
}
