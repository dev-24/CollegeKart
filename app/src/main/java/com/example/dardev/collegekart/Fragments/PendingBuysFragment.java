package com.example.dardev.collegekart.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dardev.collegekart.R;
import com.example.dardev.collegekart.ViewAdActivity;
import com.example.dardev.collegekart.model.Ad;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PendingBuysFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PendingBuysFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PendingBuysFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView setting_list;
    private ArrayList<Ad> options;
    private OptionsAdapter optionsAdapter;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PendingBuysFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PendingBuysFragment newInstance(String param1, String param2) {
        PendingBuysFragment fragment = new PendingBuysFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PendingBuysFragment() {
        // Required empty public constructor
    }

    class Holder{
        TextView title;
        TextView desc;
        TextView price;
        ImageView icon;
    }

    class OptionsAdapter extends BaseAdapter
    {
        public OptionsAdapter() {
        }

        @Override
        public int getCount() {
            return options.size();
        }

        @Override
        public Object getItem(int position) {
            return options.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder;
            if(convertView==null)
            {
                LayoutInflater li = getActivity().getLayoutInflater();
                convertView = li.inflate(R.layout.ads_list_item, null);
                holder=new Holder();
                holder.title = (TextView) convertView.findViewById(R.id.txt_title);
                holder.desc = (TextView) convertView.findViewById(R.id.txt_desc);
                holder.icon = (ImageView) convertView.findViewById(R.id.optionIcon);
                holder.price = (TextView) convertView.findViewById(R.id.txt_price);
                convertView.setTag(holder);
            }


            else
            {
                holder=(Holder) convertView.getTag();
            }

            Ad item = (Ad) getItem(position);
            holder.title.setText(item.getTitle());
            holder.desc.setText(item.getDesc());
            holder.price.setText(item.getPrice());

            return convertView;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_one_way, container, false);
        setting_list = (ListView) rootView.findViewById(R.id.list_ads);

        options = new ArrayList<Ad>();


        //  FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToListView(setting_list);
        //options.add(new SettingListItem("Edit contact details",R.drawable.ic_add ));

        optionsAdapter = new OptionsAdapter();
        setting_list.setAdapter(optionsAdapter);
        setting_list.setAdapter(optionsAdapter);

        ((ViewGroup) fab.getParent()).removeView(fab);

        setting_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ad advertClicked = (Ad) optionsAdapter.getItem(i);

                Intent intent = new Intent(getActivity(), ViewAdActivity.class);
//based on item add info to intent
                startActivity(intent);

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);

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
