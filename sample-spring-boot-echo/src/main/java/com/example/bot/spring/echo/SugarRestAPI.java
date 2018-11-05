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

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class SugarRestAPI {
    private static String restApiKey = "";

    /*　参考：http://www.techscore.com/blog/2016/09/20/jersey-client-api/　*/
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

    public static void setRestApiKey(String restApiKey) {
        SugarRestAPI.restApiKey = restApiKey;
    }
}
