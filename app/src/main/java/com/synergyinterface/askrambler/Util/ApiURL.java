package com.synergyinterface.askrambler.Util;

/**
 * Created by sam on 8/5/17.
 */

public class ApiURL {

    public static String getLatLongFromLocation = "http://maps.google.com/maps/api/geocode/json?address=";

    public static String getAllPost = "http://askrambler.com/Android_API/getAllPost.php";
    public static String getAllPostShort = "http://askrambler.com/Android_API/getAllPostShort.php";
    public static String getAllPostDetail = "http://askrambler.com/Android_API/getPostDetail.php?post_id=";
    public static String getLogin = "http://askrambler.com/Android_API/login.php";

    //Advanced Search API Link
    public static String searchCompanion = "http://askrambler.com/Android_API/searchCompanion.php";
    public static String searchBaggage = "http://askrambler.com/Android_API/searchBaggage.php";
    public static String searchHost = "http://askrambler.com/Android_API/searchHost.php";
    public static String searchTrip = "http://askrambler.com/Android_API/searchTrip.php";

    //Country List
    public static String countryList = "http://askrambler.com/Android_API/countryList.php";

}
