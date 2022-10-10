package businessLogic;

import java.io.Serializable;

public class BaseProduct extends MenuItem implements Serializable {
    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private double price;

    public BaseProduct (String title, double rating, int calories, int protein, int fat, int sodium, double price) {
        super (title);
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    @Override
    public double getRating () {
        return this.rating;
    }

    @Override
    public void setRating (double rating) {
        this.rating = rating;
    }

    @Override
    public int getCalories () {
        return this.calories;
    }

    @Override
    public void setCalories (int calories) {
        this.calories = calories;
    }

    @Override
    public int getProtein () {
        return this.protein;
    }

    @Override
    public void setProtein (int protein) {
        this.protein = protein;
    }

    @Override
    public int getFat () {
        return this.fat;
    }

    @Override
    public void setFat (int fat) {
        this.fat = fat;
    }

    @Override
    public int getSodium () {
        return this.sodium;
    }

    @Override
    public void setSodium (int sodium) {
        this.sodium = sodium;
    }

    @Override
    public double getPrice () {
        return this.price;
    }

    @Override
    public void setPrice (double price) {
        this.price = price;
    }
}
