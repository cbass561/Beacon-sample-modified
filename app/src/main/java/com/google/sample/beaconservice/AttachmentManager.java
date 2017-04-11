package com.google.sample.beaconservice;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.sample.beaconservice.beacon.utils.Attachment;
import com.google.sample.libproximitybeacon.ProximityBeacon;
import com.google.sample.libproximitybeacon.ProximityBeaconImpl;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Sebastian on 4/11/2017.
 */

public class AttachmentManager {
  private static final String USER_ACCOUNT = "s.restrepo561@gmail.com";
  private static final String BEACON_NAME = "beacons/3!1234abcd1234abcd1234abcd1234abcd";
  private static final String NAMESPACE = "health-care-app-164116";

  private Context currentActivity;
  private ProximityBeacon client;
  private JSONArray currentAttachments;
  private JSONObject testBody;

  public AttachmentManager(Context context){
    currentActivity = context;
    client = new ProximityBeaconImpl(context, USER_ACCOUNT);
    currentAttachments = null;
  }

  public void updateAttachment(String message){
    fetchAttachments();
    if(currentAttachments == null || currentAttachments.length() != 0){
      // there are attachments
      // deleteCurrentAttachments()
    }
    //deleteCurrentAttachment(attachment);
    //sendNewAttachment();
  }

  private void deleteCurrentAttachment(final String attachment){
    Callback deleteAttachmentCallback = new Callback() {
      @Override
      public void onFailure(Request request, IOException e) {
        Log.e(Constants.TEST_TAG, String.format("Failed request: %s, IOException %s", request, e));
      }

      @Override
      public void onResponse(Response response) throws IOException {
        if(response.isSuccessful()){
          // the attachment was removed
        }else {
          String body = response.body().string();
          logErrorAndToast("Unsuccessful deleteAttachment request: " + body);
        }
      }
    };
    client.deleteAttachment(deleteAttachmentCallback, attachment);
  }

  private void fetchAttachments(){
    Callback listAttachmentsCallback = new Callback() {
      @Override
      public void onFailure(Request request, IOException e) {
        logErrorAndToast("Failed request: " + request, e);
      }

      @Override
      public void onResponse(Response response) throws IOException {
        String body = response.body().string();
        if(response.isSuccessful()){
          try{
            JSONObject json = new JSONObject(body);
            currentAttachments = json.getJSONArray("attachments");
            testBody = currentAttachments.getJSONObject(0);
            printCurrentAttachment();
          }catch (JSONException e) {
            Log.e(Constants.TEST_TAG, "JSONException in fetching attachments", e);
          }
        }
      }
    };
    client.listAttachments(listAttachmentsCallback, BEACON_NAME);
  }

  private void printCurrentAttachment(){
    Log.d(Constants.TEST_TAG, currentAttachments.toString());
    Log.d(Constants.TEST_TAG, testBody.toString());
    try {
      Attachment testAttachment = new Attachment(currentAttachments.getJSONObject(0));
      Log.d(Constants.TEST_TAG, "Attachment Worked!");
    }catch (JSONException e){
      Log.e(Constants.TEST_TAG, "Cannot create attachment. Error: " + e);
    }
  }

  private JSONObject buildCreateAttachmentJsonBody(String type, String data) {
    try {
      return new JSONObject().put("namespacedType", NAMESPACE + "/" + type)
              .put("data", Utils.base64Encode(data.getBytes()));
    }
    catch (JSONException e) {
      Log.e(Constants.TEST_TAG, "JSONException", e);
    }
    return null;
  }

  private void logErrorAndToast(String message) {
    Log.e(Constants.TEST_TAG, message);
    toast(message);
  }

  private void logErrorAndToast(String message, Exception e) {
    Log.e(Constants.TEST_TAG, message, e);
    toast(message);
  }

  private void toast(String s) {
    Toast.makeText(currentActivity, s, Toast.LENGTH_LONG).show();
  }

}
