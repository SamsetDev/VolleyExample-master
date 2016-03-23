/**
 * Copyright 2013 Mani Selvaraj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.samset.volleyintegration.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom Implementation of com.android.volley.toolbox.JsonObjectRequest to
 * handle the headers.
 * 
 * @author samset
 * 
 */
public class MyJsonRequest extends JsonArrayRequest {

	private Map<String, String> headers = new HashMap<String, String>();
	private Priority priority = null;

	public MyJsonRequest(int method, String url, JSONArray jsonRequest,
						 Listener<JSONArray> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		// TODO Auto-generated constructor stub
	}

	public MyJsonRequest(String url, JSONArray jsonRequest,
						 Listener<JSONArray> listener, ErrorListener errorListener) {
		super(url, jsonRequest, listener, errorListener);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers;
	}

	/*
	 * If prioirty set use it,else returned NORMAL
	 * 
	 * @see com.android.volley.Request#getPriority()
	 */
	@Override
	public Priority getPriority() {
		if (this.priority != null) {
			return priority;
		} else {
			return Priority.NORMAL;
		}
	}

	public void setHeader(String title, String content) {
		headers.put(title, content);
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

}
