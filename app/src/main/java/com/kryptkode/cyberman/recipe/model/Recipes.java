package com.kryptkode.cyberman.recipe.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Cyberman on 7/12/2017.
 */

public class Recipes implements Parcelable {
    public static final String KEY = "name_of_recipe";
    public static final String ID = "id_of_recipe";
    int id;
    private String name;
    private int servings;
    private String image;
    private Ingredients [] ingredients;
    private Steps [] steps;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Ingredients[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients[] ingredients) {
        this.ingredients = ingredients;
    }

    public Steps[] getSteps() {
        return steps;
    }

    public void setSteps(Steps[] steps) {
        this.steps = steps;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.servings);
        dest.writeString(this.image);
        dest.writeTypedArray(this.ingredients, flags);
        dest.writeTypedArray(this.steps, flags);
    }

    public Recipes() {
    }

    protected Recipes(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.servings = in.readInt();
        this.image = in.readString();
        this.ingredients = in.createTypedArray(Ingredients.CREATOR);
        this.steps = in.createTypedArray(Steps.CREATOR);
    }

    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel source) {
            return new Recipes(source);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };
}
