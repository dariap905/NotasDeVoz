package com.example.notasdevoz.ui.main;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notasdevoz.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    private ImageView logo;
    private FloatingActionButton record;
    private FloatingActionButton stop;
    private FloatingActionButton play;
    private String fileName;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        logo = view.findViewById(R.id.logo);
        record = view.findViewById(R.id.record);
        stop = view.findViewById(R.id.stop);
        play = view.findViewById(R.id.play);

        String ruta = getContext().getExternalFilesDir(null).getAbsolutePath();
        fileName = ruta + "/audiorecord.3gp";

        record.setOnClickListener(v -> {
            logo.setImageDrawable(getResources().getDrawable(R.drawable.grabando));
        });

        stop.setOnClickListener(v -> {
            logo.setImageDrawable(getResources().getDrawable(R.drawable.espera));
        });

        play.setOnClickListener(v -> {
            logo.setImageDrawable(getResources().getDrawable(R.drawable.reproduciendo));
        });

        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        return view;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) {
            Toast.makeText(
                    getContext(),
                    "Permission needed",
                    Toast.LENGTH_LONG
            ).show();
            getActivity().finish();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

}