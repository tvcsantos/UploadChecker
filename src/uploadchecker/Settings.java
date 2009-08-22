/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uploadchecker;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author camon
 */
public class Settings implements Serializable {

    private String announceURL;
    private List<String> nameFilters;
    
    public Settings() {
        announceURL = null;
        nameFilters = new LinkedList<String>();
        nameFilters.add("BluRay.1080p.DTS.x264.dxva-EuReKA".toLowerCase());
    }

    public void setAnnounceURL(String url) {
        announceURL = url;
    }

    public String getAnnounceURL() {
        return announceURL;
    }

    public boolean addNameFilter(String filter) {
        if (filter == null) throw new NullPointerException();
        String lower = filter.toLowerCase();
        if (!nameFilters.contains(lower)) {
            nameFilters.add(lower);
            return true;
        } else return false;
    }

    public boolean removeNameFilter(String filter) {
        if (filter == null) throw new NullPointerException();
        String lower = filter.toLowerCase();
        return nameFilters.remove(lower);
    }

    public List<String> getNameFilters() {
        return nameFilters;
    }
}
