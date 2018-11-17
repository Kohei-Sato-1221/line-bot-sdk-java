/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.example.bot.spring.echo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SugarRestAPI {
    private static String restApiKey = "sugarsugarsugar1221";
    private static String accoutBookUrl = "/macros/s/AKfycbxzIFcv8BScGoMdeke23HP2P8L4r1o5NO4HVLmkqQ0OKO9uMng/";
    
    /*　参考：http://www.techscore.com/blog/2016/09/20/jersey-client-api/　*/
    /*
    public static String get(String expense, String remark) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://script.google.com")
                .path("/macros/s/AKfycbxzIFcv8BScGoMdeke23HP2P8L4r1o5NO4HVLmkqQ0OKO9uMng/exec")
                .queryParam("key", restApiKey)
                .queryParam("expense", expense)
                .queryParam("remark", remark);

            String result;
            try {
                result = target.request().get(String.class);
            } catch (BadRequestException e) {
                result = "error: response=" + e.getResponse().readEntity(String.class);
                System.out.println("error: response=" + e.getResponse().readEntity(String.class));
                throw e;
            }
        return result;
    }
    */

    public static String addAccountData(String expense, String remark) {
        StringBuilder urlSb = new StringBuilder();
        urlSb.append("https://script.google.com");
        urlSb.append(accoutBookUrl + "exec?");
        urlSb.append("key=" + restApiKey + "&");
        urlSb.append("expense=" + expense + "&");
        urlSb.append("remark=" + remark);
        Charset charset = StandardCharsets.UTF_8;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String urlStr = urlSb.toString();
        HttpGet request = new HttpGet(urlStr);

        CloseableHttpResponse response = null;
        String responseData = "Error!!";

        try {
            response = httpclient.execute(request);
            int status = response.getStatusLine().getStatusCode();

            if (status == HttpStatus.SC_OK) {
                responseData = "#" + urlStr + ":"  + EntityUtils.toString(response.getEntity(),charset);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseData;
    }

    public static void setRestApiKey(String restApiKey) {
        SugarRestAPI.restApiKey = restApiKey;
    }
}
