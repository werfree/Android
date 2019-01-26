/*package com.example.sayantan.life_saver;


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


A simple {@link Fragment} subclass.

public class RecipientFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference donorReference,databaseReference, getUserData;
    private FirebaseAuth mAuth;

    String UserCity;

    public View view;
    String  stext="";



    public RecipientFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_recipient2, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.Recycle_view);


        mAuth = FirebaseAuth.getInstance();

        String cur_uid = mAuth.getUid();
        ((android.support.v7.widget.SearchView) view.findViewById(R.id.sbar)).setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                stext=query;


                onStart();

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        donorReference= FirebaseDatabase.getInstance().getReference().child("Users");
        donorReference.keepSynced(true);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getUserData = FirebaseDatabase.getInstance().getReference().child("Users").child(cur_uid);

        getUserData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserCity = (dataSnapshot.child("userCity").getValue().toString());
                stext=UserCity;


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                try {
                    throw databaseError.toException();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });


        // Inflate the layout for this fragment

        View v=null;

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<all_donor,DonarFragment.all_donarViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<all_donor, DonarFragment.all_donarViewHolder>
                        (
                                all_donor.class,
                                R.layout.donor_display,
                                DonarFragment.all_donarViewHolder.class,
                                donorReference
                        ) {
                    @Override
                    protected void populateViewHolder(DonarFragment.all_donarViewHolder viewHolder, final all_donor model, int position) {


                        if(model.getUserType().equals("Recipient")& (model.getUserCity().equalsIgnoreCase(stext))){


                            view.setVisibility(View.VISIBLE);
                            viewHolder.setUserName(model.getUserName());
                            viewHolder.setUserBlood(model.getUserBlood());
                            viewHolder.setUserPh(model.getUserPh());
                            viewHolder.setUserCity(model.getUserCity());




                        }
                        else {

                            viewHolder.Layout_hide();
                        }

                        viewHolder.call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+model.getUserPh()));
                                startActivity(intent);

                            }
                        });


                    }


                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class all_donarViewHolder extends RecyclerView.ViewHolder{

        View mView;

        Button call;
        private LinearLayout linearLayout;
        LinearLayout.LayoutParams params;
        public all_donarViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            this.linearLayout = linearLayout;
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linear);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            call=itemView.findViewById(R.id.call);

        }
        public void setUserType(String userType) {

        }

        public void setUserName(String userName) {

            TextView name=(TextView)mView.findViewById(R.id.donor_name);

            name.setText(userName);

        }

        public void setUserBlood(String userBlood){
            TextView blood=(TextView)mView.findViewById(R.id.donor_blood);
            blood.setText(userBlood);
        }

        public  void setUserPh(String userPh) {
            TextView ph=(TextView)mView.findViewById(R.id.donor_phone_no);
            ph.setText(userPh);
        }
        public  void setUserCity(String userCity) {
            TextView ct=(TextView)mView.findViewById(R.id.donor_city);
            ct.setText(userCity);
        }

        public void Layout_hide(){

            params.height=0;
            params.width=0;
            linearLayout.setLayoutParams(params);


        }
    }
}




*/


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

