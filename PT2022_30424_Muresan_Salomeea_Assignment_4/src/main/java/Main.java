import GUI.LogInGUI;
import businessLogic.*;
import businessLogic.MenuItem;
import dataAccess.FileReader;

import javax.swing.*;
import java.sql.SQLException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws SQLException{
        TreeMap<Orders, ArrayList<MenuItem>> arrayListTreeMap = new TreeMap<>();
        FileReader fileReader = new FileReader();
        List<BaseProduct> baseProductList = fileReader.readBaseProducts("D:\\PT_projects\\PT2022_30424_Muresan_Salomeea_Assignment_4\\products.csv");
        List<MenuItem> menuItemList = new ArrayList<MenuItem>();
        menuItemList.addAll(baseProductList);

        HashSet<User> userHashSet = fileReader.readUsers("D:\\PT_projects\\PT2022_30424_Muresan_Salomeea_Assignment_4\\users.csv");
        List<User> userArrayList = new ArrayList<>(userHashSet);
        ArrayList<MenuItem> shoppingCart = new ArrayList<>();
        DeliveryService deliveryService = new DeliveryService(menuItemList,userArrayList,arrayListTreeMap);

        JFrame frame = new JFrame("Log in");
        frame.setSize(500,500);
        frame.setContentPane(new LogInGUI(deliveryService).getPanel0());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

}
