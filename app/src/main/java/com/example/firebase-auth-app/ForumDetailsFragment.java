package com.example.inclass08;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForumDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumDetailsFragment extends Fragment {
    TextView textViewForumTitle;
    TextView textViewForumOwnerName;
    TextView textViewForumDesc;
    TextView textViewNumComments;
    EditText editTextTextComment;
    Button buttonPostSubmit;
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Forum forum;
    private String mParam2;

    public ForumDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ForumDetailsFragment newInstance(Forum forum) {
        ForumDetailsFragment fragment = new ForumDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, forum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            forum = (Forum) getArguments().getSerializable(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum_details, container, false);
        textViewForumTitle = view.findViewById(R.id.tvforumTitle);
        textViewForumOwnerName = view.findViewById(R.id.tvOwnerName);
        textViewForumDesc = view.findViewById(R.id.tvForumDescrip);
        textViewNumComments = view.findViewById(R.id.tvNumComments);
        editTextTextComment = view.findViewById(R.id.etTextComments);
        buttonPostSubmit = view.findViewById(R.id.buttonPostSubmit);
        recyclerView = view.findViewById(R.id.recyclerView);

        textViewForumTitle.setText(forum.getTitle());
        textViewForumDesc.setText(forum.getForum());
        textViewForumOwnerName.setText(forum.getAuthor());
        textViewNumComments.setText("");

        view.findViewById(R.id.buttonPostSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = editTextTextComment.getText().toString();
                if(commentText.isEmpty()){
                    Toast.makeText(getActivity(), "Enter comment text", Toast.LENGTH_SHORT);
                }else{

                }
            }
        });
        return view;
    }
}