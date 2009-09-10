package utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author camon
 */
public class HierarchyProperties {
    protected HierarchyProperties parent;
    protected Object key;
    protected Object value;
    protected Map<Object, List<HierarchyProperties>> contents;

    public HierarchyProperties(Object key, Object value) {
        this.parent = null;
        this.key = key;
        this.value = value;
        this.contents = new HashMap<Object, List<HierarchyProperties>>();
    }

    public void add(HierarchyProperties n) {
        n.setParent(this);
        List<HierarchyProperties> list = contents.get(n.key);
        if (list == null) list = new LinkedList<HierarchyProperties>();
        list.add(n);
        contents.put(n.key, list);
    }

    public List<HierarchyProperties> get(String s) {
        return contents.get(s);
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public void setParent(HierarchyProperties parent) {
        this.parent = parent;
    }

    public HierarchyProperties getParent() {
        return parent;
    }

    public String toFormatedString() {
        return toFormatedString(0);
    }

    private String toFormatedString(int level) {
        String res = "";
        for (int i = 0 ; i < level ; i++) res += "  ";
        String result = res + key + ": " + value ;
        if (contents.isEmpty()) return result;
        for (List<HierarchyProperties> list : contents.values()) {
            for (HierarchyProperties n : list) {
                result += "\n" + n.toFormatedString(level + 1);
            }
        }
        return result;
    }
}
