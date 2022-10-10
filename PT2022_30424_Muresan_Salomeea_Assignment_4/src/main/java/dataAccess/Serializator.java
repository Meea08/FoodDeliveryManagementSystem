package dataAccess;

import businessLogic.MenuItem;
import businessLogic.Orders;
import businessLogic.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Serializator {

    public static HashSet<MenuItem> readItems(String filename){
        HashSet<MenuItem> items = null;
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            items = (HashSet<MenuItem>) in.readObject();

            in.close();
            file.close();

            System.out.println("items have been deserialized");
        }

        catch(IOException | ClassNotFoundException ex)
        {
            System.out.println("Could not deserialize items due unknown causes. Data cannot be loaded.");
        }

        return items;
    }


    public static HashSet<User> readUsers(String filename){
        HashSet<User> users = null;
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            users = (HashSet<User>) in.readObject();

            in.close();
            file.close();

            System.out.println("Users have been deserialized");
        }

        catch(IOException | ClassNotFoundException ex)
        {
            System.out.println("Could not deserialize users due unknown causes. Data cannot be loaded.");
        }

        return users;
    }

}
