package com.yunmel.commons.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.yunmel.commons.model.HttpResult;

@Service
public class ApiService {
  @Autowired(required = false)
  private CloseableHttpClient httpclient;

  @Autowired(required = false)
  private RequestConfig requestConfig;

  public String doGet(String url) throws URISyntaxException, ClientProtocolException, IOException {
    return doGet(url, null);
  }

  public String doGet(String url, Map<String, Object> params)
      throws URISyntaxException, ClientProtocolException, IOException {
    URI uri = null;
    if (params != null) {
      URIBuilder builder = new URIBuilder(url);
      for (Map.Entry<String, Object> entry : params.entrySet()) {
        builder.addParameter(entry.getKey(), (String) entry.getValue());
      }
      uri = builder.build();
    }

    HttpGet httpGet = null;
    if (uri != null)
      httpGet = new HttpGet(uri);
    else {
      httpGet = new HttpGet(url);
    }

    httpGet.setConfig(this.requestConfig);

    CloseableHttpResponse response = null;
    try {
      response = this.httpclient.execute(httpGet);

      if (response.getStatusLine().getStatusCode() == 200) {
        String str = EntityUtils.toString(response.getEntity(), "UTF-8");
        return str;
      }
    } finally {
      if (response != null) {
        response.close();
      }
    }
    return null;
  }

  public HttpResult doPost(String url, Map<String, Object> params)
      throws ClientProtocolException, IOException {
    HttpPost httpPost = new HttpPost(url);
    httpPost.setConfig(this.requestConfig);
    if (params != null) {
      List<NameValuePair> parameters = Lists.newArrayList();
      for (Map.Entry<String, Object> entry : params.entrySet()) {
        parameters.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
      }
      UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
      httpPost.setEntity(urlEncodedFormEntity);
    }
    CloseableHttpResponse response = null;
    try {
      response = this.httpclient.execute(httpPost);
      HttpResult httpResult = new HttpResult();
      httpResult.setCode(response.getStatusLine().getStatusCode());

      if (response.getStatusLine().getStatusCode() == 200) {
        httpResult.setContent(EntityUtils.toString(response.getEntity(), "UTF-8"));
      }
      return httpResult;
    } finally {
      if (response != null)
        response.close();
    }
  }

  public HttpResult doPostJson(String url, String json)
      throws ClientProtocolException, IOException {
    HttpPost httpPost = new HttpPost(url);
    httpPost.setConfig(this.requestConfig);
    if (json != null) {
      StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
      httpPost.setEntity(stringEntity);
    }
    CloseableHttpResponse response = null;
    try {
      response = this.httpclient.execute(httpPost);
      HttpResult httpResult = new HttpResult();
      httpResult.setCode(Integer.valueOf(response.getStatusLine().getStatusCode()));

      if (response.getStatusLine().getStatusCode() == 200) {
        httpResult.setContent(EntityUtils.toString(response.getEntity(), "UTF-8"));
      }
      return httpResult;
    } finally {
      if (response != null)
        response.close();
    }
  }

}
