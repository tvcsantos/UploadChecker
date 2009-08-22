/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uploadchecker;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author camon
 */
public class GenericCache<Key, Value> implements Cache<Key, Value> {
    protected Map<Key, Value> cache;

    public GenericCache() {
        cache = new HashMap<Key, Value>();
    }

    public Value get(Key key) {
        synchronized(cache) {
            return cache.get(key);
        }
    }

    public Value put(Key key, Value value) {
        synchronized(cache) {
            return cache.put(key, value);
        }
    }
}
