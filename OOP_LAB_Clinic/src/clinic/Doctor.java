package clinic;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Doctor extends Person {
	private String specialization;
	private int id;
	private Map<String, Person> patients = new TreeMap<>();  

	public Doctor(String sSN, String first, String last, Doctor doctor,
			String specialization, int id) {
		super(sSN, first, last, doctor);
		this.specialization = specialization;
		this.id = id;
	}

	public int getId(){
		return id;
	}
	
	public String getSpecialization(){
		return specialization;
	}
	
	public Collection<Person> getPatients() {
		return patients.values();
	}
	
	public void addPatient(Person p) {
		patients.put(p.getSSN(), p);
	}

}
