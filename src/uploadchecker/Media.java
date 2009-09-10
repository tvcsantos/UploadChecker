package uploadchecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author camon
 */
public class Media {

    public static enum Type {

        Movie1080p, Movie720p,
        Animation1080p, Animation720p
    };

    public static enum Result {

        NOTGOOD, DISCOURAGED, OK
    };
    private File file;
    private General generalInfo;
    private Video videoInfo;
    private List<Audio> audioInfoList;
    private List<Text> textInfoList;
    private String formatedInfo;
    private List<String> originalLanguages;

    public static final int[][] MAX_1080p_HEIGHT =
        { {720, 6}, {864, 5}, {1088, 4} };
    public static final int[][] MAX_720p_HEIGHT =
        { {540, 12}, {588, 11}, {648, 10}, {720, 9} };

    public Media(String file) throws IOException {
        this.file = new File(file);
        generalInfo = new General();
        videoInfo = new Video();
        audioInfoList = new LinkedList<Audio>();
        textInfoList = new LinkedList<Text>();
        formatedInfo = null;
        originalLanguages = null;
        load();
    }

    public File getFile() {
        return file;
    }

    public String toFormatedString() {
        String res = this.getClass().getSimpleName() + " : " + file.getName();
        res += "\n" + generalInfo.toFormatedString();
        res += "\n\n" + videoInfo.toFormatedString();
        for (Audio aud : audioInfoList) {
            res += "\n\n" + aud.toFormatedString();
        }
        for (Text text : textInfoList) {
            res += "\n\n" + text.toFormatedString();
        }
        return res;
    }

    public String getFormatedInfo() {
        return formatedInfo;
    }

    public void setOriginalLanguages(List<String> originalLanguages) {
        this.originalLanguages = originalLanguages;
    }
    
