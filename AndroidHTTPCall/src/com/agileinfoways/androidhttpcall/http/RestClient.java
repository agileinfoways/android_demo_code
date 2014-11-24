package com.agileinfoways.androidhttpcall.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.util.Log;
import android.webkit.URLUtil;

import com.agileinfoways.androidhttpcall.Consts;

/**
 * This is RestClient Class it is Handle all network HttpConnection with HttpDefaultClient  
 * 
 */
public class RestClient
{
	
	public final String TAG = getClass().getSimpleName(); 

	/** Its a key name to upload user image */
	public final String USER_IMAGE = "timage"; 
	
	/** Its a key name to upload user file */
	public final String USER_FILE= "file"; 

	public static final int CONNECTION_TIMEOUT= 20* 1000;
	
	public static final int SOCKET_TIMEOUT= 30* 1000;
	
	public enum RequestMethod 
	{
		DELETE, GET, POST, PUT
	}
	
	public static HttpResponse mHttpResponse;
	
	public static long HttpResponseTime;
	
	public boolean authentication;
	public ArrayList<NameValuePair> headers;

	public String jsonBody;
	public String message;

	public ArrayList<NameValuePair> params;
	public String response;
	public int responseCode;

	public String url;

	// HTTP Basic Authentication
	public String username;
	public String password;

	protected Context context;

	public RestClient(String url) {
		this.url = url;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}
	
	//Be warned that this is sent in clear text, don't use basic auth unless you have to.
	public void addBasicAuthentication(String user, String pass) {
		authentication = true;
		username = user;
		password = pass;
	}

	public void addHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public void addParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public String execute(RequestMethod method) throws Exception
	{
		
		String Response="";
		try
		{
		if(URLUtil.isValidUrl(url))
		{
			switch (method) {
				case GET: {
					HttpGet request = new HttpGet(url + addGetParams());
					request = (HttpGet) addHeaderParams(request);
					Response =	executeRequest(request, url);
					break;
				}
				case POST: {
					HttpPost request = new HttpPost(url);
					request = (HttpPost) addHeaderParams(request);
					request = (HttpPost) addBodyParams(request);
					Response = executeRequest(request, url);
					break;
				}
				case PUT: {
					HttpPut request = new HttpPut(url);
					request = (HttpPut) addHeaderParams(request);
					request = (HttpPut) addBodyParams(request);
					Response =	executeRequest(request, url);
					break;
				}
				case DELETE: {
					HttpDelete request = new HttpDelete(url);
					request = (HttpDelete) addHeaderParams(request);
					Response =	executeRequest(request, url);
					break;
				}
			}
		  }
		  else
		  {
			  if(Consts.IS_DEBUG)
				{
			       Log.e(TAG, "Your Url is invalid");
				}
		  }
		}
		catch (Exception e) {
			// TODO: handle exception
			if(Consts.IS_DEBUG)
			{
				e.printStackTrace();
			}
		}
		return Response;
	}

	public HttpUriRequest addHeaderParams(HttpUriRequest request)
			throws Exception {
		for (NameValuePair h : headers) {
			request.addHeader(h.getName(), h.getValue());
		}

		if (authentication) {

			UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
					username, password);
			request.addHeader(new BasicScheme().authenticate(creds, request));
		}

