package com.example.dardev.collegekart.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dardev.collegekart.R;

/**
 * Created by samarth on 24/12/15.
 */
public class GuestFragment extends Fragment {
    public GuestFragment()
    {}
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.guest_fragment, container, false);
    }
}
