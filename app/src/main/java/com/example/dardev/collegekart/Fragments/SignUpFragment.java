package com.example.dardev.collegekart.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.dardev.collegekart.MainActivity;
import com.example.dardev.collegekart.R;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Firebase ref;

    private RadioGroup rgYear, rgBranch;
    private EditText firstName, lastName, mobile, email, password, passwordRe;
    private String sFName, sLName, sMob, sEm, sPass, sPassRe, sYear, sBranch;
    private Button signupButton;
    private String year, branch;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.signup_fragment, container, false);
        rgYear = (RadioGroup) rootView.findViewById(R.id.rg_year);
         year= ((RadioButton) rootView.findViewById(rgYear.getCheckedRadioButtonId())).getText().toString();

        rgBranch = (RadioGroup)  rootView.findViewById(R.id.rg_branch);
       branch = ((RadioButton) rootView.findViewById(rgBranch.getCheckedRadioButtonId())).getText().toString();

        firstName= (EditText)  rootView.findViewById(R.id.input_firstName);
        lastName= (EditText)  rootView.findViewById(R.id.input_lastName);
        mobile=(EditText)  rootView.findViewById(R.id.input_number);
        email= (EditText) rootView.findViewById(R.id.input_email);
        password =(EditText)  rootView.findViewById(R.id.input_password);
        passwordRe= (EditText) rootView.findViewById(R.id.input_password_reenter);
        signupButton= (Button) rootView.findViewById(R.id.btn_signup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).register(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString(), year,branch,mobile.getText().toString());
            }
        });




        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {

        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
