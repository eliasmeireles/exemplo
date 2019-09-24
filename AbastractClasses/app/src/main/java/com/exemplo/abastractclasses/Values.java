package com.exemplo.abastractclasses;

import android.os.Parcel;

import com.exemplo.abastractclasses.service.BaseService;

public enum Values implements BaseService.ItemRepresentation<Integer> {

    VALUE_ONE(0, "Pizza"),
    VALUE_TWO(1, "Pasta"),
    VALUE_TREE(2, "tizza");
    private int value;
    private String name;

    Values(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer id() {
        return value;
    }


    Values(Parcel in) {
        value = in.readInt();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Values> CREATOR = new Creator<Values>() {
        @Override
        public Values createFromParcel(Parcel in) {
            return Values.values()[in.readInt()];
        }

        @Override
        public Values[] newArray(int size) {
            return new Values[size];
        }
    };
}
