/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uploadchecker;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author camon
 */
public abstract class AbstractInfo {
    protected Map<String,String> map;

    public AbstractInfo() {
        map = new HashMap<String,String>();
    }

    public String put(String key, String value) {
        return map.put(key.toLowerCase(),value);
    }

    public String remove(String key) {
        return map.remove(key.toLowerCase());
    }

    public String get(String key) {
        return map.get(key.toLowerCase());
    }

    public String toFormatedString() {
        String res = this.getClass().getSimpleName();
        for (Entry<String,String> entry : map.entrySet())
            res += "\n" + entry.getKey() + " : " + entry.getValue();
        return res;
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }
}
