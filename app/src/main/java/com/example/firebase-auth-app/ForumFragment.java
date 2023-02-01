package com.example.inclass08;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumFragment extends Fragment {
    Button logout;
    Button newForum;
    RecyclerView recyclerView;
    ArrayList<Forum> forumArrayList = new ArrayList<>();
    LinearLayoutManager layoutManager;
    ForumRecyclerAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForumFragment newInstance(String param1, String param2) {
        ForumFragment fragment = new ForumFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        getActivity().setTitle("Forums");
        logout = view.findViewById(R.id.logout_button);
        newForum = view.findViewById(R.id.New_Forum);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ForumRecyclerAdapter(forumArrayList, getActivity());
        recyclerView.setAdapter(adapter);
        getData();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                getFragmentManager().beginTransaction()
                        .replace(R.id.containerView, new LoginFragment())
                        .commit();
            }
        });

        newForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.containerView, new NewForumFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    private void getData(){

        /*
        db.collection("forums").get()
        .addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Log.d("TAG", "onComplete: " + document.getId());
                        Log.d("TAG", "onComplete: " + document.getData());
                    }
                }else{
                    Log.d("TAG", "onComplete: " + task.getException().getMessage());
                }
            }
        });
        */
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("forums")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        forumArrayList.clear();
                        for(QueryDocumentSnapshot document: value){
                           // forumArrayList.add(new Forum(document.getString("author"), document.getString("title"), document.getString("forum"), document.getDate("date")));

                            Forum forum = new Forum(document.getString("author"), document.getString("title"), document.getString("forum"), document.getDate("date"));
                            forumArrayList.add(forum);

                         //   Forum forum = document.toObject(Forum.class);
                         //   forumArrayList.add(forum);
                            Log.d("TAG", "onComplete: " + document.getId());
                            Log.d("TAG", "onComplete: " + document.getData());
                            Log.d("LAG", "onComplete: " + forumArrayList.size());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}