package clinic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Clinic {
	Map<String, Person> patients = new TreeMap<>();
	Map<Integer, Doctor> doctors = new TreeMap<>();

	public void addPatient(String first, String last, String ssn) {
		patients.put(ssn, new Person(ssn, first, last, null));
	}

	public void addDoctor(String first, String last, String ssn, int docID,
			String specialization) {
		doctors.put(docID,
				new Doctor(ssn, first, last, null, specialization, docID));
	}

	public Person getPatient(String ssn) throws NoSuchPatient {
		if (!patients.containsKey(ssn))
			throw new NoSuchPatient();
		return patients.get(ssn);
	}

	public Doctor getDoctor(int docID) throws NoSuchDoctor {
		if (!doctors.containsKey(docID))
			throw new NoSuchDoctor();
		return doctors.get(docID);
	}

	public void assignPatientToDoctor(String ssn, int docID)
			throws NoSuchPatient, NoSuchDoctor {
		getDoctor(docID).addPatient(getPatient(ssn));
		getPatient(ssn).setDoctor(getDoctor(docID));
	}

	/**
	 * returns the collection of doctors that have no patient at all, sorted in
	 * alphabetic order.
	 */
	Collection<Doctor> idleDoctors() {
		Collection<Doctor> dd =	doctors.values().stream()
				.filter(d -> d.getPatients().size() == 0)
				.collect(Collectors.toList());
		return dd;
	}

	/**
	 * returns the collection of doctors that a number of patients larger than
	 * the average.
	 */
	Collection<Doctor> busyDoctors() {
		double average = doctors.values().stream().collect(
				Collectors.averagingDouble(d -> d.getPatients().size()));
		Collection<Doctor> dd = doctors.values().stream()
				.filter(d -> d.getPatients().size() > average)
				.collect(Collectors.toList());
		return dd;
	}

	/**
	 * returns list of strings containing the name of the doctor and the
	 * relative number of patients with the relative number of patients, sorted
	 * by decreasing number.<br>
	 * The string must be formatted as "<i>### : ID SURNAME NAME</i>" where
	 * <i>###</i> represent the number of patients (printed on three
	 * characters).
	 */
	Collection<String> doctorsByNumPatients() {
		return doctors.values().stream()
				.sorted(Comparator.comparing(d -> d.getPatients().size(),
						Comparator.reverseOrder()))
				.map(d -> String.format("%03d %d %s %s", d.getPatients().size(),
						d.getId(), d.getLast(), d.getFirst()))
				.collect(Collectors.toList());
	}

	/**
	 * computes the number of patients per (their doctor's) specialization. The
	 * elements are sorted first by decreasing count and then by alphabetic
	 * specialization.<br>
	 * The strings are structured as "<i>### - SPECIALITY</i>" where <i>###</i>
	 * represent the number of patients (printed on three characters).
	 */
	public Collection<String> countPatientsPerSpecialization() {
		return doctors.values().stream()
				.collect(Collectors.groupingBy(Doctor::getSpecialization,
						Collectors.summingInt(d -> d.getPatients().size())))
				.entrySet().stream()
				.map(e -> String.format("%03d - %s", e.getValue(), e.getKey()))
				.collect(Collectors.toList());
	}

	public void loadData(String path) throws IOException {
		Path p = Paths.get(path);
		List<String> linee = Files.readAllLines(p);
		String typeOfString;
		String first;
		String last;
		String ssn;
		int badge;
		String specialita;
		for (String linea : linee) {
			try (Scanner s = new Scanner(linea)) {
				s.useDelimiter(";");
				typeOfString = s.next();
				if (typeOfString.equals("P")) {
					first = s.next();
					last = s.next();
					ssn = s.next();
					addPatient(first, last, ssn);
				} else if (typeOfString.equals("M")) {
					badge = s.nextInt();
					first = s.next();
					last = s.next();
					ssn = s.next();
					specialita = s.next();
					addDoctor(first, last, ssn, badge, specialita);
				}
			}
			catch (NoSuchElementException  | IllegalStateException e) {
				System.out.println(e.getMessage());
			}

		}
	}
}
