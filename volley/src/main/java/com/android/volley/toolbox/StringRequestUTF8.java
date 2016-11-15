package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import java.io.UnsupportedEncodingException;

/**
 * Created by Lenovo on 2016/3/31.
 */
public class StringRequestUTF8 extends StringRequest{
    public StringRequestUTF8(int method, String url, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public StringRequestUTF8(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
    }

    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed = null;
        try {
            parsed = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}
