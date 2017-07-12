package com.kryptkode.cyberman.recipe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kryptkode.cyberman.recipe.R;
import com.kryptkode.cyberman.recipe.model.Recipes;

import java.util.ArrayList;

/**
 * Created by Cyberman on 7/12/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private ArrayList<Recipes> recipesArrayList;
    private Context context;
    private LayoutInflater layoutInflater;

    public RecipeAdapter(Context context, ArrayList<Recipes> recipesArrayList) {
        this.recipesArrayList = recipesArrayList;
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
        Recipes recipe = recipesArrayList.get(position);
        ImageView cover = holder.coverImageView;
        TextView titleTextView = holder.titleTextView;
        TextView stepsTextView = holder.stepsTextView;
        TextView ingredientsTextView = holder.ingredientsTextView;
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
        ingredientsTextView.setText(context.getString(R.string.ingredients_num));
        stepsTextView.setText(context.getString(R.string.steps_num));

    }

    @Override
    public int getItemCount() {
        return recipesArrayList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{

        ImageView coverImageView;
        TextView titleTextView;
        TextView stepsTextView;
        TextView ingredientsTextView;

        RecipeViewHolder(View itemView) {
            super(itemView);

            coverImageView = (ImageView) itemView.findViewById(R.id.main_imageView);
            titleTextView = (TextView) itemView.findViewById(R.id.main_title_textView);
            stepsTextView = (TextView) itemView.findViewById(R.id.main_steps_textView);
            ingredientsTextView = (TextView) itemView.findViewById(R.id.main_ingredients_textView);
        }
    }
}
