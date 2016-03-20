package com.example.dardev.collegekart.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.example.dardev.collegekart.BuyRequestsActivity;
import com.example.dardev.collegekart.ViewAdActivity;
import com.example.dardev.collegekart.model.User;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.client.utilities.Base64;
import com.melnykov.fab.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dardev.collegekart.R;
import com.example.dardev.collegekart.model.Ad;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MarketFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MarketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarketFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView setting_list;
    private ArrayList<Ad> options;
    private Firebase ref;
    private OptionsAdapter optionsAdapter;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progress;
    private Button buy_rent;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment OneWayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MarketFragment newInstance(String param1) {
        MarketFragment fragment = new MarketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MarketFragment() {
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
            try {
                byte[] imageByte = Base64.decode(item.getImage());
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length, options);
                holder.icon.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            holder.desc.setText(item.getType());
            holder.price.setText("INR "+item.getPrice());

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
        Firebase.setAndroidContext(getActivity());
        sharedPreferences= getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


      //  FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToListView(setting_list);
        //options.add(new SettingListItem("Edit contact details",R.drawable.ic_add ));



         ((ViewGroup) fab.getParent()).removeView(fab);
        setting_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ad advertClicked = (Ad) optionsAdapter.getItem(i);

                Intent intent = new Intent(getActivity(), ViewAdActivity.class);
                intent.putExtra("key",((Ad) optionsAdapter.getItem(i)).getKey());
//based on item add info to intent
                startActivity(intent);

            }


        });
        progress = new ProgressDialog(getContext());

        progress.setMessage("Loading");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onResume() {
        ref = new Firebase("https://collegekart.firebaseio.com/ads");
        System.out.println("here");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    options.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        Ad post = child.getValue(Ad.class);
                        if(post!=null) {
                            post.setKey(child.getKey());
                            options.add(post);
                            System.out.println(post.getTitle());
                        }

                    }

                }
                optionsAdapter = new OptionsAdapter();


                setting_list.setAdapter(optionsAdapter);
                progress.hide();

                optionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

            // ....
        });


        super.onResume();
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
