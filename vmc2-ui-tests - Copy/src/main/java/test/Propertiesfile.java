
package test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class Propertiesfile {
	final static Properties prop = new Properties(); // Properties Object.
	private final static String[] FOLDERS = { "screenshots", "reports", "drivers", "avatars" };

	/**
	 * Loads all the properties and returns property based on value.
	 * 
	 * @param propertyvalue
	 * @return
	 * @throws IOException
	 */
	public static String getProperty(String propertyvalue) throws IOException {
		InputStream in = Propertiesfile.class.getClassLoader().getResourceAsStream("config.properties");
		prop.load(in);
		createFolders();
		return prop.getProperty(propertyvalue);
	}

	/**
	 * Returns InputStream object based on given filename.
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	static InputStream getFile(String fileName) throws IOException {
		String file = getProperty(fileName);
		return Propertiesfile.class.getClassLoader().getResourceAsStream(file);
	}

	/**
	 * Returns file path based on given filename.
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	static String getFilePath(String fileName, String folderName) throws IOException {
		String name = Propertiesfile.getProperty(fileName); //.replace("/resources/", "");
		ClassLoader classLoader = Propertiesfile.class.getClassLoader();
		URL resource = classLoader.getResource(name);
		File file = new File(folderName + File.separator + name);
		if (!file.exists()) {
			file.createNewFile();
			FileUtils.copyURLToFile(resource, file);
		}
		return file.getAbsolutePath();
	}

	/**
	 * Creates list of folders given in the list.
	 */
	private static void createFolders() {
		for (String folder : FOLDERS) {
			File f = new File(folder);
			if (!f.exists()) {
				f.mkdirs();
			}
		}
	}
	
	/**
	 * Returns absolute path of the given file name.
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	static String getAbsoultePath(String fileName) throws IOException {
		String filePath = new File(Propertiesfile.getProperty(fileName)).getAbsolutePath();
		return filePath;
	}
}
