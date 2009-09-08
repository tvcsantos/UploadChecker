/*
 * UploadCheckerApp.java
 */

package uploadchecker;

import java.io.File;
import java.net.URL;
import org.gudy.azureus2.core3.security.SESecurityManager;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class UploadCheckerApp extends SingleFrameApplication {

    public static final String USER_HOME = System.getProperty("user.home");

    public static final String USER_OS = System.getProperty("os.name");

    public static final String FILE_SEPARATOR = File.separator;
    
    public static final String SETTINGS_DIR = ".uploadchecker";

    public static final Runtime APP_RUNTIME = Runtime.getRuntime();

    public static final URL APP_LOCATION = UploadCheckerApp.class.
            getProtectionDomain().getCodeSource().getLocation();

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        clear();
        show(new UploadCheckerView(this));
    }

    private void clear() {
        File dir = new File(USER_HOME, SETTINGS_DIR);
        delete(dir);
    }

    private void delete(File f) {
        if (f == null) return ;
        String name = f.getName();
        if (name.endsWith(".db") || name.endsWith(".cache")) return;
        else if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (File ff : files) delete(ff);
        } else f.delete();
    }

    @Override protected void end() {
        SESecurityManager.exitVM(0);
    }
    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of UploadCheckerApp
     */
    public static UploadCheckerApp getApplication() {
        return Application.getInstance(UploadCheckerApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(UploadCheckerApp.class, args);
    }
}
