package au.com.jcloud.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import au.com.jcloud.util.Constants;

/**
 * Created by david.vittor on 7/08/16.
 */
public class PropertyReaderService extends BaseService {

	public Properties loadProperties(String filename) throws IOException {
		return loadProperties(filename, true);
	}

	public Properties loadProperties(String filename, boolean useOverride) throws IOException {
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream in = classLoader.getResourceAsStream(filename);
		if (in != null) {
			properties.load(in);
		} else {
			File f = new File(filename);
			if (f.exists()) {
				properties.load(new FileReader(f));
			} else {
				f = new File(Constants.PATH_RESOURCES_TEST + filename);
				if (f.exists()) {
					properties.load(new FileReader(f));
				} else {
					f = new File(Constants.PATH_RESOURCES_MAIN + filename);
					if (f.exists()) {
						properties.load(new FileReader(f));
					}
				}
			}
		}

		if (useOverride) {
			// Look for any override properties
			String overrideParam = "override";
			String os = System.getProperty("os.name");
			if (os.toLowerCase().contains("win")) {
				overrideParam = "win." + overrideParam;
			}
			if (properties.containsKey(overrideParam)) {
				String value = properties.getProperty(overrideParam);
				Properties overrideProperties = loadProperties(value, false);
				properties.putAll(overrideProperties);
			}
		}
		return properties;
	}
}
