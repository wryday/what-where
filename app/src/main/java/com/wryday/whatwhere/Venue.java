package com.wryday.whatwhere;

import java.io.Serializable;

public class Venue implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mId;
    private String mName;
    private Location mLocation;
    private Contact mContact;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public Contact getContact() {
        return mContact;
    }

    public void setContact(Contact contact) {
        mContact = contact;
    }
}
