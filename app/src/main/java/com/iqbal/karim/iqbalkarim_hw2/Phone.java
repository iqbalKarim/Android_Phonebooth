package com.iqbal.karim.iqbalkarim_hw2;


import android.os.Parcel;
import android.os.Parcelable;

public class Phone implements Parcelable {
    private int id;
    private String name;
    private double price;
    private String description;
    private byte[] image;
    private int storage;
    private int memory;

    public Phone() {
    }

    protected Phone(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
        description = in.readString();
        image = in.createByteArray();
        storage = in.readInt();
        memory = in.readInt();
    }

    public static final Creator<Phone> CREATOR = new Creator<Phone>() {
        @Override
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        @Override
        public Phone[] newArray(int size) {
            return new Phone[size];
        }
    };

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", storage=" + storage +
                ", memory=" + memory +
                '}';
    }

    public Phone(String name, double price, String description, int storage, int memory) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.storage = storage;
        this.memory = memory;
        this.image = null;
    }

    public Phone(int id, String name, int storage, int memory, double price, String description, byte[] image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.storage = storage;
        this.memory = memory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(price);
        parcel.writeString(description);
        parcel.writeByteArray(image);
        parcel.writeInt(storage);
        parcel.writeInt(memory);
    }
}
