package br.com.aniche;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Runner {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		if(args.length<2) {
			System.out.println(
					"usage: java -jar propcomparator.java " + 
					"files=f1.properties,f2.properties " + 
					"default=f1.properties " + 
					"ignore=key1,key2"
			);
			System.exit(1);
		}
		
		Map<String, Properties> allProps = loadAllProperties(args[0]);
		Properties defaultProp = loadDefaultProperties(args[1]);
		String[] ignoreKeys = loadIgnoreKeys(args[2]);
		
		Set<MissingProperty> errors = new SuperComparator(allProps, defaultProp, ignoreKeys).compare();
		if(errors.isEmpty()) System.exit(0);
		printErrors(errors);
		
	}

	private static void printErrors(Set<MissingProperty> errors) {
		System.out.println("Some differences were found:");
		for(MissingProperty error : errors) {
			System.out.println("- Missing " + error.getProperty() + " in " + error.getMissingFile() + " (exists in " + error.getOriginalFile() + ")");
		}
		System.exit(1);
	}

	private static String[] loadIgnoreKeys(String key) {
		String keys = key.replace("ignore=", "");
		if(keys.isEmpty()) return new String[]{};
		
		return keys.split(",");
	}

	private static Properties loadDefaultProperties(String args) throws FileNotFoundException, IOException {
		String file = args.replace("default=", "");
		if(file.isEmpty()) return new Properties();
		
		return readFile(file);
	}

	private static Map<String, Properties> loadAllProperties(String file)
			throws IOException, FileNotFoundException {
		
		String[] args = file.replace("files=", "").split(",");
		Map<String, Properties> allProps = new HashMap<String, Properties>();
		for(int i = 0; i < args.length; i++) {
			Properties prop = readFile(args[i]);
			allProps.put(args[i], prop);
		}
		return allProps;
	}

	private static Properties readFile(String file)
			throws IOException, FileNotFoundException {
		Properties prop = new Properties();
		prop.load(new FileInputStream(file));
		return prop;
	}
}
