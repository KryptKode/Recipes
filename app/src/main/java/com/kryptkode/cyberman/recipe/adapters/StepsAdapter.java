package com.kryptkode.cyberman.recipe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kryptkode.cyberman.recipe.R;
import com.kryptkode.cyberman.recipe.model.Steps;

import java.util.Objects;

/**
 * Created by Cyberman on 7/12/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private StepsAdapterCallbacks stepsAdapterCallbacks;
    private LayoutInflater layoutInflater;
    private Context context;
    private Steps [] steps;

    public StepsAdapter(Context context, Steps[] steps) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.steps = steps;
    }

    public void setStepsAdapterCallbacks(StepsAdapterCallbacks stepsAdapterCallbacks) {
        this.stepsAdapterCallbacks = stepsAdapterCallbacks;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StepsViewHolder(layoutInflater.inflate(R.layout.steps_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        Steps step = steps [position];
        TextView titleTextView = holder.titleTextView;
        TextView descriptionTextView = holder.descriptionTextView;
        View view = holder.view;

        Button playVideoButton = holder.playVideoButton;
        String videoUrl = step.getVideoUrl();

        titleTextView.setText(step.getShortDescription());
        descriptionTextView.setText(step.getDescription());

        if (videoUrl.equals("")){
            playVideoButton.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return steps.length;
    }

    public interface StepsAdapterCallbacks{
        void onPlayVideoButtonClicked();
    }


    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titleTextView;
        TextView descriptionTextView;
        Button playVideoButton;
        View view;

        StepsViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.steps_title_text_view);
            descriptionTextView = (TextView) itemView.findViewById(R.id.steps_description_text_view);
            playVideoButton = (Button) itemView.findViewById(R.id.steps_play_video);
            view = itemView.findViewById(R.id.steps_divider);

            //set a click listener on the button
            playVideoButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            stepsAdapterCallbacks.onPlayVideoButtonClicked();
        }
    }
}
