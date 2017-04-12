package com.google.sample.beaconservice;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.sample.beaconservice.beacon.utils.AttachmentType;

import java.util.Timer;
import java.util.TimerTask;

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
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView =  inflater.inflate(R.layout.patient, container, false);
    Button emergency = (Button) rootView.findViewById(R.id.emergency);
    emergency.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        attachmentManager.addAttachment(123, AttachmentType.EMERGENCY.toString());
        Toast.makeText(getActivity(), "Emergency Click", Toast.LENGTH_SHORT).show();
      }
    });

    Button button1 = (Button) rootView.findViewById(R.id.button1);
    button1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        attachmentManager.addAttachment(123, AttachmentType.FOOD.toString());
        Toast.makeText(getActivity(), "Button1 Click", Toast.LENGTH_SHORT).show();
      }
    });

    Button button2 = (Button) rootView.findViewById(R.id.button2);
    button2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        attachmentManager.addAttachment(123, AttachmentType.WATER.toString());
        Toast.makeText(getActivity(), "Button2 Click", Toast.LENGTH_SHORT).show();
      }
    });
    Button button3 = (Button) rootView.findViewById(R.id.button3);
    button3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        attachmentManager.addAttachment(123, AttachmentType.BATHROOM.toString());
        Toast.makeText(getActivity(), "Button3 Click", Toast.LENGTH_SHORT).show();
      }
    });
    Button button4 = (Button) rootView.findViewById(R.id.button4);
    button4.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        attachmentManager.addAttachment(123, AttachmentType.NON_EMERGENCY.toString());
        Toast.makeText(getActivity(), "Button4 Click", Toast.LENGTH_SHORT).show();
      }
    });
    return rootView;
  }
}
