package com.kryptkode.cyberman.recipe.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Cyberman on 7/12/2017.
 */

public class Steps implements Parcelable {
    public static final String KEY = "step_key";
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;
    private int recipeId;


    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoURL;
    }

    public void setVideoUrl(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailUrl() {
        return thumbnailURL;
    }

    public void setThumbnailUrl(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }


    public Steps() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
        dest.writeInt(this.recipeId);
    }

    protected Steps(Parcel in) {
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
        this.recipeId = in.readInt();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel source) {
            return new Steps(source);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
}
