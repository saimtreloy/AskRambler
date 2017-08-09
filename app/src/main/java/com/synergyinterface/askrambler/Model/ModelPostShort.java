package com.synergyinterface.askrambler.Model;

/**
 * Created by Android on 8/9/2017.
 */

public class ModelPostShort {
    public String ads_id, to_where, to_date, user_photo;

    public ModelPostShort(String ads_id, String to_where, String to_date, String user_photo) {
        this.ads_id = ads_id;
        this.to_where = to_where;
        this.to_date = to_date;
        this.user_photo = user_photo;
    }


    public String getAds_id() {
        return ads_id;
    }

    public String getTo_where() {
        return to_where;
    }

    public String getTo_date() {
        return to_date;
    }

    public String getUser_photo() {
        return user_photo;
    }
}
