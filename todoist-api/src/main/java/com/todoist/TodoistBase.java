/*
 * Copyright 2010 Original Author(s)
 * 
 * This file is part of Kommando
 * 
 * Kommando is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Kommando is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Kommando.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.todoist;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.CustomDeserializerFactory;
import org.codehaus.jackson.map.deser.StdDeserializerProvider;
import org.codehaus.jackson.map.introspect.VisibilityChecker;
import org.codehaus.jackson.type.TypeReference;

import com.todoist.Item.Priority;
import com.todoist.json.ColorDeserializer;
import com.todoist.json.Item_PriorityDeserializer;
import com.todoist.json.TimeZoneDeserializer;

public abstract class TodoistBase {

    protected ObjectMapper objectMapper = new ObjectMapper() {
        {
            setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(Visibility.ANY));
            setDeserializerProvider(new StdDeserializerProvider(new CustomDeserializerFactory() {
                {
                    addSpecificMapping(Color.class, new ColorDeserializer());
                    addSpecificMapping(TimeZone.class, new TimeZoneDeserializer());
                    addSpecificMapping(Priority.class, new Item_PriorityDeserializer());
                }
            }));
        }
    };

    private HttpClient client = new DefaultHttpClient() {
        {
            // proxy configuration
            getCredentialsProvider().setCredentials(new AuthScope("proxy", -1), new UsernamePasswordCredentials("exb896", "iamvigo5"));
            getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost("proxy", 80, "http"));
        }
    };

    protected void execute(HttpGet request) {
        try {
            HttpResponse response = client.execute(request);
            if (response.getEntity() != null) {
                response.getEntity().consumeContent();
            }
        } catch (ClientProtocolException e) {
            throw new TodoistException(e);
        } catch (IOException e) {
            throw new TodoistException(e);
        }
    }

    protected <T> T execute(HttpGet request, final TypeReference<T> typeReference) {
        try {
            ResponseHandler<T> handler = new ResponseHandler<T>() {
                public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    String answer = EntityUtils.toString(response.getEntity());
                    System.out.println("response: " + answer);

                    if (response.getStatusLine().getStatusCode() != 200) {
                        throw new TodoistException(response.getStatusLine().getReasonPhrase());
                    }

                    if (Pattern.matches("\".*\"", answer)) {
                        throw new TodoistException(answer);
                    }

                    return objectMapper.readValue(answer, typeReference);
                }
            };

            return client.execute(request, handler);
        } catch (ClientProtocolException e) {
            throw new TodoistException(e);
        } catch (IOException e) {
            throw new TodoistException(e);
        }
    }

    protected <T> T execute(HttpGet request, final Class<T> resultType) {
        return execute(request, new TypeReference<T>() {
            @Override
            public Type getType() {
                return resultType;
            }
        });
    }

    // create a request for the given command and parameters
    protected HttpGet request(String command, Map<String, Object> parameters) {
        return request(command, parameters, false);
    }

    protected HttpGet request(String command, Map<String, Object> parameters, boolean secure) {
        List<NameValuePair> qparameters = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            Object value = entry.getValue();
            String paramValue = value == null ? null : value.toString();
            qparameters.add(new BasicNameValuePair(entry.getKey(), paramValue));
        }

        try {
            String scheme = secure ? "https" : "http";
            int port = secure ? 443 : 80;

            return new HttpGet(URIUtils.createURI(scheme, "todoist.com", port, "/API/" + command, URLEncodedUtils.format(qparameters, "UTF-8"), null));
        } catch (URISyntaxException e) {
            throw new TodoistException(e);
        }
    }

    protected String asJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonGenerationException e) {
            throw new TodoistException(e);
        } catch (JsonMappingException e) {
            throw new TodoistException(e);
        } catch (IOException e) {
            throw new TodoistException(e);
        }
    }

}
