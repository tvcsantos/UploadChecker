package uploadchecker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author camon
 */
public class Persistent {
	private Persistent() {}	
	
    public static void persist(String file, Serializable obj)
    throws FileNotFoundException, IOException {
    	ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(file));
        oos.writeObject(obj);
        oos.close();
    }
    
    public static <T extends Serializable> T 
    	resurrect(String file, Class<T> c) 
    	throws FileNotFoundException, IOException,
    	ClassNotFoundException {
        System.out.println(new File(file).getAbsolutePath());
    	ObjectInputStream ois = new ObjectInputStream(
    			new FileInputStream(file));
        T ret = c.cast(ois.readObject());
        ois.close();
    	return ret;
    }

    public static <T extends Cache> T loadCache(String file, Class<T> c,
            long limit) throws IOException, ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        T result;
        try {
            File scache = new File(file);
            if (scache.length() < limit ) // IF SIZE < LIMIT
                result = Persistent.resurrect(scache.getAbsolutePath(), c);
            else {
                scache.delete();
                result = c.newInstance();
            }
        } catch (FileNotFoundException ex) {
            result = c.newInstance();
        }
        return result;
    }
          
    public static void main(String[] args) throws Exception {
    	String s = "xpto";
    	persist("abc.ser", s);
    	String b = resurrect("abc.ser", String.class);
    	System.out.println(s.equals(b));
    }
}
