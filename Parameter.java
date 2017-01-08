import java.io.Serializable;

public class Parameter implements Serializable{

	private String label;
	private String stringValue;
	private int intValue;
	private double doubleValue;

	public Parameter(String label, String stringValue, int intValue, double doubleValue) {
		this.label = label;
		this.stringValue = stringValue;
		this.intValue = intValue;
		this.doubleValue = doubleValue;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

}