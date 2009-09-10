/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author camon
 */
public class AppUtils {
    private AppUtils() {};

    public static final String USER_HOME = System.getProperty("user.home");

    public static final String USER_OS = System.getProperty("os.name");

    public static final String FILE_SEPARATOR = java.io.File.separator;

    public static final Runtime APP_RUNTIME = Runtime.getRuntime();

    public static final javax.swing.Icon DEFAULT_HOME_FOLDER_ICON =
            javax.swing.UIManager.getIcon("FileChooser.homeFolderIcon");

    public static final javax.swing.Icon DEFAULT_UP_FOLDER_ICON =
            javax.swing.UIManager.getIcon("FileChooser.upFolderIcon");

    public static final javax.swing.Icon DEFAULT_NEW_FOLDER_ICON =
            javax.swing.UIManager.getIcon("FileChooser.newFolderIcon");

    public static final javax.swing.Icon DEFAULT_LIST_VIEW_ICON =
            javax.swing.UIManager.getIcon("FileChooser.listViewIcon");

    public static final javax.swing.Icon DEFAULT_DETAILS_VIEW_ICON =
            javax.swing.UIManager.getIcon("FileChooser.detailsViewIcon");

    public static final javax.swing.filechooser.FileNameExtensionFilter
            getExtensionFilter(String description, String ... extensions) {
        return new javax.swing.filechooser.FileNameExtensionFilter(
                            description, extensions);
    }

    public static final void useWindows7FileChooserIcons(
            javax.swing.Icon homeFolderIcon, javax.swing.Icon upFolderIcon,
            javax.swing.Icon newFolderIcon, javax.swing.Icon listViewIcon,
            javax.swing.Icon detailsViewIcon,
            javax.swing.JFileChooser fileChooser) {
        if (fileChooser == null) throw new java.lang.NullPointerException();
        javax.swing.UIManager.put("FileChooser.homeFolderIcon", homeFolderIcon);
        javax.swing.UIManager.put("FileChooser.upFolderIcon", upFolderIcon);
        javax.swing.UIManager.put("FileChooser.newFolderIcon", newFolderIcon);
        javax.swing.UIManager.put("FileChooser.listViewIcon", listViewIcon);
        javax.swing.UIManager.put(
                "FileChooser.detailsViewIcon", detailsViewIcon);
        javax.swing.SwingUtilities.updateComponentTreeUI(fileChooser);
    }

    public static final void useDefaultFildeChooserIcons(
            javax.swing.JFileChooser fileChooser) {
        if (fileChooser == null) throw new java.lang.NullPointerException();
        javax.swing.UIManager.put("FileChooser.homeFolderIcon",
                DEFAULT_HOME_FOLDER_ICON);
        javax.swing.UIManager.put("FileChooser.upFolderIcon",
                DEFAULT_UP_FOLDER_ICON);
        javax.swing.UIManager.put("FileChooser.newFolderIcon",
                DEFAULT_NEW_FOLDER_ICON);
        javax.swing.UIManager.put("FileChooser.listViewIcon",
                DEFAULT_LIST_VIEW_ICON);
        javax.swing.UIManager.put("FileChooser.detailsViewIcon",
                DEFAULT_DETAILS_VIEW_ICON);
        javax.swing.SwingUtilities.updateComponentTreeUI(fileChooser);
    }

    public static final java.net.URL getLocation(Class<?> theClass) {
        if (theClass == null) throw new java.lang.NullPointerException();
        java.security.ProtectionDomain pd = theClass.getProtectionDomain();
        if (pd == null) return null;
        java.security.CodeSource cs = pd.getCodeSource();
        if (cs == null) return null;
        return cs.getLocation();
    }
}
