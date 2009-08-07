/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uploadchecker;

/**
 *
 * @author camon
 */
public class Video extends AbstractInfo {
    private Encoding encodingSettings;

    public Video() {
        encodingSettings = new Encoding();
    }

    public String putEnc(String key, String value) {
        return encodingSettings.put(key.toLowerCase(), value);
    }

    public String remEnc(String key) {
        return encodingSettings.remove(key.toLowerCase());
    }

    public String getEnc(String key) {
        return encodingSettings.get(key.toLowerCase());
    }

    public String get(String key, boolean enc) {
        return enc ? encodingSettings.get(key.toLowerCase())
                : get(key.toLowerCase());
    }

    @Override
    public String toFormatedString() {
        String res = super.toFormatedString() + "\n\n";
        res += encodingSettings.toFormatedString();
        return res;
    }
}
