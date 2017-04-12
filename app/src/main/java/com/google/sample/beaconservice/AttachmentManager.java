package com.google.sample.beaconservice;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.sample.beaconservice.beacon.utils.Attachment;
import com.google.sample.beaconservice.beacon.utils.AttachmentList;
import com.google.sample.libproximitybeacon.ProximityBeacon;
import com.google.sample.libproximitybeacon.ProximityBeaconImpl;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sebastian on 4/11/2017.
 */

public class AttachmentManager {
  private static final String USER_ACCOUNT = "s.restrepo561@gmail.com";
  private static final String BEACON_NAME = "beacons/3!1234abcd1234abcd1234abcd1234abcd";
  private static final String NAMESPACE = "health-care-app-164116";
  private static final String TYPE = "111";

  private Context currentActivity;
  private ProximityBeacon client;
  private AttachmentList currentAttachments;
  private JSONObject testBody;

  public AttachmentManager(Context context){
    currentActivity = context;
    client = new ProximityBeaconImpl(context, USER_ACCOUNT);
    currentAttachments = new AttachmentList();
  }

  /**
   * This attaches an attachment to the predefined beacon.
   * @param type This should be the room number of the patient.
   * @param message This is the attachment associated with the patient.
   */
  public void addAttachment(int type, String message){
    // this is called when a user clicks a button
    Callback createAttachmentCallback = new Callback() {
      @Override
      public void onFailure(Request request, IOException e) {
        logErrorAndToast("Failed request: " + request, e);
      }

      @Override
      public void onResponse(Response response) throws IOException {
        String body = response.body().string();
        if (response.isSuccessful()) {
          try {
            JSONObject json = new JSONObject(body);
            Log.d(Constants.TEST_TAG, "Create Response: " + json.toString());
          } catch (JSONException e) {
            logErrorAndToast("JSONException in building attachment data", e);
          }
        } else {
          logErrorAndToast("Unsuccessful createAttachment request: " + body);
        }
      }
    };

    client.createAttachment(createAttachmentCallback, BEACON_NAME, buildCreateAttachmentJsonBody(Integer.toString(111), "BYE"));

    // before an attachment can be sent, you need to check if there already an attachments associated with that type
    // if there are, then that attachment needs to be deleted and
  }

  public void removeAttachment(String attachmentName){
    // this will remove an attachment from the beacons
    Callback deleteAttachmentCallback = new Callback() {
      @Override
      public void onFailure(Request request, IOException e) {
        logErrorAndToast("Failed request: " + request, e);
      }

      @Override
      public void onResponse(Response response) throws IOException {
        String body = response.body().string();
        if (response.isSuccessful()) {
          Log.d(Constants.TEST_TAG, "Delete response: " + body);
        } else {
          body = response.body().string();
          logErrorAndToast("Unsuccessful deleteAttachment request: " + body);
        }
      }
    };
    client.deleteAttachment(deleteAttachmentCallback, currentAttachments.get(0).getAttachmentName());

  }

  public void updateAttachment(String message){
    fetchAttachments();
    if(currentAttachments == null || currentAttachments.size() != 0){
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
            if(json.length() == 0) {
              return;
            }
            JSONArray attachments = json.getJSONArray("attachments");
            for (int i = 0; i < attachments.length(); i++) {
              JSONObject attachment = attachments.getJSONObject(i);
              currentAttachments.add(new Attachment(attachment));
            }
            printCurrentAttachment(); //for testing
          }catch (JSONException e) {
            Log.e(Constants.TEST_TAG, "JSONException in fetching attachments", e);
          }
        }
      }
    };
    client.listAttachments(listAttachmentsCallback, BEACON_NAME);
  }

  private void printCurrentAttachment(){
    for(int i = 0; i < currentAttachments.size(); i++){
      Log.d(Constants.TEST_TAG, currentAttachments.get(i).getAttachmentName());
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
