/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uploadchecker;

import java.io.Serializable;

/**
 *
 * @author camon
 */
public interface Cache<Key, Value> extends Serializable {
    public Value get(Key key);
    public Value put(Key key, Value value);
}
