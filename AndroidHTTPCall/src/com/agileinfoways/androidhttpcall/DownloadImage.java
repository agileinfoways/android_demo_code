package com.agileinfoways.androidhttpcall;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Environment;
import android.util.Log;

public class DownloadImage {

	public boolean DownloadFromUrl(String DownloadUrl, String fileName) {

        try {
            File dir = new File(Environment.getExternalStorageDirectory()
                    + "/XDir");
            dir.mkdir();

            if (dir.exists() == false) {
                dir.mkdirs();
            }

            File file = new File(dir, fileName);

            long startTime = System.currentTimeMillis();
            Log.v("DownloadManager", "download begining");
            Log.v("DownloadManager", "download url:" + DownloadUrl);
            Log.v("DownloadManager", "downloaded file name:" + fileName);

            HttpUriRequest request = new HttpGet(DownloadUrl.trim().replaceAll(" ", "%20").replaceAll("'", "%27"));
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
     
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                byte[] bytes = EntityUtils.toByteArray(entity);
                    
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.flush();
                fos.close();
                
               Log.v("DownloadManager",
                        "download ready in"
                                + ((System.currentTimeMillis() - startTime) / 1000)
                                + " sec");
               
               return true;
            } 
            else
            {
               Log.v("Download failed, HTTP response code ",+ statusCode + " - " + statusLine.getReasonPhrase());
               
               return false;
            }

        } catch (Exception e) 
        {
               Log.e("DownloadManager", "Error: " + e);              
               return false;
        }
        
    }

    // to get file list from sdcard
    public ArrayList<String> getFileNames() {

        ArrayList<String> fileNameArrayList = new ArrayList<String>();
        File sdCardRoot = Environment.getExternalStorageDirectory();
        File yourDir = new File(sdCardRoot, "/XDir");
        yourDir.mkdir();
        for (File f : yourDir.listFiles()) {
            if (f.isFile()) {
                fileNameArrayList.add(f.getName());

            }
        }
        return fileNameArrayList;
    }

}
