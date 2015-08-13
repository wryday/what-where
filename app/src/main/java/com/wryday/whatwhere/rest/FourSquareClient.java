package com.wryday.whatwhere.rest;

import android.content.Context;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class FourSquareClient {
    private static final String TAG = FourSquareClient.class.getSimpleName();

    private static final String BASE_URL = "https://api.foursquare.com/v2/venues/search";

    private FourSquareService mApiService;
    private PinningMode mPinningMode;

    public FourSquareClient(Context context) {
        this(context, PinningMode.DISABLED);
    }

    public FourSquareClient(Context context, PinningMode pinningMode) {
        Log.i(TAG, "client initialized");
        mPinningMode = pinningMode;

        RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder();
        restAdapterBuilder.setLogLevel(RestAdapter.LogLevel.FULL);
        restAdapterBuilder.setEndpoint(BASE_URL);
        restAdapterBuilder.setErrorHandler(new RestErrorHandler());

        setupJsonAdapter(restAdapterBuilder);
        setupHttpClient(restAdapterBuilder);

        RestAdapter restAdapter = restAdapterBuilder.build();
        mApiService = restAdapter.create(FourSquareService.class);
    }

    // register type adapters for custom serializers in the gson builder
    private void setupJsonAdapter(RestAdapter.Builder builder) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        builder.setConverter(new GsonConverter(gson));
    }

    private void setupHttpClient(RestAdapter.Builder builder) {
        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.interceptors.add(new UserAgentInterceptor());

        builder.setClient(new OkClient(okHttpClient));
    }

    public PinningMode getPinningMode() {
        return mPinningMode;
    }

    public FourSquareService getApiService() {
        return mApiService;
    }

    public enum PinningMode {
        DISABLED,
        ENABLED_EVERYWHERE
    }

    private static class ItemTypeAdapterFactory implements TypeAdapterFactory {

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
            final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

            return new TypeAdapter<T>() {
                @Override
                public void write(JsonWriter out, T value) throws IOException {
                    delegate.write(out, value);
                }

                @Override
                public T read(JsonReader in) throws IOException {
                    JsonElement jsonElement = elementAdapter.read(in);

                    if (jsonElement.isJsonObject()) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();

                        if (jsonObject.has("objects") && jsonObject.get("objects").isJsonArray()) {
                            jsonElement = jsonObject.get("objects");
                        }
                    }

                    return delegate.fromJsonTree(jsonElement);
                }
            }.nullSafe();
        }
    }
}
