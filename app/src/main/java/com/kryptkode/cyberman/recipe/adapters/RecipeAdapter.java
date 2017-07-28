package com.kryptkode.cyberman.recipe.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kryptkode.cyberman.recipe.R;
import com.kryptkode.cyberman.recipe.model.Recipes;
import com.kryptkode.cyberman.recipe.ui.RecipeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Cyberman on 7/12/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
    public static final String TAG = RecipeFragment.TAG;
    private Recipes [] recipes;
    private Context context;
    private LayoutInflater layoutInflater;
    private RecipeAdapterCallbacks recipeAdapterCallbacks;

    public  interface RecipeAdapterCallbacks{
        void onRecipeItemClicked(int position);
        void onStepsButtonClicked(int position);
        void onIngredientsButtonClicked(int position);
    }



    public RecipeAdapter(Context context, Recipes [ ]recipes ){
        this.recipes = recipes;
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
        Recipes recipe = recipes[position];
        Log.i(TAG, "onBindViewHolder: " + recipe.getName());
        ImageView cover = holder.coverImageView;
        TextView titleTextView = holder.titleTextView;
        Button stepsButton = holder.stepsButton;
        Button ingredientsButton = holder.ingredientsButton;
        String imageUrl = recipe.getImage();

        //if there is an image url, load the image into the image
        if (!imageUrl.isEmpty())  {
            Picasso.with(context).load(imageUrl).into(cover);
        }else{

            int imageResourceId = R.drawable.ic_app_icon;

            switch (recipe.getName()){
                case "Nutella Pie":
                    imageResourceId = R.drawable.img_nutella_home;
                    break;
                case "Brownies":
                    imageResourceId = R.drawable.img_brownies_main;
                    break;

                case "Cheesecake":
                    imageResourceId = R.drawable.img_cheesecake_main;
                    break;

                case "Yellow Cake":
                    imageResourceId = R.drawable.img_yellowcake_main;
                    break;
            }
            cover.setImageResource(imageResourceId);
        }
        titleTextView.setText(recipe.getName());
        ingredientsButton.setText(context.getString(R.string.ingredients_num, recipe.getIngredients().length));
        stepsButton.setText(context.getString(R.string.steps_num, recipe.getSteps().length - 1));

    }

    @Override
    public int getItemCount() {
        if (recipes == null){
            Log.i(TAG, "getItemCount: " + 0);
            return  0;
        }else{
            Log.i(TAG, "getItemCount: " + recipes.length);
            return recipes.length;
        }
    }

    public void setRecipeAdapterCallbacks(RecipeAdapterCallbacks recipeAdapterCallbacks) {
        this.recipeAdapterCallbacks = recipeAdapterCallbacks;
    }

    public void setRecipes(Recipes[] recipes) {
        this.recipes = recipes;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

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
