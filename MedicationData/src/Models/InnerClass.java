package Models;

import java.util.List;
import java.util.Map;

public class InnerClass {
    private Map<String, List<AssociatedDrug>> associatedDrugs;

    public InnerClass(Map<String, List<AssociatedDrug>> associatedDrugs) {
        this.associatedDrugs = associatedDrugs;
    }

    public Map<String, List<AssociatedDrug>> getAssociatedDrugs() {
        return associatedDrugs;
    }
}

