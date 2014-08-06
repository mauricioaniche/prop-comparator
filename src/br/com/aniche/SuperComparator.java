package br.com.aniche;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SuperComparator {

	private Map<String, Properties> allProps;

	public SuperComparator(Map<String, Properties> allProps) {
		this.allProps = allProps;
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

		for(Object key : prop1.keySet()) {
			if(!prop2.contains(key)) errors.add(new MissingProperty(propName2, (String) key));
		}
		
		return errors;
	}
}
