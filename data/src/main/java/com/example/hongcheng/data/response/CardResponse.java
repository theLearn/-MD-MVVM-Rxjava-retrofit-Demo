package com.example.hongcheng.data.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hongcheng.data.response.models.Card;

import java.util.List;

/**
 * Created by hongcheng on 16/9/11.
 */
public class CardResponse implements Parcelable{
    private List<Card> cardList;

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public CardResponse()
    {
    }

    public CardResponse(Parcel in)
    {
        super();
        this.cardList = in.readArrayList(Card.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeList(cardList);
    }

    public static final Parcelable.Creator<CardResponse> CREATOR = new Parcelable.Creator<CardResponse>()
    {
        public CardResponse createFromParcel(Parcel in)
        {
            return new CardResponse(in);
        }

        public CardResponse[] newArray(int size)
        {
            return new CardResponse[size];
        }
    };
}
