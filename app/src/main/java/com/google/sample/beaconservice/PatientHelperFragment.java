package com.google.sample.beaconservice;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientHelperFragment extends Fragment {
  AttachmentManager attachmentManager;

  public PatientHelperFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    attachmentManager = new AttachmentManager(getActivity());
    attachmentManager.updateAttachment("test");
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_patient_helper, container, false);
  }

  private void updateBeaconAttachment(){

  }

}
