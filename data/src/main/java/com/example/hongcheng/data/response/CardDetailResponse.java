package com.example.hongcheng.data.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.hongcheng.data.response.models.CardRecommend;

import java.util.List;

/**
 * Created by hongcheng on 16/9/15.
 */
public class CardDetailResponse implements Parcelable{
    private List<CardRecommend> cardRecommends;

    public List<CardRecommend> getCardRecommends() {
        return cardRecommends;
    }

    public void setCardRecommends(List<CardRecommend> cardRecommends) {
        this.cardRecommends = cardRecommends;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public CardDetailResponse()
    {
    }

    public CardDetailResponse(Parcel in)
    {
        super();
        this.cardRecommends = in.readArrayList(CardRecommend.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeList(cardRecommends);
    }

    public static final Parcelable.Creator<CardDetailResponse> CREATOR = new Parcelable.Creator<CardDetailResponse>()
    {
        public CardDetailResponse createFromParcel(Parcel in)
        {
            return new CardDetailResponse(in);
        }

        public CardDetailResponse[] newArray(int size)
        {
            return new CardDetailResponse[size];
        }
    };
}
