package com.kryptkode.cyberman.recipe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kryptkode.cyberman.recipe.R;
import com.kryptkode.cyberman.recipe.model.Recipes;

import java.util.ArrayList;

/**
 * Created by Cyberman on 7/12/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private Recipes [] recipesArray;
    private Context context;
    private LayoutInflater layoutInflater;
    private RecipeAdapterCallbacks recipeAdapterCallbacks;

    public  interface RecipeAdapterCallbacks{
        void onRecipeItemClicked(int position);
        void onStepsButtonClicked(int position);
        void onIngredientsButtonClicked(int position);
    }

    public RecipeAdapter(Context context, Recipes [] recipesArray) {
        this.recipesArray = recipesArray;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(layoutInflater.inflate(R.layout.main_list_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipes recipe = recipesArray[0];
        ImageView cover = holder.coverImageView;
        TextView titleTextView = holder.titleTextView;
        Button stepsButton = holder.stepsButton;
        Button ingredientsButton = holder.ingredientsButton;

        int imageResourceId = R.drawable.ic_app_icon;

        switch (recipe.getRecipeName()){
            case "Nutella":
                imageResourceId = R.drawable.img_nutella_home;
                break;
            case "Brownies":
                imageResourceId = R.drawable.img_brownies_main;
                break;

            case "Cheese Cake":
                imageResourceId = R.drawable.img_cheesecake_main;
                break;

            case "Yellow Cake":
                imageResourceId = R.drawable.img_yellowcake_main;
                break;
        }
        cover.setImageResource(imageResourceId);
        titleTextView.setText(recipe.getRecipeName());
        ingredientsButton.setText(context.getString(R.string.ingredients_num, recipe.getIngredients().length));
        stepsButton.setText(context.getString(R.string.steps_num, recipe.getSteps().length));

    }

    @Override
    public int getItemCount() {
        return recipesArray.length;
    }

    public void setRecipeAdapterCallbacks(RecipeAdapterCallbacks recipeAdapterCallbacks) {
        this.recipeAdapterCallbacks = recipeAdapterCallbacks;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        ImageView coverImageView;
        TextView titleTextView;
        Button stepsButton;
        Button ingredientsButton;

        RecipeViewHolder(View itemView) {
            super(itemView);

            coverImageView = (ImageView) itemView.findViewById(R.id.main_imageView);
            titleTextView = (TextView) itemView.findViewById(R.id.main_title_textView);
            stepsButton = (Button) itemView.findViewById(R.id.main_steps_textView);
            ingredientsButton = (Button) itemView.findViewById(R.id.main_ingredients_textView);

            //set the listeners
            itemView.setOnClickListener(this);
            stepsButton.setOnClickListener(this);
            ingredientsButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.main_steps_textView:
                    recipeAdapterCallbacks.onStepsButtonClicked(getAdapterPosition());
                    break;
                case R.id.main_ingredients_textView:
                    recipeAdapterCallbacks.onIngredientsButtonClicked(getAdapterPosition());
                    break;
                default:
                    recipeAdapterCallbacks.onRecipeItemClicked(getAdapterPosition());
                    break;
            }
        }
    }
}
