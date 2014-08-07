package br.com.aniche;

public class MissingProperty {

	private String property;
	private String originalFile;
	private String missingFile;

	public MissingProperty(String originalFile, String missingFile, String property) {
		this.originalFile = originalFile;
		this.missingFile = missingFile;
		this.property = property;
	}


	public String getProperty() {
		return property;
	}


	public String getOriginalFile() {
		return originalFile;
	}


	public String getMissingFile() {
		return missingFile;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((missingFile == null) ? 0 : missingFile.hashCode());
		result = prime * result
				+ ((originalFile == null) ? 0 : originalFile.hashCode());
		result = prime * result
				+ ((property == null) ? 0 : property.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MissingProperty other = (MissingProperty) obj;
		if (missingFile == null) {
			if (other.missingFile != null)
				return false;
		} else if (!missingFile.equals(other.missingFile))
			return false;
		if (originalFile == null) {
			if (other.originalFile != null)
				return false;
		} else if (!originalFile.equals(other.originalFile))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "MissingProperty [property=" + property + ", originalFile="
				+ originalFile + ", missingFile=" + missingFile + "]";
	}

	
	
	
}
