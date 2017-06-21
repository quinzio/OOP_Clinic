package clinic;

import clinic.Doctor;

public class Person {
	private String sSN;
	private String first;
	private String last;
	private Doctor doctor;
	
	
	public Person(String sSN, String first, String last, Doctor doctor) {
		this.sSN = sSN;
		this.first = first;
		this.last = last;
		this.doctor = doctor;
	}

	public String getSSN(){
		return sSN;
	}

	public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

}
