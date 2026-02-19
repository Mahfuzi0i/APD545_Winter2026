/**********************************************
 Workshop 03
 Course: APD545 - Semester Winter 2026
 Last Name: Mahfuz
 First Name: Abdullah Al
 ID:180377236
 Section:NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Abdullah Al Mahfuz
 Date:19 Feb 2026
 **********************************************/

package autoloan.controller;

import autoloan.app.AppConfig;
import autoloan.model.Customer;
import autoloan.model.Loan;
import autoloan.model.Vehicle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.Locale;

public class LoanController {

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField cityField;
    @FXML private ComboBox<String> provinceBox;

    @FXML private RadioButton carRadio;
    @FXML private RadioButton truckRadio;
    @FXML private RadioButton vanRadio;
    @FXML private RadioButton newRadio;
    @FXML private RadioButton usedRadio;
    @FXML private TextField priceField;

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

    private final ToggleGroup typeGroup = new ToggleGroup();
    private final ToggleGroup ageGroup = new ToggleGroup();
    private final ToggleGroup rateGroup = new ToggleGroup();
    private final ToggleGroup frequencyGroup = new ToggleGroup();

    @FXML
    public void initialize(){

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
                "Ontario","Quebec","Alberta","British Columbia",
                "Manitoba","New Brunswick","Newfoundland and Labrador",
                "Nova Scotia","Prince Edward Island","Saskatchewan"
        );

        savedLoans.setItems(AppConfig.loanRepository.getLoans());
        savedLoans.getSelectionModel().selectedItemProperty().addListener((obs, oldLoan, selectedLoan) -> {
            if(selectedLoan != null){
                loadLoan(selectedLoan);
            }
        });

        durationSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if(validateAllInputs(false)) {
                calculate();
            }
        });

        setupRealtimeValidation();
    }

    private void setupRealtimeValidation() {
        nameField.textProperty().addListener((obs, oldVal, newVal) -> validateName(false));
        nameField.focusedProperty().addListener((obs, oldVal, focused) -> { if(!focused) validateName(true); });

        phoneField.textProperty().addListener((obs, oldVal, newVal) -> validatePhone(false));
        phoneField.focusedProperty().addListener((obs, oldVal, focused) -> { if(!focused) validatePhone(true); });

        cityField.textProperty().addListener((obs, oldVal, newVal) -> validateCity(false));
        cityField.focusedProperty().addListener((obs, oldVal, focused) -> { if(!focused) validateCity(true); });

        provinceBox.valueProperty().addListener((obs, oldVal, newVal) -> validateProvince(false));

        typeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> validateVehicleType(false));
        ageGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> validateVehicleAge(false));

        priceField.textProperty().addListener((obs, oldVal, newVal) -> validatePrice(false));
        priceField.focusedProperty().addListener((obs, oldVal, focused) -> { if(!focused) validatePrice(true); });

        downField.textProperty().addListener((obs, oldVal, newVal) -> validateDownPayment(false));
        downField.focusedProperty().addListener((obs, oldVal, focused) -> { if(!focused) validateDownPayment(true); });

        rateGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            updateOtherRateFieldState();
            validateRate(false);
        });
        otherRateField.textProperty().addListener((obs, oldVal, newVal) -> validateRate(false));
        otherRateField.focusedProperty().addListener((obs, oldVal, focused) -> {
            if (focused && !rateOther.isSelected()) {
                showWarning("please select other box to specify rate");
                nameField.requestFocus();
            }
        });
        otherRateField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!rateOther.isSelected()) {
                showWarning("please select other box to specify rate");
                event.consume();
            }
        });

        frequencyGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> validateFrequency(false));
    }

    @FXML
    public void calculate(){
        if(!validateAllInputs(true)){
            paymentLabel.setText("Invalid Input");
            return;
        }

        Loan loan = createLoan();
        double payment = calculatePaymentForLoan(loan);
        loan.setEstimatedPayment(payment);
        paymentLabel.setText(formatCurrency(payment));
    }

    @FXML
    private void clearForm() {

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

    @FXML
    public void saveLoan(){
        if(!validateAllInputs(true)){
            paymentLabel.setText("Cannot save. Check inputs.");
            return;
        }

        Loan loan = createLoan();
        double payment = calculatePaymentForLoan(loan);
        loan.setEstimatedPayment(payment);
        AppConfig.loanRepository.saveLoan(loan);
        paymentLabel.setText(formatCurrency(payment));
    }

    private Loan createLoan(){

        Customer customer = new Customer();
        customer.setName(nameField.getText().trim());
        customer.setPhone(phoneField.getText().trim());
        customer.setCity(cityField.getText().trim());
        customer.setProvince(provinceBox.getValue());

        Vehicle vehicle = new Vehicle();
        vehicle.setPrice(Double.parseDouble(priceField.getText().trim()));
        vehicle.setType(getSelectedText(typeGroup));
        vehicle.setAge(getSelectedText(ageGroup));

        Loan loan = new Loan(customer, vehicle);
        loan.setDownPayment(Double.parseDouble(downField.getText().trim()));
        loan.setInterestRate(getSelectedInterestRate());
        loan.setDurationMonths((int)durationSlider.getValue());
        loan.setFrequency(getSelectedText(frequencyGroup));

        return loan;
    }

    private double calculatePaymentForLoan(Loan loan) {
        double principal = loan.getVehicle().getPrice() - loan.getDownPayment();
        double monthlyPayment = AppConfig.loanCalculation.calculatePayment(
                principal,
                loan.getInterestRate(),
                loan.getDurationMonths()
        );

        String frequency = loan.getFrequency();
        if("Weekly".equalsIgnoreCase(frequency)) {
            return monthlyPayment * 12.0 / 52.0;
        }
        if("Bi-weekly".equalsIgnoreCase(frequency)) {
            return monthlyPayment * 12.0 / 26.0;
        }
        return monthlyPayment;
    }

    private void loadLoan(Loan loan){

        nameField.setText(loan.getCustomer().getName());
        phoneField.setText(loan.getCustomer().getPhone());
        cityField.setText(loan.getCustomer().getCity());
        provinceBox.setValue(loan.getCustomer().getProvince());

        priceField.setText(String.valueOf(loan.getVehicle().getPrice()));
        downField.setText(String.valueOf(loan.getDownPayment()));
        durationSlider.setValue(loan.getDurationMonths());

        selectToggleByText(typeGroup, loan.getVehicle().getType());
        selectToggleByText(ageGroup, loan.getVehicle().getAge());
        selectRateToggle(loan.getInterestRate());
        selectToggleByText(frequencyGroup, loan.getFrequency());

        if(loan.getEstimatedPayment() <= 0) {
            double payment = calculatePaymentForLoan(loan);
            loan.setEstimatedPayment(payment);
        }
        paymentLabel.setText(formatCurrency(loan.getEstimatedPayment()));

        validateAllInputs(false);
    }

    @FXML
    public void openAmortization() {

        if(!validateAllInputs(true)){
            new Alert(Alert.AlertType.ERROR, "Please correct the highlighted fields.").show();
            return;
        }

        try {
            Loan loan = createLoan();
            loan.setEstimatedPayment(calculatePaymentForLoan(loan));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/autoloan/view/amortization.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Amortization Schedule");
            stage.setScene(new Scene(loader.load(), 1400, 900));

            LoanAmortizationController controller = loader.getController();
            controller.loadLoan(loan);

            stage.show();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please calculate the loan first.").show();
        }
    }

    private boolean validateAllInputs(boolean showAlerts) {
        if(!validateName(showAlerts)) return false;
        if(!validatePhone(showAlerts)) return false;
        if(!validateCity(showAlerts)) return false;
        if(!validateProvince(showAlerts)) return false;
        if(!validateVehicleType(showAlerts)) return false;
        if(!validateVehicleAge(showAlerts)) return false;
        if(!validatePrice(showAlerts)) return false;
        if(!validateDownPayment(showAlerts)) return false;
        if(!validateRate(showAlerts)) return false;
        return validateFrequency(showAlerts);
    }

    private boolean validateName(boolean showAlert) {
        String value = nameField.getText() == null ? "" : nameField.getText().trim();
        boolean valid = !value.isEmpty() && value.matches("[A-Za-z ]+");
        markControl(nameField, valid);
        if(showAlert && !valid) showWarning("Customer name is required (letters and spaces only).");
        return valid;
    }

    private boolean validatePhone(boolean showAlert) {
        String value = phoneField.getText() == null ? "" : phoneField.getText().trim();
        boolean valid = value.matches("\\d{10}");
        markControl(phoneField, valid);
        if(showAlert && !valid) showWarning("Phone number must be exactly 10 digits.");
        return valid;
    }

    private boolean validateCity(boolean showAlert) {
        String value = cityField.getText() == null ? "" : cityField.getText().trim();
        boolean valid = !value.isEmpty() && value.matches("[A-Za-z ]+");
        markControl(cityField, valid);
        if(showAlert && !valid) showWarning("City is required (letters and spaces only).");
        return valid;
    }

    private boolean validateProvince(boolean showAlert) {
        boolean valid = provinceBox.getValue() != null && !provinceBox.getValue().trim().isEmpty();
        markControl(provinceBox, valid);
        if(showAlert && !valid) showWarning("Please select a province.");
        return valid;
    }

    private boolean validateVehicleType(boolean showAlert) {
        boolean valid = typeGroup.getSelectedToggle() != null;
        markRadioButtons(new RadioButton[]{carRadio, truckRadio, vanRadio}, valid);
        if(showAlert && !valid) showWarning("Please select vehicle type (Car, Truck, or Family Van).");
        return valid;
    }

    private boolean validateVehicleAge(boolean showAlert) {
        boolean valid = ageGroup.getSelectedToggle() != null;
        markRadioButtons(new RadioButton[]{newRadio, usedRadio}, valid);
        if(showAlert && !valid) showWarning("Please select vehicle age (New or Used).");
        return valid;
    }

    private boolean validatePrice(boolean showAlert) {
        boolean valid;
        String value = priceField.getText() == null ? "" : priceField.getText().trim();
        try {
            valid = !value.isEmpty() && Double.parseDouble(value) > 0;
        } catch (NumberFormatException e) {
            valid = false;
        }

        markControl(priceField, valid);
        if(showAlert && !valid) showWarning("Vehicle price must be a valid number greater than 0.");
        return valid;
    }

    private boolean validateDownPayment(boolean showAlert) {
        boolean valid;
        String value = downField.getText() == null ? "" : downField.getText().trim();

        try {
            double down = Double.parseDouble(value);
            double price = Double.parseDouble(priceField.getText().trim());
            valid = down >= 0 && down < price;
        } catch (Exception e) {
            valid = false;
        }

        markControl(downField, valid);
        if(showAlert && !valid) showWarning("Down payment must be a valid number, 0 or greater, and less than vehicle price.");
        return valid;
    }

    private boolean validateRate(boolean showAlert) {
        boolean valid = rateGroup.getSelectedToggle() != null;

        if(valid && rateOther.isSelected()) {
            try {
                double rate = Double.parseDouble(otherRateField.getText().trim());
                valid = rate > 0;
            } catch (Exception e) {
                valid = false;
            }
        }

        markRadioButtons(new RadioButton[]{rate099, rate199, rate299, rateOther}, valid);
        markControl(otherRateField, !rateOther.isSelected() || valid);

        if(showAlert && !valid) showWarning("Please select a valid interest rate.");
        return valid;
    }

    private boolean validateFrequency(boolean showAlert) {
        boolean valid = frequencyGroup.getSelectedToggle() != null;
        markRadioButtons(new RadioButton[]{weeklyRadio, biweeklyRadio, monthlyRadio}, valid);
        if(showAlert && !valid) showWarning("Please select payment frequency.");
        return valid;
    }

    private void markControl(Control control, boolean valid) {
        control.setStyle(valid ? "" : "-fx-border-color: red; -fx-border-width: 2;");
    }

    private void markRadioButtons(RadioButton[] radios, boolean valid) {
        String style = valid ? "" : "-fx-text-fill: red;";
        for (RadioButton radio : radios) {
            radio.setStyle(style);
        }
    }

    private void clearStyles() {
        markControl(nameField, true);
        markControl(phoneField, true);
        markControl(cityField, true);
        markControl(provinceBox, true);
        markControl(priceField, true);
        markControl(downField, true);
        markControl(otherRateField, true);

        markRadioButtons(new RadioButton[]{carRadio, truckRadio, vanRadio}, true);
        markRadioButtons(new RadioButton[]{newRadio, usedRadio}, true);
        markRadioButtons(new RadioButton[]{rate099, rate199, rate299, rateOther}, true);
        markRadioButtons(new RadioButton[]{weeklyRadio, biweeklyRadio, monthlyRadio}, true);
    }

    private void updateOtherRateFieldState() {
        boolean isOtherSelected = rateOther.isSelected();
        otherRateField.setEditable(isOtherSelected);
        if(!isOtherSelected) {
            otherRateField.clear();
        }
    }

    private void showWarning(String message) {
        new Alert(Alert.AlertType.WARNING, message).show();
    }

    private String getSelectedText(ToggleGroup group) {
        if(group.getSelectedToggle() == null) {
            return "";
        }
        return ((RadioButton) group.getSelectedToggle()).getText();
    }

    private double getSelectedInterestRate(){
        if(rate099.isSelected()) return 0.99;
        if(rate199.isSelected()) return 1.99;
        if(rate299.isSelected()) return 2.99;
        if(rateOther.isSelected()) return Double.parseDouble(otherRateField.getText().trim());
        return 0;
    }

    private void selectToggleByText(ToggleGroup group, String text) {
        if(text == null) {
            group.selectToggle(null);
            return;
        }

        for (Toggle toggle : group.getToggles()) {
            RadioButton radioButton = (RadioButton) toggle;
            if(text.equalsIgnoreCase(radioButton.getText())) {
                group.selectToggle(toggle);
                return;
            }
        }
        group.selectToggle(null);
    }

    private void selectRateToggle(double rate) {
        if (Math.abs(rate - 0.99) < 0.0001) {
            rateGroup.selectToggle(rate099);
            otherRateField.clear();
        } else if (Math.abs(rate - 1.99) < 0.0001) {
            rateGroup.selectToggle(rate199);
            otherRateField.clear();
        } else if (Math.abs(rate - 2.99) < 0.0001) {
            rateGroup.selectToggle(rate299);
            otherRateField.clear();
        } else {
            rateGroup.selectToggle(rateOther);
            otherRateField.setText(String.valueOf(rate));
        }
    }

    private String formatCurrency(double value) {
        return NumberFormat.getCurrencyInstance(Locale.CANADA).format(value);
    }
}
