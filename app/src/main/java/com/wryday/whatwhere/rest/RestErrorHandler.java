package com.wryday.whatwhere.rest;

import android.util.Log;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

public class RestErrorHandler implements ErrorHandler {
    private static final String TAG = RestErrorHandler.class.getSimpleName();

    @Override
    public Throwable handleError(RetrofitError cause) {
        Log.e(TAG, "Handling an error. Cause: " + cause.getMessage());
        return null;
    }
}
