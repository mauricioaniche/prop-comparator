package br.com.aniche;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SuperComparatorTest {

	private Map<String, Properties> allProps;
	@Before
	public void setUp() {
		allProps = new HashMap<String, Properties>();
	}
	
	@Test
	public void should_compare_two_identical_properties() {

		allProps.put("file1.properties", newProps("k1", "k2", "k3"));
		allProps.put("file2.properties", newProps("k1", "k2", "k3"));
		
		Set<MissingProperty> errors = new SuperComparator(allProps).compare();
		
		Assert.assertTrue(errors.isEmpty());
	}
	
	@Test
	public void should_complain_if_a_property_is_missing_on_second_file() {
		
		allProps.put("file1.properties", newProps("k1", "k2", "k3"));
		allProps.put("file2.properties", newProps("k1", "k2"));
		
		Set<MissingProperty> errors = new SuperComparator(allProps).compare();
		
		Assert.assertFalse(errors.isEmpty());
		Assert.assertTrue(errors.contains(new MissingProperty("file2.properties", "k3")));
	}

	@Test
	public void should_complain_if_a_property_is_missing_on_first_file() {
		
		allProps.put("file1.properties", newProps("k1", "k2"));
		allProps.put("file2.properties", newProps("k1", "k2", "k3"));
		
		Set<MissingProperty> errors = new SuperComparator(allProps).compare();
		
		Assert.assertFalse(errors.isEmpty());
		Assert.assertTrue(errors.contains(new MissingProperty("file1.properties", "k3")));
	}

	@Test
	public void should_complain_if_different_properties_are_missing_in_different_files() {
		
		allProps.put("file1.properties", newProps("k1", "k2"));
		allProps.put("file2.properties", newProps("k1", "k2", "k3"));
		allProps.put("file3.properties", newProps("k4", "k2", "k7"));
		
		Set<MissingProperty> errors = new SuperComparator(allProps).compare();
		
		Assert.assertEquals(7, errors.size());
		Assert.assertTrue(errors.contains(new MissingProperty("file1.properties", "k3")));
		Assert.assertTrue(errors.contains(new MissingProperty("file1.properties", "k7")));
		Assert.assertTrue(errors.contains(new MissingProperty("file1.properties", "k4")));
		Assert.assertTrue(errors.contains(new MissingProperty("file2.properties", "k7")));
		Assert.assertTrue(errors.contains(new MissingProperty("file2.properties", "k4")));
		Assert.assertTrue(errors.contains(new MissingProperty("file3.properties", "k3")));
		Assert.assertTrue(errors.contains(new MissingProperty("file3.properties", "k1")));
	}

	private Properties newProps(String... keysAndValues) {
		Properties prop = new Properties();
		for(String value : keysAndValues) {
			prop.put(value, value);
		}
		return prop;
	}
}
