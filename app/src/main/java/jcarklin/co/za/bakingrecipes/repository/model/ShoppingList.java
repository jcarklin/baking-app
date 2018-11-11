package jcarklin.co.za.bakingrecipes.repository.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "shopping_list")
public class ShoppingList implements Parcelable {

    @PrimaryKey
    protected Integer id;
    protected String shoppingList;

    public ShoppingList(Integer id, String shoppingList) {
        super();
        this.id = id;
        this.shoppingList = shoppingList;
    }

    public ShoppingList(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.shoppingList = ((String) in.readValue((String.class.getClassLoader())));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(id);
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
}
