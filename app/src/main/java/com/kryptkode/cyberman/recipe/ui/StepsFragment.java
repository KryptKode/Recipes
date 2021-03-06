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
import com.kryptkode.cyberman.recipe.adapters.StepsAdapter;
import com.kryptkode.cyberman.recipe.model.Steps;

/**
 * Created by Cyberman on 7/12/2017.
 */

public class StepsFragment extends Fragment implements StepsAdapter.StepsAdapterCallbacks{

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private StepsAdapter stepsAdapter;
    private Steps [] steps;
    private StepFragmentCallbacks stepFragmentCallbacks;

    public interface StepFragmentCallbacks{
        void onPlayVideoButtonClicked(String videoUrl, String thumbnail);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        steps = (Steps[]) getArguments().getParcelableArray(Steps.KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.steps_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getContext());
        stepsAdapter = new StepsAdapter(getContext(), steps);
        stepsAdapter.setStepsAdapterCallbacks(this);
        recyclerView.setAdapter(stepsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    public void setSteps(Steps[] steps) {
        this.steps = steps;
    }

    public void setStepFragmentCallbacks(StepFragmentCallbacks stepFragmentCallbacks) {
        this.stepFragmentCallbacks = stepFragmentCallbacks;
    }

    @Override
    public void onPlayVideoButtonClicked(int position) {
        String videoUrl = steps[position].getVideoUrl();
        String thumbnail = steps[position].getThumbnailUrl();
        stepFragmentCallbacks.onPlayVideoButtonClicked(videoUrl, thumbnail);
    }
}
