package com.google.sample.beaconservice.beacon.utils;

import android.util.Log;

import com.google.sample.beaconservice.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sebastian on 4/11/2017.
 */

public class Attachment {
  private String attachmentName;
  private String namespace;
  private int type;
  private String data;
  private String creationTime;

  public Attachment(JSONObject attachmentInfo) {
    try {
      attachmentName = attachmentInfo.getString("attachmentName");
      String[] namespacedType = attachmentInfo.getString("namespacedType").split("/");
      namespace = namespacedType[0];
      type = Integer.parseInt(namespacedType[1]);
      data = attachmentInfo.getString("data");
    } catch (JSONException e) {
      // something bad happened
      Log.e(Constants.TEST_TAG, "Cannot create attachment. Error: " + e);

    }
  }
  public String getAttachmentName() {
    return attachmentName;
  }

  public String getNamespace() {
    return namespace;
  }

  public int getAttachmentType() {
    return type;
  }

  public String getAttachmentData() {
    return data;
  }

}
