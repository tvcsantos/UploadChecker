package pt.com.santos.uploadchecker;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import pt.com.santos.uploadchecker.Media.Result;

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
        StringBuffer s = new StringBuffer();
        s.append(file.getName()).append("\n");
        for (Entry<String, String> e : descs.entrySet()) {
            s.append(e.getKey()).append(" : ").
                    append(e.getValue()).append("\n");
        }
        s.append("Result: ");
        switch (res) {
            case NOTGOOD:
                s.append("Not Good Enough");
                break;
            case DISCOURAGED:
                s.append("Discouraged");
                break;
            case OK:
                s.append("OK");
                break;
            default:
                s.append("Not Good Enough");
        }
        return s.toString();
    }
}
