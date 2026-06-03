/**********************************************
 Workshop 06 & 07
 Course: APD545 - Semester Winter 2026
 Last Name: Mahfuz
 First Name: Abdullah Al
 ID: 180377236
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Abdullah Al Mahfuz
 Date: 11 March 2026
 **********************************************/

package autoloan.controller;

import autoloan.app.MainApp;
import autoloan.model.*;
import autoloan.service.LoanCalculation;
import autoloan.service.LoanService;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Main loan-form controller.
 *
 * New in W06/07:
 *  - LoanService injected (replaces direct LoanRepository access).
 *  - LoanCalculation injected via Guice (FixedRateLoan).
 *  - 4 persistence buttons: Write to DB, Read from DB, Write to File, Read from File.
 *  - In-memory ObservableList still backs the ListView for the current session;
 *    the DB/File buttons augment that list from persistent storage.
 */
public class LoanController {

    // -------------------------------------------------------------------------
    // FXML bindings – Customer
    // -------------------------------------------------------------------------
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField cityField;
    @FXML private ComboBox<String> provinceBox;

    // -------------------------------------------------------------------------
    // FXML bindings – Vehicle
    // -------------------------------------------------------------------------
    @FXML private RadioButton carRadio;
    @FXML private RadioButton truckRadio;
    @FXML private RadioButton vanRadio;
    @FXML private RadioButton newRadio;
    @FXML private RadioButton usedRadio;
    @FXML private TextField priceField;

    // -------------------------------------------------------------------------
    // FXML bindings – Loan
    // -------------------------------------------------------------------------
    @FXML private TextField downField;
    @FXML private Slider durationSlider;
    @FXML private Label paymentLabel;

    @FXML private RadioButton rate099;
    @FXML private RadioButton rate199;
    @FXML private RadioButton rate299;
    @FXML private RadioButton rateOther;
    @FXML private TextField otherRateField;

    @FXML private RadioButton weeklyRadio;
    @FXML private RadioButton biweeklyRadio;
    @FXML private RadioButton monthlyRadio;

    @FXML private ListView<Loan> savedLoans;

    // -------------------------------------------------------------------------
    // Toggle groups
    // -------------------------------------------------------------------------
    private final ToggleGroup typeGroup      = new ToggleGroup();
    private final ToggleGroup ageGroup       = new ToggleGroup();
    private final ToggleGroup rateGroup      = new ToggleGroup();
    private final ToggleGroup frequencyGroup = new ToggleGroup();

    // -------------------------------------------------------------------------
    // In-memory list backing the ListView
    // -------------------------------------------------------------------------
    private final ObservableList<Loan> loanList = FXCollections.observableArrayList();

    // -------------------------------------------------------------------------
    // Injected services
    // -------------------------------------------------------------------------
    private final LoanService loanService;
    private final LoanCalculation loanCalculation;
    private final CustomerController customerController;
    private final VehicleController vehicleController;

    @Inject
    public LoanController(
            LoanService loanService,
            LoanCalculation loanCalculation,
            CustomerController customerController,
            VehicleController vehicleController) {
        this.loanService        = loanService;
        this.loanCalculation    = loanCalculation;
        this.customerController = customerController;
        this.vehicleController  = vehicleController;
    }

    // -------------------------------------------------------------------------
    // Initialization
    // -------------------------------------------------------------------------

