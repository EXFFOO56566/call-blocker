package com.call.calllog.blocklist.object;

/**
 * Created by jksol3 on 3/3/17.
 */

public class AppData {

    public int app_icon;
    public String app_name;
    public String app_desc;
    public String package_name;

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public int getApp_icon() {
        return app_icon;
    }

    public void setApp_icon(int app_icon) {
        this.app_icon = app_icon;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_desc() {
        return app_desc;
    }

    public void setApp_desc(String app_desc) {
        this.app_desc = app_desc;
    }
}
