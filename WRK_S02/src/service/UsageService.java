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
import model.UsageLog;
import java.util.*;

public class UsageService {
    private Map<Vehicle, List<UsageLog>> dataStore = new HashMap<>();

    public void addLog(Vehicle v, UsageLog log) {
        dataStore.computeIfAbsent(v, k -> new ArrayList<>()).add(log);
    }

    public List<UsageLog> getLogs(Vehicle v) {
        return dataStore.getOrDefault(v, new ArrayList<>());
    }

    public Map<Vehicle, List<UsageLog>> getAllLogs() {
        return dataStore;
    }
}
