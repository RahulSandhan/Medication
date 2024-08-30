import java.io.IOException;
import java.sql.*;
import java.util.*;
import Models.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MedicationData {
    public static void main(String[] args) throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/data";
        String user = "root";
        String password = "password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT m.name AS medication_name, mc.class_name, ad.name AS drug_name, ad.dose AS drug_dose, ad.strength AS drug_strength "
                    + "FROM medications m "
                    + "JOIN medication_classes mc ON m.id = mc.medication_id "
                    + "JOIN associated_drugs ad ON mc.id = ad.medication_class_id "
                    + "ORDER BY m.id, mc.class_name, ad.name;";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Medication medication = new Medication();
            MedicationClass medicationClass = new MedicationClass();
            medication.getMedicationsClasses().add(medicationClass);

            Map<String, Map<String, List<AssociatedDrug>>> classMap = new HashMap<>();
            classMap.put("className", new LinkedHashMap<>());
            classMap.put("className2", new LinkedHashMap<>());

            while (rs.next()) {
                String className = rs.getString("class_name");
                String drugName = rs.getString("drug_name");
                String drugDose = rs.getString("drug_dose");
                String drugStrength = rs.getString("drug_strength");

                Map<String, List<AssociatedDrug>> drugMap = classMap.get(className);
                String drugKey = drugName.equals("asprin") ? "associatedDrug" : "associatedDrug#2";

                drugMap.computeIfAbsent(drugKey, k -> new ArrayList<>())
                        .add(new AssociatedDrug(drugName, drugDose, drugStrength));
            }

            for (Map.Entry<String, Map<String, List<AssociatedDrug>>> classEntry : classMap.entrySet()) {
                if (!classEntry.getValue().isEmpty()) {
                    Map<String, List<InnerClass>> innerMap = new LinkedHashMap<>();
                    innerMap.put(classEntry.getKey(), Collections.singletonList(new InnerClass(classEntry.getValue())));
                    medicationClass.getMedicationClasses().add(innerMap);
                }
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String prettyJsonOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(Collections.singletonMap("medications", Collections.singletonList(medication)));
            System.out.println(prettyJsonOutput);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}


