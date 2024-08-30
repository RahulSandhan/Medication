package Models;

import java.util.ArrayList;
import java.util.List;

public class Medication {
    private List<MedicationClass> medicationsClasses = new ArrayList<>();

    public List<MedicationClass> getMedicationsClasses() {
        return medicationsClasses;
    }
}
