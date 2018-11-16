package com.example.sayantan.life_saver;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private DatabaseReference donorReference, getUserData,databaseReference;
    private FirebaseAuth mAuth;

    public View view;

    public static int q=0;
    public static int w=0;

    public static List<String> vect= new ArrayList<>(), vact= new ArrayList<>();

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

        Button b=view.findViewById(R.id.sbar);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent=new Intent(getActivity(),Slip.class);
                startActivity(mainIntent);

            }
        });





        mAuth = FirebaseAuth.getInstance();

        String cur_uid=mAuth.getUid();

        getUserData=FirebaseDatabase.getInstance().getReference().child("Users").child(cur_uid);

        getUserData.child("History").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    //Toast.makeText(getContext(), childSnapshot.getKey(), Toast.LENGTH_LONG).show();
                    char[] temp = childSnapshot.getKey().toCharArray();
                    String tamp = childSnapshot.getValue().toString().trim();
                    temp[4]='/';
                    temp[7]='/';
                    temp[10]=' ';
                    temp[13]=':';
                    temp[16]=':';
                    vact.add(tamp);
                    vect.add(String.copyValueOf(temp));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getUserData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                BloodGroup=(dataSnapshot.child("userBlood").getValue().toString());
                BloodType=(dataSnapshot.child("userType")).getValue().toString();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                try{
                    throw databaseError.toException();
                }
                catch (Exception e){
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });


        donorReference= FirebaseDatabase.getInstance().getReference().child("Users");
        donorReference.keepSynced(true);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        // Inflate the layout for this fragment

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        /*databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                // Toast.makeText(null,m.keySet().toArray()[0].toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

            FirebaseRecyclerAdapter<all_donor, YourFragment.all_donarViewHolder> firebaseRecyclerAdapter =
                    new FirebaseRecyclerAdapter<all_donor, YourFragment.all_donarViewHolder>
                            (
                                    all_donor.class,
                                    R.layout.mytab_layout,
                                    YourFragment.all_donarViewHolder.class,
                                    donorReference
                            ) {
                        @Override
                        protected void populateViewHolder(YourFragment.all_donarViewHolder viewHolder, final all_donor model, int position) {


                            boolean a;
                            if (model.getUserType().equals(BloodType) || (model.getUserType().equals("Not Now"))) {
                                a = false;
                            } else {
                                a = true;
                            }


                            if (a && (model.getUserBlood().equals(BloodGroup))) {


                                view.setVisibility(View.VISIBLE);

                                viewHolder.setUserName(model.getUserName());
                                viewHolder.setUserBlood(model.getUserBlood());
                                viewHolder.setUserPh(model.getUserPh());
                                viewHolder.setUserType(model.getUserType());
                                viewHolder.setUserCity(model.getUserCity());


                            } else {

                                viewHolder.Layout_hide();


                            }




                        }


                    };

            recyclerView.setAdapter(firebaseRecyclerAdapter);


        }


    public static class all_donarViewHolder extends RecyclerView.ViewHolder{

        String[] a;

        View mView;
        Button call,sbar;
        private LinearLayout linearLayout;
        LinearLayout.LayoutParams params;
        public all_donarViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            this.linearLayout = linearLayout;
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linear);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);




        }


        public void setUserType(String userType) {

        }

        public void setUserName(String userName) {

            TextView name=(TextView)mView.findViewById(R.id.donor_name);
            String p="";

                if (q < vect.size()) {
                    p = vect.get(q).toString();

                }



            name.setText(p);


        }

        public void setUserBlood(String userBlood){
            TextView blood=(TextView)mView.findViewById(R.id.donor_blood);
            String p="";



                if (q <vact.size()) {
                    p = vact.get(q).toString();

                    a = p.split(" ");



                    blood.setText(a[0]);




                }




        }

        public  void setUserPh(String userPh) {
            TextView ph=(TextView)mView.findViewById(R.id.donor_phone_no);
            String p="";
            if (q <vact.size()) {





               ph.setText("Unit:-"+a[1]);





            }
        }
        public  void setUserCity(String userCity) {
            TextView ct=(TextView)mView.findViewById(R.id.donor_city);
            String p="";
            if (q <vact.size()) {





                ct.setText(a[2]);
                q++;
        }}


        public void types(String s){
            params.height=0;
            params.width=0;
            TextView t=(TextView)mView.findViewById(R.id.donor_types);
            t.setText(s);
        }

        public void Layout_hide() {

            params.height = 0;
            params.width = 0;
            linearLayout.setLayoutParams(params);


        }
    }


}

