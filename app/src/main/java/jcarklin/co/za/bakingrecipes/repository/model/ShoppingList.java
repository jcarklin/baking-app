package jcarklin.co.za.bakingrecipes.repository.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import static jcarklin.co.za.bakingrecipes.repository.model.ShoppingList.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class ShoppingList implements Parcelable {

    public static final String TABLE_NAME = "shopping_list";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RECIPE_NAME = "recipe_name";
    public static final String COLUMN_SHOPPING_LIST = "shopping_list_items";

    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    protected Integer id;
    @ColumnInfo(name = COLUMN_RECIPE_NAME)
    protected String recipeName;
    @ColumnInfo(name = COLUMN_SHOPPING_LIST)
    protected String shoppingList;

    public ShoppingList(Integer id, String recipeName, String shoppingList) {
        super();
        this.id = id;
        this.recipeName = recipeName;
        this.shoppingList = shoppingList;
    }

    public ShoppingList(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.recipeName = ((String) in.readValue((String.class.getClassLoader())));
        this.shoppingList = ((String) in.readValue((String.class.getClassLoader())));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(id);
        parcel.writeValue(recipeName);
        parcel.writeValue(shoppingList);
    }

    public final static Parcelable.Creator<ShoppingList> CREATOR = new Creator<ShoppingList>() {

        public ShoppingList createFromParcel(Parcel in) {
            return new ShoppingList(in);
        }

        public ShoppingList[] newArray(int size) {
            return (new ShoppingList[size]);
        }

    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(String shoppingList) {
        this.shoppingList = shoppingList;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
