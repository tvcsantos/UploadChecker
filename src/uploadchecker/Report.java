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
    private String mout;

    public Report(File file) {
        this.file = file;
        this.descs = new HashMap<String, String>();
        this.res = Result.NOTGOOD;
        this.mout = null;
    }

    public void add(String prop, String msg) {
        this.descs.put(prop, msg);
    }

    public void setRes(Result res) {
        this.res = res;
    }

    public void setMediaInfoOutput(String mout) {
        this.mout = mout;
    }

    public String getMediaInfoOutput() {
        return mout;
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
        switch (res) {
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
