

package com.samset.volleyintegration.network;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

/**
 * Created by samset on 16/09/15.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson mGson;
    private final Class<T> mClazz;
    private final Listener<T> mListener;
    /** Charset for request. */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    public GsonRequest(int method,
                       String url,
                       Class<T> clazz,
                       Listener<T> listener,
                       ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.mClazz = clazz;
        this.mListener = listener;
        mGson = new Gson();


    }


    public GsonRequest(int method,
                       String url,
                       Class<T> clazz,
                       Listener<T> listener,
                       ErrorListener errorListener,
                       Gson gson) {
        super(Method.GET, url, errorListener);
        this.mClazz = clazz;
        this.mListener = listener;
        mGson = gson;
    }



	@Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
        if (cacheEntry == null) {
            cacheEntry = new Cache.Entry();
        }
        final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
        final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
        long now = System.currentTimeMillis();
        final long softExpire = now + cacheHitButRefreshed;
        final long ttl = now + cacheExpired;
        cacheEntry.data = response.data;
        cacheEntry.softTtl = softExpire;
        cacheEntry.ttl = ttl;
        String headerValue;
        headerValue = response.headers.get("Date");
        if (headerValue != null) {
            cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }
        headerValue = response.headers.get("Last-Modified");
        if (headerValue != null) {
            cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }
        cacheEntry.responseHeaders = response.headers;
        try {
            String json = new String(cacheEntry.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(json, mClazz),cacheEntry);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }

    }}




    //If you not maitain cache use below method


/*
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            //response.headers.put(HTTP.CONTENT_TYPE,
            response.headers.get("content-type");
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }}

    */