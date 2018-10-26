package jcarklin.co.za.bakingrecipes.repository.model;

import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.util.List;

public class RecipeComplete extends Recipe implements Parcelable
{

    @Relation(parentColumn = "id", entityColumn = "recipe_id")
    @Json(name = "ingredients")
    private List<Ingredient> ingredients = null;

    @Relation(parentColumn = "id", entityColumn = "recipe_id")
    @Json(name = "steps")
    private List<Step> steps = null;

    public RecipeComplete() {
    }

    /**
     *
     * @param ingredients
     * @param id
     * @param servings
     * @param name
     * @param image
     * @param steps
     */
    public RecipeComplete(Integer id, String name, List<Ingredient> ingredients, List<Step> steps, Integer servings, String image) {
        super();
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    private RecipeComplete(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.ingredients, (Ingredient.class.getClassLoader()));
        in.readList(this.steps, (Step.class.getClassLoader()));
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
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
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeValue(servings);
        dest.writeValue(image);
    }

    @Override
    public int describeContents() {
        return  0;
    }

    public final static Creator<RecipeComplete> CREATOR = new Creator<RecipeComplete>() {

        public RecipeComplete createFromParcel(Parcel in) {
            return new RecipeComplete(in);
        }

        public RecipeComplete[] newArray(int size) {
            return (new RecipeComplete[size]);
        }

    };

}
