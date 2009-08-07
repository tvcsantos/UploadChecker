/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uploadchecker;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import uploadchecker.Media.Result;

/**
 *
 * @author camon
 */
public class Report {

    private File file;
    private Map<String, String> descs;
    private Result res;

    public Report(File file) {
        this.file = file;
        this.descs = new HashMap<String, String>();
        this.res = Result.NOTGOOD;
    }

    public void add(String prop, String msg) {
        this.descs.put(prop, msg);
    }

    public void setRes(Result res) {
        this.res = res;
    }

    public Result getResult() {
        return res;
    }

    public String toFormatedString() {
        String s = "";
        s += file.getName() + "\n";
        for (Entry<String, String> e : descs.entrySet()) {
            s += e.getKey() + " : " + e.getValue() + "\n";
        }
        s += "Result: ";
        switch (this.res) {
            case NOTGOOD:
                s += "Not Good Enough";
                break;
            case DISCOURAGED:
                s += "Discouraged";
                break;
            case OK:
                s += "OK";
                break;
            default:
                s += "Not Good Enough";
        }
        return s;
    }
}
