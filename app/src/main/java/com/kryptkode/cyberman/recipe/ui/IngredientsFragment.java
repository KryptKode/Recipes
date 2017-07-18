package com.kryptkode.cyberman.recipe.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kryptkode.cyberman.recipe.R;
import com.kryptkode.cyberman.recipe.adapters.IngredientsAdapter;
import com.kryptkode.cyberman.recipe.model.Ingredients;

/**
 * Created by Cyberman on 7/12/2017.
 */

public class IngredientsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private IngredientsAdapter ingredientsAdapter;
    private Ingredients [] ingredientsArray;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getContext());
        ingredientsAdapter = new IngredientsAdapter(getContext(), ingredientsArray);
        recyclerView.setAdapter(ingredientsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    public void setIngredientsArray(Ingredients[] ingredientsArray) {
        this.ingredientsArray = ingredientsArray;
    }
}
