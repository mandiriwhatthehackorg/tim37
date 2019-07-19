package com.essensift.mandirihack.ui;

import android.app.DialogFragment;
import android.content.Context;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.essensift.mandirihack.R;
import com.essensift.mandirihack.engine.NfcHelper;

import java.io.IOException;
import java.nio.charset.Charset;

public class NFCWriteFragment extends DialogFragment {

    public static final String TAG = NFCWriteFragment.class.getSimpleName();

    public static NFCWriteFragment newInstance() {

        return new NFCWriteFragment();
    }

    private TextView mTvMessage;
    private ProgressBar mProgress;
    private NfcHelper.NfcCallback mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_nfc,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onNfcDetected(Ndef ndef, String messageToWrite){

        mProgress.setVisibility(View.VISIBLE);
        writeToNfc(ndef,messageToWrite);
    }

    private void writeToNfc(Ndef ndef, String message){

        mTvMessage.setText("Write in Progress");
        if (ndef != null) {

            try {
                ndef.connect();
                NdefRecord mimeRecord = NdefRecord.createMime("text/plain", message.getBytes(Charset.forName("US-ASCII")));
                ndef.writeNdefMessage(new NdefMessage(mimeRecord));
                ndef.close();
                //Write Successful
                mTvMessage.setText("Write Success");

            } catch (IOException | FormatException e) {
                e.printStackTrace();
                mTvMessage.setText("Write Error");

            } finally {
                mProgress.setVisibility(View.GONE);
            }

        }
    }
}