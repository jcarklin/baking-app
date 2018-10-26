package jcarklin.co.za.bakingrecipes.repository.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "recipe_steps",
        primaryKeys = {"id","recipe_id"},
        indices = {@Index("recipe_id")},
        foreignKeys = {@ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipe_id",
                onDelete = ForeignKey.CASCADE)})
public class Step implements Parcelable {

    @Json(name = "id")
    @NonNull
    private Integer id;

    @ColumnInfo(name = "short_description")
    @Json(name = "shortDescription")
    private String shortDescription;

    @ColumnInfo(name = "description")
    @Json(name = "description")
    private String description;

    @ColumnInfo(name = "video_url")
    @Json(name = "videoURL")
    private String videoURL;

    @ColumnInfo(name = "thumbnail_url")
    @Json(name = "thumbnailURL")
    private String thumbnailURL;

    @ColumnInfo(name = "recipe_id")
    @NonNull
    private Integer recipeId;

    @Ignore
    public Step() {
    }

    /**
     * 
     * @param id
     * @param shortDescription
     * @param description
     * @param videoURL
     * @param thumbnailURL
     */
    public Step(Integer id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        super();
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    private Step(Parcel in) {
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
        this.recipeId = in.readInt();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
        dest.writeInt(recipeId);
    }

    public int describeContents() {
        return  0;
    }

    public final static Parcelable.Creator<Step> CREATOR = new Creator<Step>() {

        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        public Step[] newArray(int size) {
            return (new Step[size]);
        }

    };
}
