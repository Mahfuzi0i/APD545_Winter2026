/**********************************************
 Workshop 4&5
 Course: APD545 - Semester Winter 2026
 Last Name: Mahfuz
 First Name: Abdullah Al
 ID:180377236
 Section:NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Abdullah Al Mahfuz
 Date: 24 Feb 2026
 **********************************************/
package nexus.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FixedPriceProject extends Project {
    private final DoubleProperty maxBudget;
    private final DoubleProperty clientMarkup;

    public FixedPriceProject(
            String id,
            String title,
            Status status,
            double maxBudget,
            double clientMarkup
    ) {
        super(id, title, status);
        this.maxBudget = new SimpleDoubleProperty(maxBudget);
        this.clientMarkup = new SimpleDoubleProperty(clientMarkup);
    }

    public double getMaxBudget() {
        return maxBudget.get();
    }

    public void setMaxBudget(double maxBudget) {
        this.maxBudget.set(maxBudget);
    }

    public DoubleProperty maxBudgetProperty() {
        return maxBudget;
    }

    public double getClientMarkup() {
        return clientMarkup.get();
    }

    public void setClientMarkup(double clientMarkup) {
        this.clientMarkup.set(clientMarkup);
    }

    public DoubleProperty clientMarkupProperty() {
        return clientMarkup;
    }

    @Override
    public double getProfitMargin() {
        if (getMaxBudget() <= 0.0) {
            return 0.0;
        }
        return ((getMaxBudget() - getTotalCost()) / getMaxBudget()) * 100.0;
    }
}
