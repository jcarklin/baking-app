package jcarklin.co.za.bakingrecipes.repository.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;
import com.squareup.moshi.Json;

@Entity(tableName = "ingredients")
public class Ingredient implements Parcelable {

    @Json(name = "quantity")
    private Integer quantity;

    @Json(name = "measure")
    private String measure;

    @Json(name = "ingredient")
    private String ingredient;

    @ColumnInfo(name = "recipe_id")
    private Integer recipeId;

    @ColumnInfo(name = "shopping_list")
    private boolean inShoppingList;

    public Ingredient() {

    }

    /**
     * 
     * @param measure
     * @param ingredient
     * @param quantity
     */
    public Ingredient(Integer quantity, String measure, String ingredient) {
        super();
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    protected Ingredient(Parcel in) {
        this.quantity = in.readInt();
        this.measure = in.readString();
        this.ingredient = in.readString();
        this.recipeId = in.readInt();
        this.inShoppingList = in.readInt() != 0;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public boolean isInShoppingList() {
        return inShoppingList;
    }

    public void setInShoppingList(boolean inShoppingList) {
        this.inShoppingList = inShoppingList;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
        dest.writeInt(recipeId);
        dest.writeInt((isInShoppingList() ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return  0;
    }

    public final static Parcelable.Creator<Ingredient> CREATOR = new Creator<Ingredient>() {

        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return (new Ingredient[size]);
        }

    };

}
