package com.ntsua.thelife;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRate extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentRate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRate.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRate newInstance(String param1, String param2) {
        FragmentRate fragment = new FragmentRate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    RecyclerView recyclerRatings;
    ArrayList<Ratings> arrRatings; //Danh sach 10 nguoi choi co so tien lon nhat
    RatingAdapter adater;
    int minMoney = 9999999; //So tien nho nhat trong top 10 nguoi choi co so tien lon nhat

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_rate, container, false);

        recyclerRatings = view.findViewById(R.id.recyclerViewRatings);
        recyclerRatings.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext().getApplicationContext());
        manager.setStackFromEnd(true);

        arrRatings = new ArrayList<>();
//        arrRatings.add(new Ratings("NTSuaa", 10, "https://graph.facebook.com/613275316729738/picture"));
//        arrRatings.add(new Ratings("NTSuaa", 100, "https://graph.facebook.com/613275316729738/picture"));
//        arrRatings.add(new Ratings("NTSuaa", 5000, "https://graph.facebook.com/613275316729738/picture"));
//        arrRatings.add(new Ratings("NTSuaa", 1000000, "https://graph.facebook.com/613275316729738/picture"));
//        arrRatings.add(new Ratings("NTSuaa", 1001000, "https://graph.facebook.com/613275316729738/picture"));
//        arrRatings.add(new Ratings("NTSuaa", 1000, "https://graph.facebook.com/613275316729738/picture"));
//        arrRatings.add(new Ratings("NTSuaa", 50000, "https://graph.facebook.com/613275316729738/picture"));
//        arrRatings.add(new Ratings("NTSuaa", 2000, "https://graph.facebook.com/613275316729738/picture"));
//        //arrRatings.add(new Ratings("NTSuaa", 1000000, "https://graph.facebook.com/613275316729738/picture"));
//        arrRatings.add(new Ratings("NTSuaa  NTSuaa NTSuaa NTSuaa NTSuaa NTSuaa", 300, "https://graph.facebook.com/613275316729738/picture"));
//
//        Collections.sort(arrRatings, Ratings.RatingComparator);
        adater = new RatingAdapter(arrRatings);
        recyclerRatings.setAdapter(adater);

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrRatings.clear();
                for(DataSnapshot snap: snapshot.getChildren())
                {
                    //Toast.makeText(getActivity(), "" + snap.getKey(), Toast.LENGTH_SHORT).show();
                    GenericTypeIndicator<ArrayList<Food>> objectsGTypeInd = new GenericTypeIndicator<ArrayList<Food>>() {};
                    ArrayList<Food> arrAsset = snap.child("Asset").getValue(objectsGTypeInd);

                    int money = snap.child("Basic").child("money").getValue(Integer.class);
                    if (arrAsset != null)
                    {
                        for (int i=0; i<arrAsset.size(); i++)
                        {
                            money += arrAsset.get(i).getPrice();
                        }
                    }

                    String name = snap.child("Information").child("Name").getValue(String.class);
                    String uri = snap.child("Information").child("PhotoUri").getValue(String.class);
                    //Toast.makeText(getActivity(), name + " " + money, Toast.LENGTH_SHORT).show();

                    if (arrRatings.size() < 10) {
                        arrRatings.add(new Ratings(name, money, uri));
                        if (minMoney > money) {
                            minMoney = money;
                        }
                        Collections.sort(arrRatings, Ratings.RatingComparator);
                        adater.notifyDataSetChanged();
                    }
                    else if(minMoney < money){
                        arrRatings.remove(9);
                        minMoney = arrRatings.get(0).getMoney();
                        arrRatings.add(new Ratings(name, money, uri));
                        Collections.sort(arrRatings, Ratings.RatingComparator);
                        adater.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        //Toast.makeText(getActivity(), "Ratings", Toast.LENGTH_SHORT).show();

        return view;
    }

}