    @FXML
    public void initialize() {
        carRadio.setToggleGroup(typeGroup);
        truckRadio.setToggleGroup(typeGroup);
        vanRadio.setToggleGroup(typeGroup);

        newRadio.setToggleGroup(ageGroup);
        usedRadio.setToggleGroup(ageGroup);

        rate099.setToggleGroup(rateGroup);
        rate199.setToggleGroup(rateGroup);
        rate299.setToggleGroup(rateGroup);
        rateOther.setToggleGroup(rateGroup);
        updateOtherRateFieldState();

        weeklyRadio.setToggleGroup(frequencyGroup);
        biweeklyRadio.setToggleGroup(frequencyGroup);
        monthlyRadio.setToggleGroup(frequencyGroup);

        provinceBox.getItems().addAll(
                "Ontario", "Quebec", "Alberta", "British Columbia",
                "Manitoba", "New Brunswick", "Newfoundland and Labrador",
                "Nova Scotia", "Prince Edward Island", "Saskatchewan");

        savedLoans.setItems(loanList);
        Label placeholder = new Label("No saved loans yet.");
        placeholder.setStyle("-fx-text-fill: #222;");
        savedLoans.setPlaceholder(placeholder);
        savedLoans.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Loan loan, boolean empty) {
                super.updateItem(loan, empty);
                if (empty || loan == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(formatLoanSummary(loan));
                    updateSelectionStyle();
                }
            }

            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                updateSelectionStyle();
            }

            private void updateSelectionStyle() {
                if (getItem() == null) {
                    setStyle("");
                    return;
                }
                if (isSelected()) {
                    setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #111;");
                } else {
                    setStyle("-fx-background-color: white; -fx-text-fill: #111;");
                }
            }
        });
        savedLoans.setFixedCellSize(26);
        savedLoans.setPrefHeight(240);
        savedLoans.setMinHeight(240);
        savedLoans.setMaxHeight(240);
        savedLoans.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, selected) -> {
                    if (selected != null) loadSavedLoan(selected);
                });


        durationSlider.valueProperty().addListener((obs, o, n) -> {
            if (validateAllInputs(false)) calculateLoan();
        });

        setupRealtimeValidation();
    }

    // -------------------------------------------------------------------------
    // Calculate / Clear
    // -------------------------------------------------------------------------

    @FXML
    public void calculate() {
        calculateLoan();
    }

    @FXML
    public void calculateLoan() {
        if (!validateAllInputs(true)) {
            paymentLabel.setText("Invalid Input");
            return;
        }
        Loan loan = createLoan();
        double payment = calculatePaymentForLoan(loan);
        loan.setEstimatedPayment(payment);
        paymentLabel.setText(formatCurrency(payment));
    }

    @FXML
    public void clearForm() {
        nameField.clear();
        phoneField.clear();
        cityField.clear();
        provinceBox.setValue(null);
        priceField.clear();
        downField.clear();
        otherRateField.clear();

        typeGroup.selectToggle(null);
        ageGroup.selectToggle(null);
        rateGroup.selectToggle(null);
        frequencyGroup.selectToggle(null);
        updateOtherRateFieldState();

        durationSlider.setValue(36);
        paymentLabel.setText("$0.00");

        savedLoans.getSelectionModel().clearSelection();
        clearStyles();
    }

    // -------------------------------------------------------------------------
    // Persistence – 4 required buttons (W06/07 requirement)
    // -------------------------------------------------------------------------

    /** Button 1 – Saves the current form data to the SQLite database. */
    @FXML
    public void writeToDB() {
        if (!validateAllInputs(true)) return;
        Loan loan = buildLoanWithPayment();
        loanService.saveToDatabase(loan);
        loanList.add(loan);
        showInfo("Loan saved to database successfully.");
    }

    /** Button 2 – Reads all loans from the database and shows them in the ListView. */
    @FXML
    public void readFromDB() {
        List<Loan> loaded = loanService.loadFromDatabase();
        loanList.setAll(loaded);
        showInfo("Loaded " + loaded.size() + " loan(s) from the database.");
    }

    /** Button 3 – Serializes the current form data to a local JSON file. */
    @FXML
    public void writeToFile() {
        if (!validateAllInputs(true)) return;
        Loan loan = buildLoanWithPayment();
        loanService.saveToFile(loan);
        loanList.add(loan);
        showInfo("Loan saved to file successfully.");
    }

    /** Button 4 – Deserializes loans from the local JSON file into the ListView. */
    @FXML
    public void readFromFile() {
        List<Loan> loaded = loanService.loadFromFile();
        loanList.setAll(loaded);
        showInfo("Loaded " + loaded.size() + " loan(s) from file.");
    }

    // -------------------------------------------------------------------------
    // Amortization
    // -------------------------------------------------------------------------

    @FXML
    public void openAmortization() {
        if (!validateAllInputs(true)) {
            new Alert(Alert.AlertType.ERROR, "Please correct the highlighted fields.").show();
            return;
        }
        try {
            Loan loan = buildLoanWithPayment();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/autoloan/view/amortization.fxml"));
            loader.setControllerFactory(MainApp.getInjector()::getInstance);
            Stage stage = new Stage();
            stage.setTitle("Amortization Schedule");
            stage.setScene(new Scene(loader.load(), 1400, 900));

            LoanAmortizationController controller = loader.getController();
            controller.displayAmortization(loan);
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Could not open amortization: " + e.getMessage()).show();
        }
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private Loan buildLoanWithPayment() {
        Loan loan = createLoan();
        loan.setEstimatedPayment(calculatePaymentForLoan(loan));
        paymentLabel.setText(formatCurrency(loan.getEstimatedPayment()));
        return loan;
    }

    private Loan createLoan() {
        Customer customer = new Customer();
        customer.setName(nameField.getText().trim());
        customer.setPhone(phoneField.getText().trim());
        customer.setCity(cityField.getText().trim());
        customer.setProvince(provinceBox.getValue());

        Vehicle vehicle = new Vehicle();
        vehicle.setPrice(Double.parseDouble(priceField.getText().trim()));
        vehicle.setType(VehicleType.fromLabel(getSelectedText(typeGroup)));
        vehicle.setAge(VehicleAge.fromLabel(getSelectedText(ageGroup)));

        Loan loan = new Loan(customer, vehicle);
        loan.setDownPayment(Double.parseDouble(downField.getText().trim()));
        loan.setInterestRate(getSelectedInterestRate());
        loan.setDurationMonths((int) durationSlider.getValue());
        loan.setFrequency(PaymentFrequency.fromLabel(getSelectedText(frequencyGroup)));
        return loan;
    }

    private double calculatePaymentForLoan(Loan loan) {
        double monthly = loanCalculation.calculatePayment(loan, loan.getVehicle());
        return switch (loan.getFrequency()) {
            case WEEKLY    -> monthly * 12.0 / 52.0;
            case BI_WEEKLY -> monthly * 12.0 / 26.0;
            default        -> monthly;
        };
    }

    private void loadSavedLoan(Loan loan) {
        nameField.setText(loan.getCustomer().getName());
        phoneField.setText(loan.getCustomer().getPhone());
        cityField.setText(loan.getCustomer().getCity());
        provinceBox.setValue(loan.getCustomer().getProvince());

        priceField.setText(String.valueOf(loan.getVehicle().getPrice()));
        downField.setText(String.valueOf(loan.getDownPayment()));
        durationSlider.setValue(loan.getDurationMonths());

        if (loan.getVehicle().getType() != null)
            selectToggleByText(typeGroup, loan.getVehicle().getType().getLabel());
        if (loan.getVehicle().getAge() != null)
            selectToggleByText(ageGroup, loan.getVehicle().getAge().getLabel());
        selectRateToggle(loan.getInterestRate());
        if (loan.getFrequency() != null)
            selectToggleByText(frequencyGroup, loan.getFrequency().getLabel());

        if (loan.getEstimatedPayment() <= 0) {
            loan.setEstimatedPayment(calculatePaymentForLoan(loan));
        }
        paymentLabel.setText(formatCurrency(loan.getEstimatedPayment()));
        validateAllInputs(false);
    }

    // -------------------------------------------------------------------------
    // Validation (unchanged from W03)
    // -------------------------------------------------------------------------

    private void setupRealtimeValidation() {
        nameField.textProperty().addListener((o, ov, nv) -> validateName(false));
        nameField.focusedProperty().addListener((o, ov, f) -> { if (!f) validateName(true); });

        phoneField.textProperty().addListener((o, ov, nv) -> validatePhone(false));
        phoneField.focusedProperty().addListener((o, ov, f) -> { if (!f) validatePhone(true); });

        cityField.textProperty().addListener((o, ov, nv) -> validateCity(false));
        cityField.focusedProperty().addListener((o, ov, f) -> { if (!f) validateCity(true); });

        provinceBox.valueProperty().addListener((o, ov, nv) -> validateProvince(false));
        typeGroup.selectedToggleProperty().addListener((o, ov, nv) -> validateVehicleType(false));
        ageGroup.selectedToggleProperty().addListener((o, ov, nv) -> validateVehicleAge(false));

        priceField.textProperty().addListener((o, ov, nv) -> validatePrice(false));
        priceField.focusedProperty().addListener((o, ov, f) -> { if (!f) validatePrice(true); });

        downField.textProperty().addListener((o, ov, nv) -> validateDownPayment(false));
        downField.focusedProperty().addListener((o, ov, f) -> { if (!f) validateDownPayment(true); });

        rateGroup.selectedToggleProperty().addListener((o, ov, nv) -> {
            updateOtherRateFieldState();
            validateRate(false);
        });
        otherRateField.textProperty().addListener((o, ov, nv) -> validateRate(false));
        otherRateField.focusedProperty().addListener((o, ov, f) -> {
            if (f && !rateOther.isSelected()) {
                showWarning("Please select the 'Other' radio button to specify a custom rate.");
                nameField.requestFocus();
            }
        });
        otherRateField.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (!rateOther.isSelected()) {
                showWarning("Please select the 'Other' radio button first.");
                e.consume();
            }
        });

        frequencyGroup.selectedToggleProperty().addListener((o, ov, nv) -> validateFrequency(false));
    }

    private boolean validateAllInputs(boolean showAlerts) {
        return validateName(showAlerts)
            && validatePhone(showAlerts)
            && validateCity(showAlerts)
            && validateProvince(showAlerts)
            && validateVehicleType(showAlerts)
            && validateVehicleAge(showAlerts)
            && validatePrice(showAlerts)
            && validateDownPayment(showAlerts)
            && validateRate(showAlerts)
            && validateFrequency(showAlerts)
            && validateDomainRules(showAlerts);
    }

    private boolean validateDomainRules(boolean showAlerts) {
        try {
            Customer c = new Customer();
            c.setName(nameField.getText().trim());
            c.setPhone(phoneField.getText().trim());
            c.setCity(cityField.getText().trim());
            c.setProvince(provinceBox.getValue());

            Vehicle v = new Vehicle();
            v.setType(VehicleType.fromLabel(getSelectedText(typeGroup)));
            v.setAge(VehicleAge.fromLabel(getSelectedText(ageGroup)));
            v.setPrice(Double.parseDouble(priceField.getText().trim()));

            if (!customerController.validateCustomer(c)) {
                if (showAlerts) showWarning("Customer details are invalid.");
                return false;
            }
            if (!vehicleController.validateVehicle(v)) {
                if (showAlerts) showWarning("Vehicle details are invalid.");
                return false;
            }
            return true;
        } catch (Exception e) {
            if (showAlerts) showWarning("Please correct invalid form values.");
            return false;
        }
    }

    private boolean validateName(boolean show) {
        String v = txt(nameField);
        boolean ok = !v.isEmpty() && v.matches("[A-Za-z ]+");
        markControl(nameField, ok);
        if (show && !ok) showWarning("Customer name is required (letters and spaces only).");
        return ok;
    }

    private boolean validatePhone(boolean show) {
        boolean ok = txt(phoneField).matches("\\d{10}");
        markControl(phoneField, ok);
        if (show && !ok) showWarning("Phone number must be exactly 10 digits.");
        return ok;
    }

    private boolean validateCity(boolean show) {
        String v = txt(cityField);
        boolean ok = !v.isEmpty() && v.matches("[A-Za-z ]+");
        markControl(cityField, ok);
        if (show && !ok) showWarning("City is required (letters and spaces only).");
        return ok;
    }

    private boolean validateProvince(boolean show) {
        boolean ok = provinceBox.getValue() != null && !provinceBox.getValue().isBlank();
        markControl(provinceBox, ok);
        if (show && !ok) showWarning("Please select a province.");
        return ok;
    }

    private boolean validateVehicleType(boolean show) {
        boolean ok = typeGroup.getSelectedToggle() != null;
        markRadios(new RadioButton[]{carRadio, truckRadio, vanRadio}, ok);
        if (show && !ok) showWarning("Please select a vehicle type.");
        return ok;
    }

    private boolean validateVehicleAge(boolean show) {
        boolean ok = ageGroup.getSelectedToggle() != null;
        markRadios(new RadioButton[]{newRadio, usedRadio}, ok);
        if (show && !ok) showWarning("Please select vehicle age (New or Used).");
        return ok;
    }

    private boolean validatePrice(boolean show) {
        boolean ok;
        try { ok = !txt(priceField).isEmpty() && Double.parseDouble(txt(priceField)) > 0; }
        catch (NumberFormatException e) { ok = false; }
        markControl(priceField, ok);
        if (show && !ok) showWarning("Vehicle price must be a valid number greater than 0.");
        return ok;
    }

    private boolean validateDownPayment(boolean show) {
        boolean ok;
        try {
            double down  = Double.parseDouble(txt(downField));
            double price = Double.parseDouble(txt(priceField));
            ok = down >= 0 && down < price;
        } catch (Exception e) { ok = false; }
        markControl(downField, ok);
        if (show && !ok) showWarning("Down payment must be >= 0 and less than the vehicle price.");
        return ok;
    }

    private boolean validateRate(boolean show) {
        boolean ok = rateGroup.getSelectedToggle() != null;
        if (ok && rateOther.isSelected()) {
            try { ok = Double.parseDouble(txt(otherRateField)) > 0; }
            catch (Exception e) { ok = false; }
        }
        markRadios(new RadioButton[]{rate099, rate199, rate299, rateOther}, ok);
        markControl(otherRateField, !rateOther.isSelected() || ok);
        if (show && !ok) showWarning("Please select a valid interest rate.");
        return ok;
    }

    private boolean validateFrequency(boolean show) {
        boolean ok = frequencyGroup.getSelectedToggle() != null;
        markRadios(new RadioButton[]{weeklyRadio, biweeklyRadio, monthlyRadio}, ok);
        if (show && !ok) showWarning("Please select a payment frequency.");
        return ok;
    }

    // -------------------------------------------------------------------------
    // UI helpers
    // -------------------------------------------------------------------------

    private void markControl(Control c, boolean valid) {
        c.setStyle(valid ? "" : "-fx-border-color: red; -fx-border-width: 2;");
    }

    private void markRadios(RadioButton[] radios, boolean valid) {
        String style = valid ? "" : "-fx-text-fill: red;";
        for (RadioButton r : radios) r.setStyle(style);
    }

    private void clearStyles() {
        for (Control c : new Control[]{nameField, phoneField, cityField, provinceBox,
                priceField, downField, otherRateField}) markControl(c, true);
        markRadios(new RadioButton[]{carRadio, truckRadio, vanRadio}, true);
        markRadios(new RadioButton[]{newRadio, usedRadio}, true);
        markRadios(new RadioButton[]{rate099, rate199, rate299, rateOther}, true);
        markRadios(new RadioButton[]{weeklyRadio, biweeklyRadio, monthlyRadio}, true);
    }

    private void updateOtherRateFieldState() {
        boolean other = rateOther.isSelected();
        otherRateField.setEditable(other);
        if (!other) otherRateField.clear();
    }

    private void showWarning(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).show();
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).show();
    }

    private String txt(TextField f) {
        return f.getText() == null ? "" : f.getText().trim();
    }

    private String getSelectedText(ToggleGroup g) {
        if (g.getSelectedToggle() == null) return "";
        return ((RadioButton) g.getSelectedToggle()).getText();
    }

    private double getSelectedInterestRate() {
        if (rate099.isSelected())   return 0.99;
        if (rate199.isSelected())   return 1.99;
        if (rate299.isSelected())   return 2.99;
        if (rateOther.isSelected()) return Double.parseDouble(txt(otherRateField));
        return 0;
    }

    private void selectToggleByText(ToggleGroup g, String text) {
        if (text == null) { g.selectToggle(null); return; }
        for (Toggle t : g.getToggles()) {
            if (((RadioButton) t).getText().equalsIgnoreCase(text)) {
                g.selectToggle(t);
                return;
            }
        }
        g.selectToggle(null);
    }

    private void selectRateToggle(double rate) {
        if (Math.abs(rate - 0.99) < 0.001)      { rateGroup.selectToggle(rate099); otherRateField.clear(); }
        else if (Math.abs(rate - 1.99) < 0.001) { rateGroup.selectToggle(rate199); otherRateField.clear(); }
        else if (Math.abs(rate - 2.99) < 0.001) { rateGroup.selectToggle(rate299); otherRateField.clear(); }
        else { rateGroup.selectToggle(rateOther); otherRateField.setText(String.valueOf(rate)); }
    }

    private String formatCurrency(double v) {
        return NumberFormat.getCurrencyInstance(Locale.CANADA).format(v);
    }

    private String formatLoanSummary(Loan loan) {
        String name = loan.getCustomer() != null ? loan.getCustomer().getName() : "Unknown";
        String type = (loan.getVehicle() != null && loan.getVehicle().getType() != null)
                ? loan.getVehicle().getType().getLabel()
                : "Vehicle";
        String rate = String.format("%.2f%%", loan.getInterestRate());
        String freq = loan.getFrequency() != null ? loan.getFrequency().getLabel() : "N/A";
        return name + " | " + type + " | " + rate + " | " + loan.getDurationMonths()
                + " months | " + freq;
    }

}

