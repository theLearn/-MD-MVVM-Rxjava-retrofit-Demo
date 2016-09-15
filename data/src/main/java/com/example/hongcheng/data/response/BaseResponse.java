package com.example.hongcheng.data.response;


/**
 * Created by hongcheng on 16/3/30.
 */
public class BaseResponse{
    protected int status;
    protected String description;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
