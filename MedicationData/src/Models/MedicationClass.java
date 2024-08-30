package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MedicationClass {

    private List<Map<String, List<InnerClass>>> medicationClasses = new ArrayList<>();

    public List<Map<String, List<InnerClass>>> getMedicationClasses() {
        return medicationClasses;
    }
}
