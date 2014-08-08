package br.com.aniche;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SuperComparator {

	private Map<String, Properties> allProps;
	private Properties defaultProps;
	private String[] ignoreKeys;

	public SuperComparator(Map<String, Properties> allProps, Properties defaultProps, String[] ignoreKeys) {
		this.allProps = allProps;
		this.defaultProps = defaultProps;
		this.ignoreKeys = ignoreKeys;
	}
	
	public Set<MissingProperty> compare() {
		Set<MissingProperty> errors = new HashSet<MissingProperty>();
		List<String> files = new ArrayList<String>(allProps.keySet());
		
		for(int i = 0; i < files.size(); i++) {
			for(int j = 0; j < files.size(); j++) {
				if(i==j) continue;
				
				errors.addAll(compareFiles(files.get(i), files.get(j)));
			}
		}
		
		return errors;
	}

	private Set<MissingProperty> compareFiles(String propName1,
			String propName2) {
		
		Set<MissingProperty> errors = new HashSet<MissingProperty>();
		
		Properties prop1 = allProps.get(propName1);
		Properties prop2 = allProps.get(propName2);

		for(Object obj : prop1.keySet()) {
			String key = (String) obj;
			
			if(shouldBeIgnored(key)) continue;
			
			if(!prop2.containsKey(key) && !defaultProps.containsKey(key)) {
				errors.add(new MissingProperty(propName1, propName2, key));
			}
		}
		
		return errors;
	}

	private boolean shouldBeIgnored(String key) {
		for(String ignoreKey : ignoreKeys) {
			if(key.startsWith(ignoreKey)) return true;
		}
		return false;
	}
}
