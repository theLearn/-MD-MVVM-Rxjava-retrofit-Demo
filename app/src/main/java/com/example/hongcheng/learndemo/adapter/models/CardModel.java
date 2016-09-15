package com.example.hongcheng.learndemo.adapter.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.hongcheng.learndemo.BR;

/**
 * Created by hongcheng on 16/9/4.
 */
public class CardModel extends BaseObservable implements Parcelable{
    private String name;
    private String imageUrl;
    private String description;
    private String type;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    public CardModel(String name, String imageUrl, String description, String type) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.type = type;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public CardModel()
    {

    }

    public CardModel(Parcel in)
    {
        super();
        this.name = in.readString();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeString(type);
    }

    public static final Parcelable.Creator<CardModel> CREATOR = new Parcelable.Creator<CardModel>()
    {
        public CardModel createFromParcel(Parcel in)
        {
            return new CardModel(in);
        }

        public CardModel[] newArray(int size)
        {
            return new CardModel[size];
        }
    };
}
