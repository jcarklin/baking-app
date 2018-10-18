package jcarklin.co.za.bakingrecipes.repository.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.squareup.moshi.Json;

public class Ingredient implements Parcelable
{

    @Json(name = "quantity")
    private Integer quantity;
    @Json(name = "measure")
    private String measure;
    @Json(name = "ingredient")
    private String ingredient;
    public final static Parcelable.Creator<Ingredient> CREATOR = new Creator<Ingredient>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return (new Ingredient[size]);
        }

    }
    ;

    protected Ingredient(Parcel in) {
        this.quantity = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.measure = ((String) in.readValue((String.class.getClassLoader())));
        this.ingredient = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Ingredient withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Ingredient withMeasure(String measure) {
        this.measure = measure;
        return this;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public Ingredient withIngredient(String ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(quantity);
        dest.writeValue(measure);
        dest.writeValue(ingredient);
    }

    public int describeContents() {
        return  0;
    }

}
