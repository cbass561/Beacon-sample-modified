package com.google.sample.beaconservice.beacon.utils;

import java.util.ArrayList;

/**
 * Created by Sebastian on 4/11/2017.
 */

public class AttachmentList{
  ArrayList<Attachment> attachments;

  public AttachmentList(){
    attachments = new ArrayList<Attachment>();
  }

  public Attachment get(int index){
    return attachments.get(index);
  }
  /**
   * Adds the attachment to the list.
   * @param attachment The attachment that will be added to the list.
   */
  public void add(Attachment attachment){
    attachments.add(attachment);
  }

  /**
   * Removes an attachment from the list.
   * @param index The index of the attachment to be removed.
   */
  public void remove(int index){
    if(index >= attachments.size()){
      return;
    }
    attachments.remove(index);
  }

  /**
   * Removes the attachment from the list
   * @param attachment The attachment to be removed;
   */
  public void remove(Attachment attachment){
    // ugly but want to make sure it deletes the correct attachment. I dont trust the default remove() function
    for(int i =0; i <= attachments.size(); i++){
      Attachment curAttachment = attachments.get(i);
      if(curAttachment.getAttachmentName().equals(attachment.getAttachmentName())){
        attachments.remove(i);
      }
    }
  }

  /**
   * Checks whether the list of attachments contains the type already
   * @param roomNumber The room number assocaited with the patient
   * @return
   */
  public boolean doesRoomHaveAttachment(int roomNumber){
    for (Attachment attachment: attachments){
      if(attachment.getRoomNumber() == roomNumber){
        return true;
      }
    }
    return false;
  }

  public int size(){
    return attachments.size();
  }
}
