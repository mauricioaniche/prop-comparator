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
		
		Map<String, Properties> allProps = loadProperties(args);
		Set<MissingProperty> errors = new SuperComparator(allProps).compare();

		if(errors.isEmpty()) System.exit(0);
		
		System.out.println("Some differences were found:");
		for(MissingProperty error : errors) {
			System.out.println("- Missing " + error.getProperty() + " in " + error.getFile());
		}
		System.exit(1);
		
	}

	private static Map<String, Properties> loadProperties(String[] args)
			throws IOException, FileNotFoundException {
		Map<String, Properties> allProps = new HashMap<String, Properties>();
		for(int i = 0; i < args.length; i++) {
			Properties prop = new Properties();
			prop.load(new FileInputStream(args[i]));
			
			allProps.put(args[i], prop);
		}
		return allProps;
	}
}
