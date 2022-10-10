package GUI;

import businessLogic.*;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class ClientGUI extends JFrame{

    private JTable productsTable;
    private JTextField nameTextField;
    private JTextField ratingTextField;
    private JTextField caloriesTextField;
    private JTextField proteinTextField;
    private JTextField fatTextField;
    private JTextField sodiumTextField;
    private JTextField priceTextField;

    private JScrollPane scrollPane;
    private JScrollPane scrollPane1;

    public ClientGUI(DeliveryService deliveryService, String username){
        this.setTitle("CLIENT");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        JLabel nameLabel = new JLabel("NAME");
        nameLabel.setBounds(30,20,150,25);
        this.getContentPane().add(nameLabel);
        nameTextField = new JTextField();
        nameTextField.setBounds(100,20,400,25);
        this.getContentPane().add(nameTextField);

        JLabel ratingLabel = new JLabel("RATING");
        ratingLabel.setBounds(30,50,150,25);
        this.getContentPane().add(ratingLabel);
        ratingTextField = new JTextField();
        ratingTextField.setBounds(100,50,400,25);
        this.getContentPane().add(ratingTextField);

        JLabel caloriesLabel = new JLabel("CALORIES");
        caloriesLabel.setBounds(30,80,150,25);
        this.getContentPane().add(caloriesLabel);
        caloriesTextField = new JTextField();
        caloriesTextField.setBounds(100,80,400,25);
        this.getContentPane().add(caloriesTextField);

        JLabel proteinLabel = new JLabel("PROTEIN");
        proteinLabel.setBounds(30,110,150,25);
        this.getContentPane().add(proteinLabel);
        proteinTextField = new JTextField();
        proteinTextField.setBounds(100,110,400,25);
        this.getContentPane().add(proteinTextField);

        JLabel fatLabel = new JLabel("FAT");
        fatLabel.setBounds(30,140,150,25);
        this.getContentPane().add(fatLabel);
        fatTextField = new JTextField();
        fatTextField.setBounds(100,140,400,25);
        this.getContentPane().add(fatTextField);

        JLabel sodiumLabel = new JLabel("SODIUM");
        sodiumLabel.setBounds(30,170,150,25);
        this.getContentPane().add(sodiumLabel);
        sodiumTextField = new JTextField();
        sodiumTextField.setBounds(100,170,400,25);
        this.getContentPane().add(sodiumTextField);

        JLabel priceLabel = new JLabel("PRICE");
        priceLabel.setBounds(30,200,150,25);
        this.getContentPane().add(priceLabel);
        priceTextField = new JTextField();
        priceTextField.setBounds(100,200,400,25);
        this.getContentPane().add(priceTextField);

        JButton searchButton = new JButton("SEARCH PRODUCT");
        searchButton.setBounds(30,230,150,25);
        this.getContentPane().add(searchButton);
        JButton addToCartButton = new JButton("ADD TO CART");
        addToCartButton.setBounds(190,230,150,25);
        this.getContentPane().add(addToCartButton);
        JButton placeOrderButton = new JButton("PLACE ORDER");
        placeOrderButton.setBounds(350,230,150,25);
        this.getContentPane().add(placeOrderButton);

        this.scrollPane = new JScrollPane();
        this.scrollPane.setBounds(30,260,470,200);
        this.getContentPane().add(scrollPane);

        JLabel yourCartLabel = new JLabel("YOUR CART:");
        yourCartLabel.setBounds(30,470,150,25);
        this.getContentPane().add(yourCartLabel);

        scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(30,500,470,100);
        this.getContentPane().add(scrollPane1);

        searchButton.addActionListener(e -> {
            assert deliveryService != null;
            List<MenuItem> menuItemList = deliveryService.getMenuItemList();
            List<MenuItem> searchedFor = new ArrayList<>();
            if(!nameTextField.getText().isEmpty()){
                for (MenuItem menuItem : menuItemList) {
                    if (menuItem.getTitle().contains(nameTextField.getText())) {
                        searchedFor.add(menuItem);
                    }
                }
                menuItemList = searchedFor;
            }
            if(!ratingTextField.getText().isEmpty()){
                for (MenuItem menuItem : menuItemList) {
                    if (menuItem.getRating() == Double.parseDouble(ratingTextField.getText())) {
                        searchedFor.add(menuItem);
                    }
                }
                menuItemList = searchedFor;
            }
            if(!caloriesTextField.getText().isEmpty()){
                for (MenuItem menuItem : menuItemList) {
                    if (menuItem.getCalories() == Integer.parseInt(caloriesTextField.getText())) {
                        searchedFor.add(menuItem);
                    }
                }
                menuItemList = searchedFor;
            }
            if(!proteinTextField.getText().isEmpty()){
                for (MenuItem menuItem : menuItemList) {
                    if (menuItem.getProtein() == Integer.parseInt(proteinTextField.getText())) {
                        searchedFor.add(menuItem);
                    }
                }
                menuItemList = searchedFor;
            }
            if(!fatTextField.getText().isEmpty()){
                for (MenuItem menuItem : menuItemList) {
                    if (menuItem.getFat() == Integer.parseInt(fatTextField.getText())) {
                        searchedFor.add(menuItem);
                    }
                }
                menuItemList = searchedFor;
            }
            if(!sodiumTextField.getText().isEmpty()){
                for (MenuItem menuItem : menuItemList) {
                    if (menuItem.getSodium() == Integer.parseInt(sodiumTextField.getText())) {
                        searchedFor.add(menuItem);
                    }
                }
                menuItemList = searchedFor;
            }
            if(!priceTextField.getText().isEmpty()){
                for (MenuItem menuItem : menuItemList) {
                    if (menuItem.getPrice() == Integer.parseInt(priceTextField.getText())) {
                        searchedFor.add(menuItem);
                    }
                }
                menuItemList = searchedFor;
            }
            if(menuItemList.isEmpty ()) {
                showMessageDialog (null, "No items.");

            }
            else{
                this.makeProductsTable(menuItemList);
            }

        });
        addToCartButton.addActionListener(e -> {
            int selectedRow = productsTable.getSelectedRow();
            String title = (String) productsTable.getValueAt(selectedRow,0);
            int sodium = (int) productsTable.getValueAt(selectedRow,1);
            int fat = (int) productsTable.getValueAt(selectedRow,2);
            int protein = (int) productsTable.getValueAt(selectedRow,3);
            int calories = (int) productsTable.getValueAt(selectedRow,4);
            double rating = (double) productsTable.getValueAt(selectedRow,5);
            double price = (double) productsTable.getValueAt(selectedRow,6);

            MenuItem menuItem = new BaseProduct(title,rating,calories,protein,fat,sodium,price);

            deliveryService.getItemsToBeOrdered().add(menuItem);
            makeOrderTable(deliveryService.getItemsToBeOrdered());
        });
        placeOrderButton.addActionListener(e -> {
            if(deliveryService.getItemsToBeOrdered().isEmpty()){
                showMessageDialog(null,"Your cart is empty.");
            }
            else{
                User user = deliveryService.getUserByUsername(username);
                try {
                    deliveryService.createNewOrder(user.getUserID());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    public void makeProductsTable(List<MenuItem> menuItemList){
        String[] columns = new String[7];
        columns[0] = "Name";
        columns[1] = "Sodium";
        columns[2] = "Fat";
        columns[3] = "Proteins";
        columns[4] = "Calories";
        columns[5] = "Rating";
        columns[6] = "Price";

        Object[][] rows = new Object[menuItemList.size()][7];
        int i = 0;

        for(MenuItem menuItem: menuItemList){
            rows[i][0] = menuItem.getTitle();
            rows[i][1] = menuItem.getSodium();
            rows[i][2] = menuItem.getFat();
            rows[i][3] = menuItem.getProtein();
            rows[i][4] = menuItem.getCalories();
            rows[i][5] = menuItem.getRating();
            rows[i][6] = menuItem.getPrice();
            i++;
        }
        productsTable = new JTable(rows,columns);
        scrollPane.setViewportView(productsTable);

    }

    public void makeOrderTable(List<MenuItem> menuItemList){
        String[] columns = new String[7];
        columns[0] = "Name";
        columns[1] = "Sodium";
        columns[2] = "Fat";
        columns[3] = "Proteins";
        columns[4] = "Calories";
        columns[5] = "Rating";
        columns[6] = "Price";

        Object[][] rows = new Object[menuItemList.size()][7];
        int i = 0;

        for(MenuItem menuItem: menuItemList){
            rows[i][0] = menuItem.getTitle();
            rows[i][1] = menuItem.getSodium();
            rows[i][2] = menuItem.getFat();
            rows[i][3] = menuItem.getProtein();
            rows[i][4] = menuItem.getCalories();
            rows[i][5] = menuItem.getRating();
            rows[i][6] = menuItem.getPrice();
            i++;
        }
        JTable cartTable = new JTable(rows, columns);
        scrollPane1.setViewportView(cartTable);
        scrollPane1.setEnabled(true);
    }
}
