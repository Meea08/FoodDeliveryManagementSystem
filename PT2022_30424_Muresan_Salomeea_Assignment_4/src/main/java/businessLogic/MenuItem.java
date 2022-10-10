package businessLogic;

import java.io.Serializable;
import java.util.Objects;

public abstract class MenuItem implements Serializable {

    private String title;
    private int timesOrdered;

    public MenuItem (String title) {
        this.title = title;
        this.timesOrdered = 0;
    }

    public MenuItem () {

    }

    public int getTimesOrdered () {
        return timesOrdered;
    }

    @Override
    public String toString () {
        return this.title + " ";
    }

    @Override
    public boolean equals (Object object) {
        if (this == object) return true;
        if (object == null || this.getClass() != object.getClass()) return false;
        MenuItem menuItem = (MenuItem) object;
        return this.title.equals(menuItem.title);
    }

    @Override
    public int hashCode () {
        return Objects.hash(this.title);
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public abstract double getRating ();

    public abstract void setRating (double rating);

    public abstract int getCalories ();

    public abstract void setCalories (int calories);

    public abstract int getProtein ();

    public abstract void setProtein (int protein);

    public abstract int getFat ();

    public abstract void setFat (int fat);

    public abstract int getSodium ();

    public abstract void setSodium (int Sodium);

    public abstract double getPrice ();

    public abstract void setPrice (double price);

}
