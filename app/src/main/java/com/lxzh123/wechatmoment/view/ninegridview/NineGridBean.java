package com.lxzh123.wechatmoment.view.ninegridview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * description: 九宫格单元格绑定数据Bean
 *              NOTE:T class must implements the interface of Parcelable
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class NineGridBean<T extends Parcelable> implements Parcelable
{
    private String thumbUrl;
    private String originUrl;

    public NineGridBean(String thumbUrl, String originUrl)
    {
        this.thumbUrl = thumbUrl;
        this.originUrl = originUrl;
    }

    public String getThumbUrl()
    {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl)
    {
        this.thumbUrl = thumbUrl;
    }

    public String getOriginUrl()
    {
        return originUrl;
    }

    public void setOriginUrl(String originUrl)
    {
        this.originUrl = originUrl;
    }


    @Override
    public String toString()
    {
        return "NineGridBean{" +
                "thumbUrl='" + thumbUrl + '\'' +
                ", originUrl='" + originUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.thumbUrl);
        dest.writeString(this.originUrl);
        dest.writeInt(flags);
    }

    protected NineGridBean(Parcel in)
    {
        this.thumbUrl = in.readString();
        this.originUrl = in.readString();
    }

    public static final Parcelable.Creator<NineGridBean> CREATOR = new Parcelable.Creator<NineGridBean>()
    {
        @Override
        public NineGridBean createFromParcel(Parcel source)
        {
            return new NineGridBean(source);
        }

        @Override
        public NineGridBean[] newArray(int size)
        {
            return new NineGridBean[size];
        }
    };
}
