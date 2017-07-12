package com.kryptkode.cyberman.recipe.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Cyberman on 7/12/2017.
 */

public class Recipes implements Parcelable {
    private String recipeName;
    private Ingredients [] ingredients;
    private Steps [] steps;

    public static ArrayList<Recipes> generateDummyRecipes(int number){
        ArrayList<Recipes> recipes = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i< number ; i++){
            int num = random.nextInt(4) + 1;
            Recipes recipe = new Recipes();
            String name = "No name";
            switch (num){
                case 1:
                    name = "Nutella";
                    break;
                case 2:
                    name = "Brownies";
                    break;
                case 3:
                    name = "Cheese Cake";
                    break;

                case 4:
                    name = "Yellow Cake";
                    break;
            }
            recipe.setRecipeName( name);
            recipes.add(recipe);
        }
        return recipes;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
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
        dest.writeString(this.recipeName);
        dest.writeTypedArray(this.ingredients, flags);
        dest.writeTypedArray(this.steps, flags);
    }

    public Recipes() {
    }

    protected Recipes(Parcel in) {
        this.recipeName = in.readString();
        this.ingredients = in.createTypedArray(Ingredients.CREATOR);
        this.steps = in.createTypedArray(Steps.CREATOR);
    }

    public static final Parcelable.Creator<Recipes> CREATOR = new Parcelable.Creator<Recipes>() {
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
