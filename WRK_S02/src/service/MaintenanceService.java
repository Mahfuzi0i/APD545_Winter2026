/**********************************************
 Workshop 2
 Course:APD545 - Semester 5
 Last Name:Mahfuz
 First Name:Abdullah Al
 ID:180377236
 Section:NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature - Abdullah
 Date:03 February 2026
 **********************************************/

package service;

import model.Vehicle;
import model.MaintenanceRecord;
import java.util.*;

public class MaintenanceService {
    private Map<Vehicle, List<MaintenanceRecord>> dataStore = new HashMap<>();

    public void addRecord(Vehicle v, MaintenanceRecord record) {
        dataStore.computeIfAbsent(v, k -> new ArrayList<>()).add(record);
    }

    public List<MaintenanceRecord> getRecords(Vehicle v) {
        return dataStore.getOrDefault(v, new ArrayList<>());
    }

    public Map<Vehicle, List<MaintenanceRecord>> getAllRecords() {
        return dataStore;
    }
}