    public Report check(Type type) {
        // Video Check
        String swidth = videoInfo.get("width", false);
        String sheight = videoInfo.get("height", false);
        int[] check = new int[2];
        Report rep = new Report(file);
        rep.add("cabac", "Not Found");
        rep.add("ref", "Not Found");
        rep.add("vbv_maxrate", "Not Found. Assuming valid");
        rep.add("vbv_bufsize", "Not Found. Assuming valid");
        rep.add("analyse", "Not Found");
        rep.add("rc", "Not Found");
        rep.add("me_range", "Not Found");
        rep.add("trellis/deadzone", "Not Found");
        rep.add("bframes", "Not Found");
        rep.add("deblock", "Not Found");
        rep.add("me", "Not Found");
        rep.add("subme", "Not Found");
        rep.add("subtitle", "Not Found");
        rep.add("audio", "Not Found");
        rep.add("width/height","Not Found");
        rep.add("frame rate","Not Found");
        rep.setMediaInfoOutput(formatedInfo);

        int cabac = -Integer.MAX_VALUE;
        int ref = -Integer.MAX_VALUE;
        int vbv_maxrate = -Integer.MAX_VALUE;
        int vbv_bufsize = -Integer.MAX_VALUE;
        String analyse = null;
        String rc = null;
        int me_range = -Integer.MAX_VALUE;
        int trellis = -Integer.MAX_VALUE;
        double deadzone = -Double.MAX_VALUE;
        int bframes = -Integer.MAX_VALUE;
        int deblocka = -Integer.MAX_VALUE;
        int deblockb = -Integer.MAX_VALUE;
        String me = null;
        int subme = -Integer.MAX_VALUE;
        double frameRate = -Double.MAX_VALUE;

        int cref = 3;
        int[] cdeblocka = {-3, -1};
        int[] cdeblockb = {-3, -1};
        int width = 1920;
        int height = 1088;

        switch (type) {
            case Movie1080p:
                break;
            case Movie720p:
                cref = 5;
                width = 1280;
                height = 720;
                break;
            case Animation1080p:
                cdeblocka = new int[]{0, 2};
                cdeblockb = new int[]{0, 2};
                break;
            case Animation720p:
                cref = 5;
                width = 1280;
                height = 720;
                cdeblocka = new int[]{0, 2};
                cdeblockb = new int[]{0, 2};
                break;
        }

        if (swidth == null || sheight == null) {
            return rep;
        }

        swidth = swidth.replace("pixels", "").replace(" ", "");
        sheight = sheight.replace("pixels", "").replace(" ", "");

        int iwidth = Integer.parseInt(swidth);
        int iheight = Integer.parseInt(sheight);

        String value = null;
        value = videoInfo.get("cabac", true);
        if (value == null) {
            check[0]++;
        } else {
            cabac = Integer.parseInt(value);
        }

        value = videoInfo.get("ref", true);
        if (value == null) {
            check[0]++;
        } else {
            ref = Integer.parseInt(value);
        }

        value = videoInfo.get("vbv_maxrate", true);
        if (value != null) {
            vbv_maxrate = Integer.parseInt(value);
        }

        value = videoInfo.get("vbv_bufsize", true);
        if (value != null) {
            vbv_bufsize = Integer.parseInt(value);
        }

        value = videoInfo.get("analyse", true);
        if (value == null) {
            check[0]++;
        } else {
            analyse = value;
        }

        value = videoInfo.get("rc", true);
        if (value == null) {
            check[0]++;
        } else {
            rc = value;
        }

        value = videoInfo.get("me_range", true);
        if (value == null) {
            check[0]++;
        } else {
            me_range = Integer.parseInt(value);
        }

        /*boolean trellis = false;
        boolean deadzone = false;*/
        value = videoInfo.get("trellis", true);
        if (value == null) {
            //trellis = false;
            // TODO: fix
        } else {
            trellis = Integer.parseInt(value);
        }

        value = videoInfo.get("deadzone", true);
        if (value == null) {
            //deadzone = false;
        } else {
            deadzone = Double.parseDouble(
                    value.replace(",", "."));
        }

        value = videoInfo.get("bframes", true);
        if (value == null) {
            check[0]++;
        } else {
            bframes = Integer.parseInt(value);
        }

        value = videoInfo.get("deblock", true);
        if (value == null) {
            check[0]++;
        } else {
            String[] arr = value.split(":");
            if (arr.length != 3) {
                check[0]++;
            } else {
                deblocka = Integer.parseInt(arr[1]);
                deblockb = Integer.parseInt(arr[2]);
            }
        }

        value = videoInfo.get("me", true);
        if (value == null) {
            check[0]++;
        } else {
            me = value;
        }

        value = videoInfo.get("subme", true);
        if (value == null) {
            check[0]++;
        } else {
            subme = Integer.parseInt(value);
        }

        value = videoInfo.get("frame rate",false);
        if (value == null) {
            check[0]++;
        } else {
            frameRate =
                Double.parseDouble(value.replace("fps","").replace(" ",""));
        }

        // calc max ref
        int maxRef = -1;
        switch(type) {
            case Movie1080p:
            case Animation1080p:
                for (int[] h : MAX_1080p_HEIGHT)
                    if (iheight <= h[0]) {
                        maxRef = h[1];
                        break;
                    }
                break;
            case Movie720p:
            case Animation720p:
                for (int[] h : MAX_720p_HEIGHT)
                    if (iheight <= h[0]) {
                        maxRef = h[1];
                        break;
                    }
                break;
        }

        if (iwidth != width) {
            check[0]++;
            rep.add("width/height","Failed. Expecting width = " + width +
                    " found width = " + iwidth);
        } else {
            rep.add("width/height","Passed");
        }

        if (cabac == -Integer.MAX_VALUE) ;
        else if (cabac != 1) {
            check[0]++;
            rep.add("cabac", "Failed. Expecting " +
                    "cabac = 1 found cabac = " + cabac);
        } else {
            rep.add("cabac", "Passed");
        }
        if (ref == -Integer.MAX_VALUE) ;
        else if (maxRef == -1) {
            check[0]++;
            rep.add("ref", "Failed. Expecting height <= " +
                    height + " found height = " + iheight);
        } else if (ref < cref || ref > maxRef) {
            check[0]++;
            rep.add("ref", "Failed. Expecting " +
                    cref + " <= ref <= " + maxRef +
                    " found ref = " + ref);
        } else {
            rep.add("ref", "Passed");
        }
        if (vbv_maxrate == -Integer.MAX_VALUE) ;
        else if (vbv_maxrate > 50000) {
            check[0]++;
            rep.add("vbv_maxrate", "Failed. Expecting " +
                    "vbv_maxrate <= 50000 found vbv_maxrate = " +
                    vbv_maxrate);
        } else {
            rep.add("vbv_maxrate", "Passed");
        }
        if (vbv_bufsize == -Integer.MAX_VALUE) ;
        else if (vbv_bufsize > 50000) {
            check[0]++;
            rep.add("vbv_bufsize", "Failed. Expecting " +
                    "vbv_bufsize <= 50000 found vbv_bufsize = " +
                    vbv_bufsize);
        } else {
            rep.add("vbv_bufsize", "Passed");
        }
        if (analyse == null) ;
        else if (analyse.compareTo("0x3:0x113") != 0) {
            check[0]++;
            rep.add("analyse", "Failed. Expecting " +
                    "analyse = 0x3:0x113 found analyse = " +
                    analyse);
        } else {
            rep.add("analyse", "Passed");
        }
        if (rc == null) ;
        else if (rc.compareTo("crf") != 0 &&
                rc.compareTo("2-pass") != 0 &&
                rc.compareTo("2pass") != 0) {
            check[0]++;
            rep.add("rc", "Failed. Expecting " +
                    "rc = crf/2-pass/2pass found rc = " + rc);
        } else {
            rep.add("rc", "Passed");
        }
        if (me_range == -Integer.MAX_VALUE) ;
        else if (me_range < 16) {
            check[0]++;
            rep.add("me_range", "Failed. Expecting " +
                    "me_range >= 16 found me_range = " + me_range);
        } else {
            rep.add("me_range", "Passed");
        }
        if (!(trellis == 1 || trellis == 2 || deadzone < 10) &&
                trellis != -Integer.MAX_VALUE &&
                deadzone != -Double.MAX_VALUE) {
            check[0]++;
            rep.add("treliss/deadzone", "Failed");
        } else if (trellis == 1 || trellis == 2 || deadzone < 10) {
            rep.add("trellis/deadzone", "Passed");
        }
        if (bframes == -Integer.MAX_VALUE) ;
        else if (bframes < 3) {
            check[0]++;
            rep.add("bframes", "Failed. Expecting " +
                    "bframes >= 3 found bframes = " + bframes);
        } else {
            rep.add("bframes", "Passed");
        }
        if (deblocka == -Integer.MAX_VALUE ||
                deblockb == -Integer.MAX_VALUE) ;
        else if (deblocka < cdeblocka[0] ||
                deblockb < cdeblockb[0] ||
                deblocka > cdeblocka[1] ||
                deblockb > cdeblockb[1]) {
            check[0]++;
            rep.add("deblock", "Failed. Expecting " +
                    "deblock between (" +
                    cdeblocka[0] + "," +
                    cdeblockb[0] + ") and (" +
                    cdeblocka[1] + "," +
                    cdeblockb[1] + ") " +
                    "found deblock = " + "(" + deblocka +
                    "," + deblockb + ")");
        } else {
            rep.add("deblock", "Passed");
        }
        if (me == null) ;
        else if (me.compareTo("dia") == 0) {
            check[0]++;
            rep.add("me", "Failed. Expecting me != dia/hex found " +
                    "me = " + me);
        } else if (me.compareTo("hex") == 0) {
            check[1]++;
            rep.add("me", "Failed. Expecting me != dia/hex found " +
                    "me = " + me);
        } else {
            rep.add("me", "Passed");
        }
        if (subme == -Integer.MAX_VALUE) ;
        else if (subme >= 1 && subme <= 5) {
            check[0]++;
            rep.add("subme", "Failed. Expecting subme > 6 found " +
                    "subme = " + subme);
        } else if (subme == 6) {
            check[1]++;
            rep.add("subme", "Failed. Expecting subme > 6 found " +
                    "subme = " + subme);
        } else {
            rep.add("subme", "Passed");
        }
        if (frameRate == -Double.MAX_VALUE) ;
        else if (frameRate != 23.976 &&
                    frameRate != 24 &&
                        frameRate != 25 &&
                            frameRate != 29.970) {
            check[0]++;
            rep.add("frame rate","Failed. " +
                    "Expecting frame rate = 23.976/24/25/29.970 " +
                    "found frame rate = " + frameRate);
        } else {
            rep.add("frame rate", "Passed");
        }

        // Audio Check
        //boolean audio = false;
        if (audioInfoList.size() > 2) {
            check[0]++;
            rep.add("audio", "Failed. More than 2 audio tracks found");
        } else if (audioInfoList.isEmpty()) {
            check[0]++;
            rep.add("audio", "Failed. No audio tracks found");
        } else { // >= 1 <= 2
            boolean audio = true;
            for (Audio a : audioInfoList) {
                String format = a.get("format");
                if (format == null ||
                        (format.compareToIgnoreCase("DTS") != 0
                        && format.compareToIgnoreCase("AC-3") != 0)) {
                    audio = false;
                    break;
                }
            }
            if (!audio) {
                check[0]++;
                rep.add("audio", "Failed. No DTS or AC-3 audio tracks found");
            } else {
                // CHECK ORIGINAL AUDIO
                if (originalLanguages == null ||
                        originalLanguages.isEmpty())
                {
                    // CAN'T FIND ORIGINAL AUDIO
                    rep.add("audio", "Warning. Can't determine original audio");
                } else {
                    int count = 0;
                    int countNoLang = 0;
                    for (Audio a : audioInfoList) {
                        String lang = a.get("language");
                        if (lang == null) {
                            // NO LANG FOUND
                            countNoLang++;
                        } else {
                            boolean isOriginal = false;
                            for (String l : originalLanguages) {
                                if (l.compareToIgnoreCase(lang) == 0) {
                                    isOriginal = true;
                                    break;
                                }
                            }
                            if (isOriginal) {
                                count++;
                            }
                        }
                    }
                    if (countNoLang > 0) {
                        rep.add("audio", "Warning. Can't find tracks language");
                    }
                    else if (count == audioInfoList.size())
                    {
                        // ALL TRACKS ORIGINAL
                        rep.add("audio", "Passed");
                    } else
                    {
                        // (audioInfoList.size() - count) WITH NO ORIGINAL AUDIO
                        int failed = audioInfoList.size() - count;
                        rep.add("audio", "Failed. " + failed +
                                (failed > 1 ? " tracks" : " track") +
                                " with no original audio");
                    }
                }
            }
        }
        /*for (Audio a : audioInfoList) {
            String format = a.get("format");
            if (format == null) {
                continue;
            }
            if (format.compareToIgnoreCase("DTS") == 0
                    || format.compareToIgnoreCase("AC-3") == 0) {
                audio = true;
                break;
            }
        }
        if (audioInfoList.isEmpty()) check[0]++;
        else if (!audio && !audioInfoList.isEmpty()) {
            check[0]++;
            rep.add("audio", "Failed. No DTS or AC-3 audio tracks found");
        } else if (audio) {
            rep.add("audio", "Passed");
        }*/

        // Text Check
        boolean text = false;
        for (Text t : textInfoList) {
            Object lang = t.get("language");
            if (lang == null) {
                continue;
            }
            String slang = (String) lang;
            if (slang.compareToIgnoreCase("english") == 0) {
                text = true;
                break;
            }
        }
        if (textInfoList.isEmpty()) check[0]++;
        else if (!text && !textInfoList.isEmpty()) {
            check[0]++;
            rep.add("subtitle", "Failed. No english subtitle found");
        } else if (text) {
            rep.add("subtitle", "Passed");
        }

        if (check[0] <= 0) {
            if (check[1] > 0 && check[1] <= 1) {
                rep.setRes(Result.DISCOURAGED);
            } else if (check[1] <= 0) {
                rep.setRes(Result.OK);
            } else {
                rep.setRes(Result.NOTGOOD);
            }
        } else {
            rep.setRes(Result.NOTGOOD);
        }

        return rep;
    }

