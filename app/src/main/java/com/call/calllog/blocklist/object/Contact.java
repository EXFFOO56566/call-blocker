package com.call.calllog.blocklist.object;

/**
 * Created by jksol3 on 22/2/17.
 */

public class Contact {

    public String name;
    public String number;
    public boolean isChecked = false;
    public String time;
    public String type = "all";
    public String Contact_image;

    public String getIsName() {
        return isName;
    }

    public void setIsName(String isName) {
        this.isName = isName;
    }

    public String isName;


    public String getContact_image() {
        return Contact_image;
    }

    public void setContact_image(String contact_image) {
        Contact_image = contact_image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;

        if (object != null && object instanceof Contact) {
            sameSame = number.equals(((Contact) object).number);
        }

        return sameSame;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
