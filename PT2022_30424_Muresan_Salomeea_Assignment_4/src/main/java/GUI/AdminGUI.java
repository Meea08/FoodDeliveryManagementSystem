package GUI;

import businessLogic.BaseProduct;
import businessLogic.CompositeProduct;
import businessLogic.DeliveryService;
import businessLogic.MenuItem;
import dataAccess.FileReader;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class AdminGUI extends JFrame {

    private JTextField nameTextField = new JTextField();
    private JTextField ratingTextField = new JTextField();
    private JTextField caloriesTextField = new JTextField();
    private JTextField proteinTextField = new JTextField();
    private JTextField fatTextField = new JTextField();
    private JTextField sodiumTextField = new JTextField();
    private JTextField priceTextField = new JTextField();
    private JTextField pathTextField;
    private JComboBox jComboBox = new JComboBox();
    private JSpinner startHourTextField;
    private JSpinner endHourTextField;
    private JSpinner productNumberTextField;
    private JSpinner clientOrderNumberTextField;
    private JSpinner datePicker;
    JButton jButton = new JButton("Add to composite product");
    private CompositeProduct compositeProduct;

    public AdminGUI(DeliveryService deliveryService){

        this.setTitle("ADMIN");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

         pathTextField = new JTextField();
        pathTextField.setBounds(30,20,300,25);
        this.getContentPane().add(pathTextField);

        JButton importButton = new JButton("Import from .csv");
        importButton.setBounds(350,20,140,25);
        this.getContentPane().add(importButton);

        JButton addButton = new JButton("ADD");
        addButton.setBounds(30,50,140,25);
        this.getContentPane().add(addButton);

        JButton deleteButton = new JButton("DELETE");
        deleteButton.setBounds(190,50,140,25);
        this.getContentPane().add(deleteButton);

        JButton modifyButton = new JButton("MODIFY");
        modifyButton.setBounds(350,50,140,25);
        this.getContentPane().add(modifyButton);

        JButton createProductButton = new JButton("Create composite product");
        createProductButton.setBounds(30,80,300,25);
        this.getContentPane().add(createProductButton);

        JButton generateReportsButton = new JButton("Generate Reports");
        generateReportsButton.setBounds(30,110,300,25);
        this.getContentPane().add(generateReportsButton);

        jButton.addActionListener(e -> {
            if(compositeProduct==null){
                compositeProduct = new CompositeProduct();
            }
            String selectedText = (String) jComboBox.getItemAt(jComboBox.getSelectedIndex());
            MenuItem menuItem = deliveryService.getItemByName(selectedText);
            compositeProduct.addSubMenuItem(menuItem);
        });

        addButton.addActionListener(e -> {
            Object[] message = {
                    "Title", nameTextField,
                    "Rating", ratingTextField,
                    "Calories", caloriesTextField,
                    "Protein", proteinTextField,
                    "Fat", fatTextField,
                    "Sodium",sodiumTextField,
                    "Price", priceTextField
            };
            int option = JOptionPane.showConfirmDialog(null,message,"Add new product",JOptionPane.OK_CANCEL_OPTION);
            if(option == JOptionPane.OK_OPTION) {
                if (!nameTextField.getText().isBlank() &&
                        !ratingTextField.getText().isBlank() &&
                        !caloriesTextField.getText().isBlank() &&
                        !proteinTextField.getText().isBlank() &&
                        !fatTextField.getText().isBlank() &&
                        !sodiumTextField.getText().isBlank() &&
                        !priceTextField.getText().isBlank()){
                    String title = nameTextField.getText();
                    double rating = Double.parseDouble(ratingTextField.getText());
                    int calories = Integer.parseInt(caloriesTextField.getText());
                    int protein = Integer.parseInt(proteinTextField.getText());
                    int fat = Integer.parseInt(fatTextField.getText());
                    int sodium = Integer.parseInt(sodiumTextField.getText());
                    double price = Double.parseDouble(priceTextField.getText());

                    BaseProduct baseProduct = new BaseProduct(title, rating,calories,protein,fat,sodium,price);
                    deliveryService.getMenuItemList().add(baseProduct);
                }
                else showMessageDialog(null, "Please complete all fields");
            }
        });
        deleteButton.addActionListener(e -> {
            populateComboBox(deliveryService.getMenuItemList());
            Object[] message = {
                    "Choose the product you want to delete",
                    jComboBox
            };
            int option = JOptionPane.showConfirmDialog(null,message,"Delete product",JOptionPane.OK_CANCEL_OPTION);
            if(option==JOptionPane.OK_OPTION){
                String title = (String) jComboBox.getSelectedItem();
                for(int i=0;i<deliveryService.getMenuItemList().size();i++){
                    if(deliveryService.getMenuItemList().get(i).getTitle().equals(title)){
                        deliveryService.getMenuItemList().remove(deliveryService.getMenuItemList().get(i));
                        break;
                    }
                }
            }
        });
        modifyButton.addActionListener(e -> {
            jComboBox = new JComboBox();
            populateComboBox(deliveryService.getMenuItemList());
            Object[] message = {
                    "Choose which product you want to modify",jComboBox,
                    "Title", nameTextField,
                    "Rating", ratingTextField,
                    "Calories", caloriesTextField,
                    "Protein", proteinTextField,
                    "Fat", fatTextField,
                    "Sodium",sodiumTextField,
                    "Price", priceTextField
            };
            int option = JOptionPane.showConfirmDialog(null,message,"Modify product",JOptionPane.OK_CANCEL_OPTION);
            if(option==JOptionPane.OK_OPTION){
                MenuItem menuItem = deliveryService.getItemByName((String) jComboBox.getSelectedItem());
                deliveryService.modifyProduct(menuItem,nameTextField.getText(),ratingTextField.getText(),caloriesTextField.getText(),proteinTextField.getText(),fatTextField.getText(),sodiumTextField.getText(),priceTextField.getText());
            }
        });
        createProductButton.addActionListener(e -> {
            JTextField titleTextField = new JTextField();
            jComboBox = new JComboBox();
            populateComboBox(deliveryService.getMenuItemList());
            Object[] message ={
                    "Title", titleTextField,
                    "Add product",jComboBox,
                    jButton
            };
            int option = JOptionPane.showConfirmDialog(null,message,"Create composite product",JOptionPane.OK_CANCEL_OPTION);
            compositeProduct.setTitle(titleTextField.getText());
            if(option == JOptionPane.OK_OPTION){
                MenuItem menuItem = compositeProduct;
                deliveryService.addProduct(menuItem);
            }
            System.out.println(compositeProduct.getSubMenuItems().toString());
        });
        importButton.addActionListener(e -> {
            List<MenuItem> menuItemList = new ArrayList<MenuItem>();
            if(!pathTextField.getText().isEmpty()) {
                FileReader fileReader = new FileReader();
                List<BaseProduct> baseProductList = fileReader.readBaseProducts(pathTextField.getText());
                menuItemList.addAll(baseProductList);
            }
            else{
                FileReader fileReader = new FileReader();
                List<BaseProduct> baseProductList = fileReader.readBaseProducts("D:\\PT_projects\\PT2022_30424_Muresan_Salomeea_Assignment_4\\products.csv");
                menuItemList.addAll(baseProductList);
            }
            deliveryService.setMenuItemList((ArrayList<MenuItem>) menuItemList);
        });
        generateReportsButton.addActionListener(e -> {
            JCheckBox jCheckBox1 = new JCheckBox();
            JCheckBox jCheckBox2 = new JCheckBox();
            JCheckBox jCheckBox3 = new JCheckBox();
            JCheckBox jCheckBox4 = new JCheckBox();
            SpinnerModel hoursValue = new SpinnerNumberModel(0,0,24,1);
            startHourTextField = new JSpinner(hoursValue);
            hoursValue = new SpinnerNumberModel(0,0,24,1);
            endHourTextField = new JSpinner(hoursValue);
            SpinnerModel numberValue = new SpinnerNumberModel(0,0,10000,1);
            productNumberTextField = new JSpinner(numberValue);
            numberValue = new SpinnerNumberModel(0,0,10000,1);
            clientOrderNumberTextField = new JSpinner(numberValue);

            SpinnerModel dateModel = new SpinnerDateModel();
            datePicker  = new JSpinner(dateModel);
            JComponent editor = new JSpinner.DateEditor(datePicker, "dd / MM (MMMM) / yyyy");
            datePicker.setEditor(editor);

            Object[] message ={
                    "Report 1", jCheckBox1,
                    "Start hour: ", startHourTextField,
                    "End hour: ", endHourTextField,
                    "Report 2", jCheckBox2,
                    "Products ordered more than specified number of times:", productNumberTextField,
                    "Report 3", jCheckBox3,
                    "Clients that ordered more than specified number of times:", clientOrderNumberTextField,
                    "Report 4", jCheckBox4,
                    "Day:", datePicker
            };
            int option = JOptionPane.showConfirmDialog(null,message,"Generate reports",JOptionPane.OK_CANCEL_OPTION);
            if(option == JOptionPane.OK_OPTION){
                if(jCheckBox1.isSelected()){
                    deliveryService.generateReport1((Integer) startHourTextField.getValue(), (Integer) endHourTextField.getValue());
                }
                if(jCheckBox2.isSelected()){
                    deliveryService.generateReport2((Integer) productNumberTextField.getValue());
                }
                if(jCheckBox3.isSelected()){
                    deliveryService.generateReport3((Integer) clientOrderNumberTextField.getValue());
                }
                if(jCheckBox4.isSelected()){
                    deliveryService.generateReport4((Date) datePicker.getValue());
                }
            }
        });
    }

    public void populateComboBox(List<MenuItem> menuItemList){
        for (MenuItem menuItem : menuItemList) {
            this.jComboBox.addItem(menuItem.getTitle());
        }
    }
}
