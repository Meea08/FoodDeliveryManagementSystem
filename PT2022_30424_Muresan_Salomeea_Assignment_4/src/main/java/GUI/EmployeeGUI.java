package GUI;

import businessLogic.DeliveryService;
import businessLogic.Orders;

import javax.swing.*;
import java.util.ArrayList;

public class EmployeeGUI extends JFrame implements Observer{
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private DeliveryService deliveryService;

    public EmployeeGUI(DeliveryService deliveryService){
        this.deliveryService = deliveryService;

        this.setTitle("EMPLOYEE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        //textArea.setEditable(false);

        textArea.setBounds(30,20,400,600);
        this.getContentPane().add(textArea);
        textArea.setText("Orders:\n");

    }
    public void addTextToArea(String text){
        //textArea.setText(textArea.getText().concat(text));
        textArea.append(text);
        System.out.println(text);
    }

    @Override
    public void noticeOrder(String message) {
        addTextToArea(message);
    }
}
