package GUI;
import businessLogic.DeliveryService;
import businessLogic.User;
import dataAccess.FileReader;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;

import static javax.swing.JOptionPane.showMessageDialog;

public class LogInGUI extends JFrame{
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JButton logInButton;
    private JPanel Panel0;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    public JPanel getPanel0() {
        return Panel0;
    }

    public LogInGUI(DeliveryService deliveryService) {

        logInButton.addActionListener(new ActionListener() {
            private String checkUser(String name, String password){
                FileReader fileReader = new FileReader();
                HashSet<User> users;
                users=fileReader.readUsers("D:/PT_projects/PT2022_30424_Muresan_Salomeea_Assignment_4/users.csv");

                String access = "";
                Iterator<User> userIterator = users.iterator();
                boolean contains=false;
                while(userIterator.hasNext() && !contains){
                    User user = userIterator.next();
                    if(user.getUsername().equals(name)) {
                        if(user.getPassword().equals(password)){
                            contains=true;
                            access= String.valueOf(user.getAccessRights());
                        }
                    }
                }
                if(!contains) {
                    showMessageDialog(null,"Username or password is incorrect");
                    return "-1";
                }
                return access;
            }
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usernameTextField.getText().isEmpty() || passwordTextField.getPassword().length==0){
                    showMessageDialog(null,"Please complete all fields.");
                }
                else if (!checkUser(usernameTextField.getText(), String.valueOf(passwordTextField.getPassword())).equals("-1")){
                    if(checkUser(usernameTextField.getText(), String.valueOf(passwordTextField.getPassword())).equals("ADMINISTRATOR")){
                        JFrame frame = new JFrame("ADMINISTRATOR");
                        frame.setContentPane(new AdminGUI(deliveryService).getContentPane());
                        frame.pack();
                        frame.setVisible(true);
                        frame.setSize(530,200);
                        usernameTextField.setText("");
                        passwordTextField.setText("");
                    }
                    else if(checkUser(usernameTextField.getText(), String.valueOf(passwordTextField.getPassword())).equals("EMPLOYEE")){
                        JFrame frame = new JFrame("EMPLOYEE");
                        frame.setContentPane(new EmployeeGUI(deliveryService).getContentPane());
                        frame.pack();
                        frame.setVisible(true);
                        frame.setSize(470,680);
                        deliveryService.addObserver((Observer) frame);
                        usernameTextField.setText("");
                        passwordTextField.setText("");
                    }
                    else if(checkUser(usernameTextField.getText(), String.valueOf(passwordTextField.getPassword())).equals("CLIENT")){
                        JFrame frame = new JFrame("CLIENT");
                        frame.setContentPane(new ClientGUI(deliveryService, usernameTextField.getText()).getContentPane());
                        frame.pack();
                        frame.setVisible(true);
                        frame.setSize(530,650);
                        usernameTextField.setText("");
                        passwordTextField.setText("");
                    }
                }
            }
        });
    }
}
