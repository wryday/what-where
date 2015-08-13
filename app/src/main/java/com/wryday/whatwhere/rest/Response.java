package com.wryday.whatwhere.rest;

import com.wryday.whatwhere.Venue;

import java.util.List;

public class Response {

    private List<Venue> mVenues;

    public List<Venue> getVenues() {
        return mVenues;
    }

    public void setVenues(List<Venue> venues) {
        mVenues = venues;
    }
}
