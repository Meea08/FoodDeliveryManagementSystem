package businessLogic;

import GUI.Observer;
import dataAccess.FileWriter;
import dataAccess.Serializator;

import java.io.*;
import java.util.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DeliveryService extends Serializator implements IDeliveryServiceProcessing, Observable {
    private List<MenuItem> menuItemList; // produsele disponibile in meniu
    private List<User> userList; // utilizatorii aplicatiei
    private NavigableMap<Orders, ArrayList<MenuItem>> ordersMap; // map comanda - produse
    private ArrayList<Orders> ordersArrayList; // lista comenzilor plasate
    private ArrayList<MenuItem> itemsToBeOrdered; // cosul de cumparaturi
    private Serializator serializator = new Serializator ();
    private ArrayList<Observer> observers;

    /**
     * @param menuItemList           = menu items that are available for a client to order
     * @param userList               = all users that have access to this app (client, administrator, employee)
     * @param ordersArrayListHashMap
     */
    public DeliveryService ( List<MenuItem> menuItemList, List<User> userList, NavigableMap<Orders, ArrayList<MenuItem>> ordersArrayListHashMap ) {
        this.menuItemList = menuItemList;
        this.userList = userList;
        this.ordersMap = ordersArrayListHashMap;
        this.ordersArrayList = new ArrayList<> ();
        this.itemsToBeOrdered = new ArrayList<> ();
        this.observers = new ArrayList<> ();
    }

    public List<MenuItem> getMenuItemList () {
        return menuItemList;
    }

    public List<User> getUserList () {
        return userList;
    }

    public ArrayList<Orders> getOrdersArrayList () {
        return ordersArrayList;
    }

    public ArrayList<MenuItem> getItemsToBeOrdered () {
        return itemsToBeOrdered;
    }

    /**
     * @param id = represents an integer corresponding to a client (a client's id)
     * @return a User object
     */
    @Override
    public User getUserById ( int id ) {
        for (User user : userList) {
            if (user.getUserID () == id) {
                return user;
            }
        }
        return null;
    }

    /**
     * @param startHour = the start of the time interval
     * @param endHour   = the end of the time interval of orders
     */
    @Override
    public void generateReport1 ( int startHour, int endHour ) {

        assert isWellFormed ();
        assert startHour < endHour;
        assert startHour > 0 && endHour > 0;
        assert startHour < 24 && endHour < 24;

        ArrayList<Orders> orders;
        orders = (ArrayList<Orders>) this.getOrdersArrayList ().stream ().filter (orders1 -> {
            return orders1.getDate ().getHour () >= startHour;
        }).filter (orders2 -> {
            return orders2.getDate ().getHour () <= endHour;
        }).collect (toList ());
        assert isWellFormed ();
        FileWriter.writeReport1 (orders);
    }

    /**
     * @param minNumber = requested by the admin, should be the lowest part of an interval
     */
    @Override
    public void generateReport2 ( int minNumber ) {
        assert isWellFormed ();
        assert minNumber > 0;

        List<MenuItem> menuItems;
        menuItems = this.getMenuItemList ().stream ().filter (product -> {
            return product.getTimesOrdered () >= minNumber;
        }).collect (toList ());
        assert isWellFormed ();
        FileWriter.writeReport2 (menuItems);
    }

    /**
     * @param timesOrdered = times a product has been ordered, requested by the administrator
     */
    @Override
    public void generateReport3 ( int timesOrdered ) {
        assert isWellFormed ();
        assert timesOrdered > 0;

        List<User> userList;
        userList = this.getUserList ().stream ().filter (user -> {
            return user.getOrderCount () >= timesOrdered;
        }).collect (toList ());
        assert isWellFormed ();
        FileWriter.writeReport3 (userList);
    }

    /**
     * @param specifiedDay = date requested by the administrator, to find all the orders placed in that specific day
     */
    @Override
    public void generateReport4 ( Date specifiedDay ) {
        assert isWellFormed ();
        assert specifiedDay != null;
        List<Orders> ordersList;
        ordersList = this.getOrdersArrayList ().stream ().filter (order -> {
            return specifiedDay.equals (order.getDate ());
        }).collect (toList ());
        assert isWellFormed ();
        FileWriter.writeReport4 (ordersList, specifiedDay);
    }

    /**
     * @param clientId = id of the client performing the new order
     * @throws IOException
     */
    @Override
    public void createNewOrder ( int clientId ) throws IOException {
        assert isWellFormed ();
        assert clientId >= 0;

        StringBuilder string;
        int orderId, price = 0;
        Orders newOrder;
        if (!ordersMap.isEmpty ()) {
            orderId = ordersMap.lastKey ().getOrderId () + 1;
            newOrder = new Orders (clientId, orderId);
        } else {
            newOrder = new Orders (clientId, 0);
        }
        newOrder.setDateNow ();
        int preSize = ordersArrayList.size ();
        ordersArrayList.add (newOrder);
        ordersMap.put (newOrder, itemsToBeOrdered);
        string =
                new StringBuilder ("order no.: " + newOrder.getOrderId () + "\nclient: " + getUserById (clientId).getUsername () + "\n");
        string.append (newOrder.getDate ().getDayOfMonth ()).append (".").append (newOrder.getDate ().getMonth ()).append (".").append (newOrder.getDate ().getYear ()).append (" ").append (newOrder.getDate ().getHour ()).append (":").append (newOrder.getDate ().getMinute ()).append ("\n\n");
        for (MenuItem menuItem : itemsToBeOrdered) {
            price += menuItem.getPrice ();
            string.append (menuItem.getTitle ()).append ("\n");
        }
        string.append ("\n").append (price).append (" lei\n----------------");
        java.io.FileWriter fileWriter = new java.io.FileWriter (generateBill ());
        fileWriter.write (String.valueOf (string));
        notifyObservers (String.valueOf (string));
        System.out.println (string);
        fileWriter.close ();

        assert isWellFormed ();
        assert preSize + 1 == ordersArrayList.size ();
    }

    /**
     * @param menuItem = menu item that will be modified
     * @param title    = new title
     * @param rating   = new rating
     * @param calories = new calories
     * @param protein  = new protein
     * @param fat      = new fat
     * @param sodium   = new sodium
     * @param price    = new price
     */
    @Override
    public void modifyProduct ( MenuItem menuItem, String title, String rating, String calories, String protein, String fat, String sodium, String price ) {
        assert isWellFormed ();
        assert menuItem != null;
        assert !title.equals ("");

        menuItemList.remove (menuItem);
        if (!title.isEmpty ()) {
            menuItem.setTitle (title);
        }
        if (!rating.isEmpty ()) {
            menuItem.setRating (Double.parseDouble (rating));
        }
        if (!calories.isEmpty ()) {
            menuItem.setCalories (Integer.parseInt (calories));
        }
        if (!protein.isEmpty ()) {
            menuItem.setProtein (Integer.parseInt (protein));
        }
        if (!fat.isEmpty ()) {
            menuItem.setFat (Integer.parseInt (fat));
        }
        if (!sodium.isEmpty ()) {
            menuItem.setSodium (Integer.parseInt (sodium));
        }
        if (!price.isEmpty ()) {
            menuItem.setProtein (Integer.parseInt (price));
        }
        menuItemList.add (menuItem);

        MenuItem
                modofiedMenuItem =
                new BaseProduct (title, Double.parseDouble (rating), Integer.parseInt (calories), Integer.parseInt (protein), Integer.parseInt (fat), Integer.parseInt (sodium), Double.parseDouble (price));

        assert menuItemList.get (menuItemList.size () - 1) == modofiedMenuItem;
        assert isWellFormed ();
    }

    /**
     * @param name = name of a product
     * @return a MenuItem type object
     */
    @Override
    public MenuItem getItemByName ( String name ) {
        for (MenuItem menuItem : menuItemList) {
            if (menuItem.getTitle ().equals (name)) return menuItem;
        }
        return null;
    }

    /**
     * @return a file with the last order's bill
     */
    public File generateBill () {
        try {
            File file = new File ("Order" + ordersMap.lastEntry ().getKey ().getOrderId () + ".txt");
            if (file.createNewFile ()) {
                System.out.println (file.getName () + " created");
            } else {
                System.out.println (file.getName () + " already exists");
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * @param menuItem = a new product that will be added to menuItemList
     */
    @Override
    public void addProduct ( MenuItem menuItem ) {

        assert isWellFormed ();
        assert menuItem != null;

        int preSize = menuItemList.size ();
        this.menuItemList.add (menuItem);

        assert isWellFormed ();
        assert preSize + 1 == menuItemList.size ();
    }

    /**
     * @param username = a client's username
     * @return the Client object with the requested username
     */
    @Override
    public User getUserByUsername ( String username ) {
        for (User user : userList) {
            if (user.getUsername ().equals (username)) {
                return user;
            }
        }
        return null;
    }

    public boolean isWellFormed () {
        if (ordersArrayList == null) return false;
        if (userList == null) return false;
        if (menuItemList == null) return false;
        if (itemsToBeOrdered == null) return false;
        if (ordersMap == null) return false;
        return true;
    }

    @Override
    public void addObserver ( Observer observer ) {
        observers.add (observer);
    }

    @Override
    public void notifyObservers ( String message ) {
        for (Observer observer : observers) {
            observer.noticeOrder (message);
        }
    }

    public void setMenuItemList ( ArrayList<MenuItem> menuItemList ) {
        this.menuItemList = menuItemList;
    }

}