		return request;
	}

	public HttpUriRequest addBodyParams(HttpUriRequest request)
			throws Exception {
		if (jsonBody != null) {
			request.addHeader("Content-Type", "application/json");
			if (request instanceof HttpPost)
				((HttpPost) request).setEntity(new StringEntity(jsonBody,
						"UTF-8"));
			else if (request instanceof HttpPut)
				((HttpPut) request).setEntity(new StringEntity(jsonBody,
						"UTF-8"));

		} else if (!params.isEmpty()) {
			if (request instanceof HttpPost)
				((HttpPost) request).setEntity(new UrlEncodedFormEntity(params,
						HTTP.UTF_8));
			else if (request instanceof HttpPut)
				((HttpPut) request).setEntity(new UrlEncodedFormEntity(params,
						HTTP.UTF_8));
		}
		return request;
	}

	public String addGetParams() throws Exception {
		//Using StringBuffer append for better performance.
		StringBuffer combinedParams = new StringBuffer();
		if (!params.isEmpty()) {
			combinedParams.append("?");
			for (NameValuePair p : params) {
				combinedParams.append((combinedParams.length() > 1 ? "&" : "")
						+ p.getName() + "="
						+ URLEncoder.encode(p.getValue(), "UTF-8"));
			}
		}
		return combinedParams.toString();
	}

	public String getErrorMessage() {
		return message;
	}

	public String getResponse() {
		return response;
	}
	
	public static HttpResponse getHttpResponse()
	{
		return mHttpResponse;
	}
	
	public static long getHttpResponseTime()
	{
		return HttpResponseTime;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setContext(Context ctx) {
		context = ctx;
	}

	public void setJSONString(String data) {
		jsonBody = data;
	}

	public String executeRequest(HttpUriRequest request, String url) {

		DefaultHttpClient client = new DefaultHttpClient();
		HttpParams params = client.getParams();

		if(Consts.IS_DEBUG)
		{			
			Log.i(Consts.TAG, "Url: "+url);
			
			
				Log.i(Consts.TAG, "MEthod : "+request.getMethod()+" Params : "+request.getParams());
				
		}
		
		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);

		try {
			
			client.addRequestInterceptor(new HttpRequestInterceptor() {

                public void process(
                        final HttpRequest request,
                        final HttpContext context) throws HttpException, IOException {
                	               	
                //	request.setHeader("User-Agent", Consts.TJS_APP_VERSION);
                    if (!request.containsHeader("Accept-Encoding")) {
                        request.addHeader("Accept-Encoding", "gzip");
                        
                    }
                }

            });

            client.addResponseInterceptor(new HttpResponseInterceptor() {

                public void process(
                        final HttpResponse response,
                        final HttpContext context) throws HttpException, IOException {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        Header ceheader = entity.getContentEncoding();
                        if (ceheader != null) {
                            HeaderElement[] codecs = ceheader.getElements();
                            for (int i = 0; i < codecs.length; i++) {
                                if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                                    response.setEntity(
                                            new GzipDecompressingEntity(response.getEntity()));
                                    return;
                                }
                            }
                        }
                    }
                }

            });
		
            
			long startTime = System.currentTimeMillis();
			
			mHttpResponse = client.execute(request);
			responseCode = mHttpResponse.getStatusLine().getStatusCode();
					
			message = mHttpResponse.getStatusLine().getReasonPhrase();

			HttpEntity entity = mHttpResponse.getEntity();

			if (entity != null) {

				InputStream instream = entity.getContent();
				response = convertStreamToString(instream);

			 // Closing the input stream will trigger connection release
				instream.close();
			}			
			long endTime = System.currentTimeMillis();
			
			HttpResponseTime = endTime-startTime;
			
			if(Consts.IS_DEBUG)
			{ 
				Log.i(Consts.TAG, "Response Code :"+responseCode);
				Log.i(Consts.TAG, "Response :"+response);			   
			}

		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}finally
		{
			client.getConnectionManager().shutdown();
		}
		return response;
	}

	public static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	
    /**
     * getResponse(String URL, ByteArrayBody image, HashMap<String, String>
     * hashmap_key_value) Single image to upload on server with other data
     *
     * @param image - ByteArrayBody image type
     * @return String - response from server
     * @throws Exception
     */

    public String postImage(ByteArrayBody image,LinkedHashMap<String, String> hashmap_key_value) throws Exception {


      DefaultHttpClient httpClient = new DefaultHttpClient();
	
       HttpPost httpPost = new HttpPost(url);
       
        try {

            // This will add image as multipart entity to httpPost
            MultipartEntity reqEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);

            if (image != null) {
                
            // USER_IMAGE will be the folder name to upload on server
                reqEntity.addPart(USER_IMAGE, image);
            }

            // Using iterator, all values fetched from HASHMAP

            Iterator<String> myVeryOwnIterator = hashmap_key_value.keySet()
                    .iterator();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                String value = (String) hashmap_key_value.get(key);
                
            	if(Consts.IS_DEBUG)
    			{ 
                   System.out.println("Key: " + key + " Value: " + value);
                }
                // Other Data added too, value converted to StringBody to
                // support Multipart Body functionality
                reqEntity.addPart(key, new StringBody(value));
            }

            httpPost.setEntity(reqEntity);
        	long startTime = System.currentTimeMillis();
    		
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = httpClient.execute(httpPost, responseHandler);
            
            long endTime = System.currentTimeMillis();
			
			HttpResponseTime = endTime-startTime;
			
        } catch (ClientProtocolException e) {
        	httpClient.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			httpClient.getConnectionManager().shutdown();
			e.printStackTrace();
		}finally
		{
			httpClient.getConnectionManager().shutdown();
		}
        // Return response string to calling function
        return response;
    }

    /**
     * Number of images can upload using HASHMAP of images, here HashMap<String,
     * ByteArrayBody> hasjmap_imgKey_imgBody is used : key will be the folder
     * name of third part server where image will be uploaded.
     * hashmap_key_value: key will be the parameter of API and value of that
     * parameter
     *
     * @param hasjmap_imgKey_imgBody
     * @param hashmap_key_value
     * @return Response in String format
     * @throws Exception
     */

    public String postImage(  LinkedHashMap<String, ByteArrayBody> hasjmap_imgKey_imgBody,
    		LinkedHashMap<String, String> hashmap_key_value) throws Exception {

    	HttpClient httpClient = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost(url);
       
        try {

            // This will add image as multipart entity to httpPost
            MultipartEntity reqEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);

            // Using iterator, all values fetched from HASHMAP
            Iterator<String> myImageIterator = hasjmap_imgKey_imgBody.keySet()
                    .iterator();
            while (myImageIterator.hasNext()) {
                String key = (String) myImageIterator.next();
                ByteArrayBody value = (ByteArrayBody) hasjmap_imgKey_imgBody
                        .get(key);
                
            	if(Consts.IS_DEBUG)
    			{ 
                  Log.i(TAG,"Key: (image upload folder) " + key
                        + " Value: (ByteArrayBody) " + value);
                }
                reqEntity.addPart(key, value);
            }

            // Using iterator, all values fetched from HASHMAP

            Iterator<String> myVeryOwnIterator = hashmap_key_value.keySet()
                    .iterator();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                String value = (String) hashmap_key_value.get(key);
                
            	if(Consts.IS_DEBUG)
    			{ 
                	Log.i(TAG,"Key: " + key + " Value: " + value);
                }
                // Other Data added too, value converted to StringBody to
                // support Multipart Body functionality
                reqEntity.addPart(key, new StringBody(value));
            }

        	long startTime = System.currentTimeMillis();
    		
            httpPost.setEntity(reqEntity);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = httpClient.execute(httpPost, responseHandler);
            	
            long endTime = System.currentTimeMillis();
			
			HttpResponseTime = endTime-startTime;
			
        } catch (ClientProtocolException e) {
        	httpClient.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			httpClient.getConnectionManager().shutdown();
			e.printStackTrace();
		}finally
		{
			httpClient.getConnectionManager().shutdown();
		}
        // Return response string to calling function
        return response;
    }
    
    
    
   
   
    public String postFile(File myFile,LinkedHashMap<String, String> hashmap_key_value) throws Exception {

      DefaultHttpClient httpClient = new DefaultHttpClient();
	
       HttpPost httpPost = new HttpPost(url);
       
        try {

            // This will add image as multipart entity to httpPost
            MultipartEntity reqEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);

            if (myFile != null) {
                
              // USER_FILE will be the file name to upload on server
                
                FileBody filebodyVideo = new FileBody(myFile);
                reqEntity.addPart(USER_FILE, filebodyVideo);
                reqEntity.addPart("title", new StringBody("title"));
                reqEntity.addPart("description",  new StringBody("desc"));
            }

            // Using iterator, all values fetched from HASHMAP

            Iterator<String> myVeryOwnIterator = hashmap_key_value.keySet()
                    .iterator();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                String value = (String) hashmap_key_value.get(key);
                
            	if(Consts.IS_DEBUG)
    			{ 
                   System.out.println("Key: " + key + " Value: " + value);
                }
                // Other Data added too, value converted to StringBody to
                // support Multipart Body functionality
                reqEntity.addPart(key, new StringBody(value));
            }

            httpPost.setEntity(reqEntity);
        	long startTime = System.currentTimeMillis();
    		
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = httpClient.execute(httpPost, responseHandler);
            
            long endTime = System.currentTimeMillis();
			
			HttpResponseTime = endTime-startTime;
			
        } catch (ClientProtocolException e) {
        	httpClient.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			httpClient.getConnectionManager().shutdown();
			e.printStackTrace();
		}finally
		{
			httpClient.getConnectionManager().shutdown();
		}
        // Return response string to calling function
        return response;
    }
   
    
    static class GzipDecompressingEntity extends HttpEntityWrapper {
        public GzipDecompressingEntity(final HttpEntity entity) {
           super(entity);
        }

        @Override
        public InputStream getContent() throws IOException, IllegalStateException {
           // the wrapped entity's getContent() decides about repeatability
           InputStream wrappedin = wrappedEntity.getContent();
           return new GZIPInputStream(wrappedin);
        }

        @Override
        public long getContentLength() {
           // length of ungzipped content is not known
           return -1;
        }
	}
}
