package com.example.hongcheng.learndemo.adapter.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.example.hongcheng.learndemo.BR;

/**
 * Created by hongcheng on 16/9/5.
 */
public class ListInfoModel extends BaseObservable implements Parcelable{

    private String imageUrl;
    private String content;
    private String description;
    private String date;
    private String infoId;

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
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
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    @Bindable
    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
        notifyPropertyChanged(BR.infoId);
    }

    public ListInfoModel(String imageUrl, String content, String description, String date, String infoId) {
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

    public ListInfoModel()
    {
    }

    public ListInfoModel(Parcel in)
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

    public static final Parcelable.Creator<ListInfoModel> CREATOR = new Parcelable.Creator<ListInfoModel>()
    {
        public ListInfoModel createFromParcel(Parcel in)
        {
            return new ListInfoModel(in);
        }

        public ListInfoModel[] newArray(int size)
        {
            return new ListInfoModel[size];
        }
    };
}
