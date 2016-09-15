package com.example.hongcheng.data.response.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hongcheng on 16/9/15.
 */
public class CardRecommend implements Parcelable{
    private String imageUrl;
    private String content;
    private String description;
    private String date;
    private String infoId;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public CardRecommend(String imageUrl, String content, String description, String date, String infoId) {
        this.imageUrl = imageUrl;
        this.content = content;
        this.description = description;
        this.date = date;
        this.infoId = infoId;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public CardRecommend()
    {
    }

    public CardRecommend(Parcel in)
    {
        super();
        this.imageUrl = in.readString();
        this.content = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        this.infoId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(imageUrl);
        dest.writeString(content);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(infoId);
    }

    public static final Parcelable.Creator<CardRecommend> CREATOR = new Parcelable.Creator<CardRecommend>()
    {
        public CardRecommend createFromParcel(Parcel in)
        {
            return new CardRecommend(in);
        }

        public CardRecommend[] newArray(int size)
        {
            return new CardRecommend[size];
        }
    };
}
