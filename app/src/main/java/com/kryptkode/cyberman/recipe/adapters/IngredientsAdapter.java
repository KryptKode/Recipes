package com.kryptkode.cyberman.recipe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kryptkode.cyberman.recipe.R;
import com.kryptkode.cyberman.recipe.model.Ingredients;

import java.util.ArrayList;

/**
 * Created by Cyberman on 7/12/2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>{
    private LayoutInflater layoutInflater;
    private Ingredients [] ingredientsArray;

    public IngredientsAdapter(Context context, Ingredients[] ingredientsArray) {
        this.layoutInflater = LayoutInflater.from(context);
        this.ingredientsArray = ingredientsArray;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(layoutInflater.inflate(R.layout.ingredient_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        Ingredients ingredient = ingredientsArray[position];
        TextView numberTextView = holder.numberTextView;
        TextView ingredientTextView = holder.ingredientTextView;
        TextView quantityTextView = holder.quantityTextView;
        ImageView quantityImageView = holder.quantityImageView;

        numberTextView.setText(String.valueOf(position + 1));
        ingredientTextView.setText(ingredient.getIngredient());
        String measure = ingredient.getMeasure();
        String quantityMeasure = String.valueOf(ingredient.getQuantity()) + ingredient.getMeasure();
        quantityTextView.setText(quantityMeasure);
        switch (measure){
            case "CUP":
                quantityImageView.setImageResource(R.drawable.ic_measuring_cup_black);
                break;
            case "TBLSP":
                quantityImageView.setImageResource(R.drawable.ic_tablespoon_spice);
                break;
            case "TSP":
                quantityImageView.setImageResource(R.drawable.ic_teaspoon_white);
                break;
            case "UNIT":
                quantityImageView.setImageResource(R.drawable.ic_restaurant);
                break;
            default:
                quantityImageView.setImageResource(R.drawable.ic_steps);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return ingredientsArray.length;
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder{
        TextView numberTextView;
        TextView ingredientTextView;
        TextView quantityTextView;
        ImageView quantityImageView;

        public IngredientsViewHolder(View itemView) {
            super(itemView);

            numberTextView = (TextView) itemView.findViewById(R.id.number_textView);
            ingredientTextView = (TextView) itemView.findViewById(R.id.ingredient_textView);
            quantityTextView = (TextView) itemView.findViewById(R.id.quantity_textView);
            quantityImageView = (ImageView) itemView.findViewById(R.id.quantity_image_view);
        }
    }
}
