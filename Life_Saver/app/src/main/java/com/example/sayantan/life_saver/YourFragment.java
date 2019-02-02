package com.example.sayantan.life_saver;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class YourFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference donorReference, getUserData, db;
    private FirebaseAuth mAuth;

    ArrayList<String[]> data = new ArrayList<String[]>();
    public View view;

    public static int q = 0;
    public static int w = 0;

    public static List<String> vect = new ArrayList<>(), vact = new ArrayList<>();

    String BloodGroup, BloodType;


    public YourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_your, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.Recycle_view);


        Button b = view.findViewById(R.id.sbar);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(getActivity(), Slip.class);
                startActivity(mainIntent);

            }
        });


        mAuth = FirebaseAuth.getInstance();

        String cur_uid = mAuth.getUid();

        getUserData = FirebaseDatabase.getInstance().getReference().child("Slip").child(cur_uid);


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        donorReference = FirebaseDatabase.getInstance().getReference().child("Slip").child(mAuth.getUid());
        donorReference.keepSynced(true);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment



        db = FirebaseDatabase.getInstance().getReference().child("Slip").child(mAuth.getUid());

        final RecyclerView.Adapter adapter = new RecyclerView.Adapter<all_donarViewHolder>() {

            @Override
            public all_donarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.rtab_layout, parent, false);
                return new all_donarViewHolder(v);
            }

            @Override
            public void onBindViewHolder(all_donarViewHolder holder, int position) {

                holder.setUserName(data.get(position)[1]);
                holder.setUserBlood(data.get(position)[2]);
                holder.setUserPh(data.get(position)[3]);
                holder.setUserCity(data.get(position)[0]);


            }

            @Override
            public int getItemCount() {
                //Toast.makeText(getContext(), ""+data.size(),Toast.LENGTH_SHORT);
                return data.size();
            }
        };
        recyclerView.setAdapter(adapter);

        donorReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    data.add(new String[]
                            {
                                    dataSnapshot.child("loc").getValue(String.class), dataSnapshot.child("timesapp").getValue(String.class), dataSnapshot.child("type").getValue(String.class), ""
                            });
                    adapter.notifyDataSetChanged();

                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(), e.getMessage() + "" , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    class all_donarViewHolder extends RecyclerView.ViewHolder {

        View mView;

        Button call;
        private LinearLayout linearLayout;
        LinearLayout.LayoutParams params;

        public all_donarViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            this.linearLayout = linearLayout;
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //itemView.findViewById(R.id.call).setVisibility(View.GONE);
            itemView.findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"Called del",Toast.LENGTH_SHORT).show();
                    delper();
                }
            });

        }

        public void setUserType(String userType) {

        }

        public void setUserName(String userName) {

            TextView name = (TextView) mView.findViewById(R.id.donor_name);

            name.setText(userName);

        }

        public void setUserBlood(String userBlood) {
            TextView blood = (TextView) mView.findViewById(R.id.donor_blood);
            blood.setText(userBlood);
        }

        public void setUserPh(String userPh) {
            //TextView ph = (TextView) mView.findViewById(R.id.donor_phone_no);
           // ph.setText(userPh);
        }

        public void setUserCity(String userCity) {
            TextView ct = (TextView) mView.findViewById(R.id.donor_city);
            ct.setText(userCity);
        }

        public void Layout_hide() {

            params.height = 0;
            params.width = 0;
            linearLayout.setLayoutParams(params);


        }
    }

    private void delper()
    {
        delnot();

    }

    private void delnot() {

        db.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getContext(),"Done ",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
