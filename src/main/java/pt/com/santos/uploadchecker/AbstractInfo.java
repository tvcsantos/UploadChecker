package pt.com.santos.uploadchecker;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractInfo implements Info {
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
        StringBuilder res = new StringBuilder(this.getClass().getSimpleName());
        for (Entry<String,String> entry : map.entrySet())
            res.append("\n").append(entry.getKey()).
                    append(" : ").append(entry.getValue());
        return res.toString();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }
}
