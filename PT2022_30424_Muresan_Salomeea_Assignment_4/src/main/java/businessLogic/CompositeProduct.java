package businessLogic;

import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem {

    private ArrayList<MenuItem> subMenuItems;

    public CompositeProduct () {
        super ();
    }

    public List<MenuItem> getSubMenuItems () {
        return subMenuItems;
    }

    public void addSubMenuItem (MenuItem menuItem) {
        if (this.subMenuItems == null) subMenuItems = new ArrayList<> ();
        this.subMenuItems.add (menuItem);

    }

    @Override
    public double getRating () {
        double rating = 0;
        for (MenuItem subMenuItem : subMenuItems) {
            rating += subMenuItem.getRating ();
        }
        return rating / subMenuItems.size ();
    }

    @Override
    public void setRating (double rating) {
    }

    @Override
    public int getCalories () {
        int calories = 0;
        for (MenuItem subMenuItem : subMenuItems) {
            calories += subMenuItem.getCalories ();
        }
        System.out.println (calories);
        return calories;
    }

    @Override
    public void setCalories (int calories) {
    }

    @Override
    public int getProtein () {
        int protein = 0;
        for (MenuItem subMenuItem : subMenuItems) {
            protein += subMenuItem.getProtein ();
        }
        return protein;
    }

    @Override
    public void setProtein (int protein) {

    }

    @Override
    public int getFat () {
        int fat = 0;
        for (MenuItem subMenuItem : subMenuItems) {
            fat += subMenuItem.getFat ();
        }
        return fat;
    }

    @Override
    public void setFat (int fat) {

    }

    @Override
    public int getSodium () {
        int sodium = 0;
        for (MenuItem subMenuItem : subMenuItems) {
            sodium += subMenuItem.getSodium ();
        }
        return sodium;
    }

    @Override
    public void setSodium (int Sodium) {

    }

    @Override
    public double getPrice () {
        double price = 0;
        for (MenuItem subMenuItem : subMenuItems) {
            price += subMenuItem.getPrice ();
        }
        return price;
    }

    @Override
    public void setPrice (double price) {

    }
}
