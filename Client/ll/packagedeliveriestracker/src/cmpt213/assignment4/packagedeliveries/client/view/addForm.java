package cmpt213.assignment4.packagedeliveries.client.view;

import cmpt213.assignment4.packagedeliveries.client.control.PackageManager;
import cmpt213.assignment4.packagedeliveries.client.model.PackageFactory;
import cmpt213.assignment4.packagedeliveries.client.model.PkgInfo;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class for dialog to add packages in the GUI
 */
public class
addForm extends JDialog implements ActionListener {
    private final PackageManager packageManager;
    MainGUI mainGUI;
    private JPanel mainPanel;
    private JButton createButton;
    private JLabel typeLabel;
    private JLabel nameLabel;
    private JLabel customLabel;
    private JLabel priceLabel;
    private JLabel noteLabel;
    private JLabel dateLabel;
    private JLabel weightLabel;
    private JComboBox typeBox;
    private JTextField nameField;
    private JTextField notesField;
    private JTextField priceField;
    private JTextField weightField;
    private JTextField customField;
    private JButton cancelButton;
    private DatePicker deliveryDatePicker;
    private TimePicker deliveryTimePicker;
    private JLabel expiryDateLabel;
    private TimePicker expiryTimePicker;
    private DatePicker expiryDatePicker;
    private int typeIndex = 0;

    /**
     * Constructor which takes in the parent class
     *
     * @param mg
     */
    public addForm(MainGUI mg) {

        super(mg, "Add form");
        mainGUI = mg;
        packageManager = new PackageManager();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        setSize(500, 300);
        typeBox.addActionListener(this);
        createButton.addActionListener(this);
        cancelButton.addActionListener(this);
        expiryDateLabel.setVisible(false);
        expiryDatePicker.setVisible(false);
        expiryTimePicker.setVisible(false);
    }

    /**
     * method to test if an inputted string is a number
     *
     * @param str
     * @return boolean
     */
    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Action listeners for each component
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == typeBox) {
            if (typeBox.getSelectedItem() == "Book") {
                customLabel.setText("Author:");
                customLabel.setVisible(true);
                customField.setVisible(true);
                expiryDateLabel.setVisible(false);
                expiryDatePicker.setVisible(false);
                expiryTimePicker.setVisible(false);
                setSize(500, 300);
                typeIndex = 0;
                System.out.println(typeIndex);

            } else if (typeBox.getSelectedItem() == "Perishable") {
                customLabel.setVisible(false);
                expiryDateLabel.setVisible(true);
                expiryDatePicker.setVisible(true);
                expiryTimePicker.setVisible(true);
                customField.setVisible(false);
                setSize(500, 300);
                typeIndex = 1;
                System.out.println(typeIndex);


            } else {
                customLabel.setText("Fee");
                customLabel.setVisible(true);
                customField.setVisible(true);
                expiryDateLabel.setVisible(false);
                expiryDatePicker.setVisible(false);
                expiryTimePicker.setVisible(false);
                setSize(500, 300);
                typeIndex = 2;
                System.out.println(typeIndex);

            }
        }
        if (e.getSource() == cancelButton) {
            dispose();
        }

        if (e.getSource() == createButton) {

            if (Objects.equals(nameField.getText(), "") || Objects.equals(weightField.getText(), "")
                    || Objects.equals(priceField.getText(), "")) {
                JOptionPane.showMessageDialog(this, "Field(s) cannot be blank (except notes)",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (typeIndex == 0 || typeIndex == 2) {
                if (Objects.equals(customField.getText(), "")) {
                    JOptionPane.showMessageDialog(this, "Field(s) cannot be blank (except notes)",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (typeIndex == 2) {
                    if (!isNumber(customField.getText())) {

                        JOptionPane.showMessageDialog(this, "Please only enter decimal numbers for fee",
                                "ERROR", JOptionPane.ERROR_MESSAGE);

                    }
                }
            } else if (expiryDatePicker.getDate() == null || expiryTimePicker.getTime() == null) {
                JOptionPane.showMessageDialog(this, "Date(s) cannot be blank",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (deliveryDatePicker.getDate() == null || deliveryTimePicker.getTime() == null) {
                JOptionPane.showMessageDialog(this, "Date(s) cannot be blank",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isNumber(weightField.getText()) || !isNumber(priceField.getText())) {

                JOptionPane.showMessageDialog(this, "Please only enter decimal numbers for price and weight",
                        "ERROR", JOptionPane.ERROR_MESSAGE);

            }

            LocalDateTime ld = LocalDateTime.of(deliveryDatePicker.getDate(), deliveryTimePicker.getTime());

            if (typeIndex == 0) {
                PkgInfo pkg = PackageFactory.getPackage(1, nameField.getText(), notesField.getText(),
                        Double.parseDouble(priceField.getText()), Double.parseDouble(weightField.getText()), ld);
                assert pkg != null;
                pkg.setAuthor(customField.getText());
                packageManager.add(pkg);

            } else if (typeIndex == 1) {
                PkgInfo pkg = PackageFactory.getPackage(2, nameField.getText(), notesField.getText(),
                        Double.parseDouble(priceField.getText()), Double.parseDouble(weightField.getText()), ld);
                LocalDateTime ld2 = LocalDateTime.of(expiryDatePicker.getDate(), expiryTimePicker.getTime());
                assert pkg != null;
                pkg.setExpiryDate(ld2);
                packageManager.add(pkg);
            } else {
                PkgInfo pkg = PackageFactory.getPackage(3, nameField.getText(), notesField.getText(),
                        Double.parseDouble(priceField.getText()), Double.parseDouble(weightField.getText()), ld);
                assert pkg != null;
                pkg.setFee(Double.parseDouble(customField.getText()));
                packageManager.add(pkg);
            }
            packageManager.saveData();
            mainGUI.populateList();
            setVisible(false);

        }


    }
}