import com.example.sayantan.life_saver.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import me.mvdw.recyclerviewmergeadapter.adapter.RecyclerViewMergeAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipientFragment extends Fragment
{

    private RecyclerView recyclerView;
    private DatabaseReference donorReference,databaseReference, getUserData, req_slip;
    private FirebaseAuth mAuth;

    String UserCity;

    public View view;
    String  stext="";

    ArrayList<String[]> data = new ArrayList<String[]>();




    public RecipientFragment()
    {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_recipient2, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.Recycle_view);


        mAuth = FirebaseAuth.getInstance();


        String cur_uid = mAuth.getUid();
        ((android.support.v7.widget.SearchView) view.findViewById(R.id.sbar)).setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {


                stext=query;


                onStart();

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });

        donorReference= FirebaseDatabase.getInstance().getReference().child("Users");
        donorReference.keepSynced(true);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);
        req_slip = FirebaseDatabase.getInstance().getReference().child("Slip");
        req_slip.keepSynced(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getUserData = FirebaseDatabase.getInstance().getReference().child("Users").child(cur_uid);

        getUserData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserCity = (dataSnapshot.child("userCity").getValue().toString());
                stext=UserCity;


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                try {
                    throw databaseError.toException();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });


        // Inflate the layout for this fragment

        View v=null;

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        final RecyclerViewMergeAdapter adapter = new RecyclerViewMergeAdapter();

        FirebaseRecyclerAdapter<all_donor,DonarFragment.all_donarViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<all_donor, DonarFragment.all_donarViewHolder>
                        (
                                all_donor.class,
                                R.layout.donor_display,
                                DonarFragment.all_donarViewHolder.class,
                                donorReference
                        ) {
                    @Override
                    protected void populateViewHolder(DonarFragment.all_donarViewHolder viewHolder, final com.example.sayantan.life_saver.all_donor model, int position) {


                        if(model.getUserType().equals("Recipient")& (model.getUserCity().equalsIgnoreCase(stext))){


                            view.setVisibility(View.VISIBLE);
                            viewHolder.setUserName(model.getUserName());
                            viewHolder.setUserBlood(model.getUserBlood());
                            viewHolder.setUserPh(model.getUserPh());
                            viewHolder.setUserCity(model.getUserCity());




                        }
                        else {

                            viewHolder.Layout_hide();
                        }

                       /* viewHolder.call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Toast.makeText(getContext(),"Hello",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+model.getUserPh()));
                                startActivity(intent);

                            }
                        });*/


                    }


                };
        final RecyclerView.Adapter Aadapter = new RecyclerView.Adapter<RecipientFragment.all_donarViewHolder>()
        {




            @Override
            public RecipientFragment.all_donarViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
            {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.donor_display, parent, false);
                return new all_donarViewHolder(v);
            }

            @Override
            public void onBindViewHolder(RecipientFragment.all_donarViewHolder holder, int position)
            {

                holder.setUserName(data.get(position)[1]);
                holder.setUserBlood(data.get(position)[2]);
                holder.setUserPh(data.get(position)[3]);
                holder.setUserCity(data.get(position)[0]);




            }

            @Override
            public int getItemCount() {
                //Toast.makeText(getContext(), "" + data.size(), Toast.LENGTH_SHORT).show();
                return data.size();
            }
        };
        req_slip.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                //Toast.makeText(getContext(),"loc : "+ dataSnapshot.child("loc").getValue(String.class),Toast.LENGTH_LONG).show();

                for(DataSnapshot a:dataSnapshot.getChildren())
                {
                    data.add(new String[]
                            {
                                    //"A","A","A","A"
                                    a.child("loc").getValue(String.class), a.child("unit").getValue(String.class), a.child("type").getValue(String.class),a.child("phno").getValue(String.class)
                            });
                }
                adapter.notifyDataSetChanged();
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

        adapter.addAdapter(firebaseRecyclerAdapter);

        adapter.addAdapter(Aadapter);
        recyclerView.setAdapter(adapter);
    }

    public class all_donarViewHolder extends RecyclerView.ViewHolder{

        View mView;

        Button call;
        TextView phn;
        private LinearLayout linearLayout;
        LinearLayout.LayoutParams params;
        public all_donarViewHolder(final View itemView) {
            super(itemView);
            mView=itemView;

            this.linearLayout = linearLayout;
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linear);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            call=itemView.findViewById(R.id.call);
            phn=itemView.findViewById(R.id.donor_phone_no);

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+phn.getText().toString()));
                    startActivity(intent);

                }
            });

        }
        public void setUserType(String userType) {

        }

        public void setUserName(String userName) {

            TextView name=(TextView)mView.findViewById(R.id.donor_name);

            name.setText(userName);

        }

        public void setUserBlood(String userBlood){
            TextView blood=(TextView)mView.findViewById(R.id.donor_blood);
            blood.setText(userBlood);
        }

        public  void setUserPh(String userPh) {
            TextView ph=(TextView)mView.findViewById(R.id.donor_phone_no);
            ph.setText(userPh);
        }
        public  void setUserCity(String userCity) {
            TextView ct=(TextView)mView.findViewById(R.id.donor_city);
            ct.setText(userCity);
        }

        public void Layout_hide(){

            params.height=0;
            params.width=0;
            linearLayout.setLayoutParams(params);


        }
    }
}