    private void load() throws IOException {
        String path = (new File(
                UploadCheckerApp.APP_LOCATION.getFile()).getParent() +
                UploadCheckerApp.FILE_SEPARATOR + "prog" +
                UploadCheckerApp.FILE_SEPARATOR + "MediaInfo.exe")
                    .replace("%20", " ");
        String osl = System.getProperty("os.name").toLowerCase();
        if (!osl.contains("windows")) {
            path = "mediainfo";
        }
        Process proc = UploadCheckerApp.APP_RUNTIME.exec(
                new String[]{path, this.file.getAbsolutePath()});
        InputStream inputstream = proc.getInputStream();
        InputStreamReader inputstreamreader = new InputStreamReader(
                inputstream);
        BufferedReader bufferedreader = new BufferedReader(
                inputstreamreader);

        // read the ls output

        String line;
        formatedInfo = "";
        while ((line = bufferedreader.readLine()) != null) {

            formatedInfo += line + "\n";
            
            if (line.length() <= 0) {
                continue;
            }
            
            String lower = line.toLowerCase();
            boolean general = false, audio = false, video = false, text = false;
            boolean skip = false;
            if (lower.contains("general")) {
                general = true;
            } else if (lower.contains("audio")) {
                audio = true;
            } else if (lower.contains("video")) {
                video = true;
            } else if (lower.contains("text")) {
                text = true;
            } else {
                skip = true;
            }

            if (!skip) {
                Audio audioInfo = new Audio();
                Text textInfo = new Text();
                while ((line = bufferedreader.readLine()) != null) {

                    formatedInfo += line + "\n";
                    
                    if (line.length() <= 0) {
                        break;
                    }
                    int index = line.indexOf(":");
                    if (index < 0) {
                        continue;
                    }
                    String left = line.substring(0, index).trim();
                    String right =
                            line.substring(index + 1).trim();
                    if (general) {
                        generalInfo.put(left, right);
                    } else if (video) {
                        int encIndex = right.indexOf(" / ");
                        videoInfo.put(left, right);
                        if (encIndex >= 0) {
                            String[] contents = right.split(" / ");
                            for (String cont : contents) {
                                int indexEq = cont.indexOf("=");
                                if (indexEq < 0) {
                                    continue;
                                }
                                String leftEnc =
                                        cont.substring(0, indexEq).trim();
                                String rightEnc =
                                        cont.substring(indexEq + 1).trim();
                                videoInfo.putEncoding(leftEnc, rightEnc);
                            }
                        }
                    } else if (audio) {
                        audioInfo.put(left, right);
                    } else if (text) {
                        textInfo.put(left, right);
                    }
                }
                if (audio && !audioInfo.isEmpty()) {
                    audioInfoList.add(audioInfo);
                } else if (text && !textInfo.isEmpty()) {
                    textInfoList.add(textInfo);
                }
            }
        }

        // check for ls failure

        try {
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
