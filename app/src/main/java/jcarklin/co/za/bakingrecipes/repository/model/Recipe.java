package jcarklin.co.za.bakingrecipes.repository.model;

import java.util.List;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.squareup.moshi.Json;

@Entity(tableName = "recipes")
public class Recipe implements Parcelable {

    @PrimaryKey
    protected Integer id;

    protected String name;

    protected Integer servings;

    protected String image;

    @Ignore
    public Recipe() {
    }

    /**
     *
     * @param id
     * @param servings
     * @param name
     * @param image
     */
    public Recipe(Integer id, String name, Integer servings, String image) {
        super();
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    private Recipe(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.servings = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(servings);
        dest.writeValue(image);
    }

    @Override
    public int describeContents() {
        return  0;
    }

    public final static Parcelable.Creator<Recipe> CREATOR = new Creator<Recipe>() {

        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return (new Recipe[size]);
        }

    };

}
