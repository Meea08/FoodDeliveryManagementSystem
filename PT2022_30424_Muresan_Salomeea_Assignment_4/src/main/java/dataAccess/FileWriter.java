package dataAccess;

import businessLogic.MenuItem;
import businessLogic.Orders;
import businessLogic.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class FileWriter {

    public static void writeReport1(List<Orders> ordersList){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter("report1.txt"))) {
            {
                for (Orders orders : ordersList) {
                    bufferedWriter.write("Order id: " + orders.getOrderId() + " --- " +
                            "Client id: " + orders.getClientId() + " --- " + orders.getDate() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeReport2(List<MenuItem> menuItems){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter("report2.txt"));
            for (MenuItem menuItem : menuItems) {
                bufferedWriter.write(menuItem.getTitle() + " ordered " + menuItem.getTimesOrdered() + " times\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeReport3(List<User> users){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter("report3.txt"));
            for (User user : users) {
                bufferedWriter.write(user.getUserID() + " --- " +
                        user.getUsername() + " --- ordered " + user.getOrderCount() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeReport4(List<Orders> orders, Date day){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter("report4.txt"));
            for(Orders order : orders){
                bufferedWriter.write(order.getOrderId() + " ordered on day " + day+"\n");
                System.out.println(order.getDate());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
