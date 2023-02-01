package com.example.inclass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterFragment extends Fragment {
    private EditText Rname;
    private EditText Remail;
    private EditText Rpassword;
    private Button submit;
    private TextView cancel;
    private FirebaseAuth mAuth;
    final private String TAG = "demo";


    public RegisterFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        Rname = view.findViewById(R.id.register_Name);
        Remail = view.findViewById(R.id.register_Email);
        Rpassword = view.findViewById(R.id.register_Password);

        submit = view.findViewById(R.id.submit_Button);
        cancel = view.findViewById(R.id.cancel_Button);
        getActivity().setTitle("Create New Account");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Remail.getText().toString();
                String password = Rpassword.getText().toString();
                String name = Rname.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(getActivity(), "Email is empty", Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(getActivity(), "Password is empty", Toast.LENGTH_SHORT).show();
                }else if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Name is empty", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Log.d(TAG, "onComplete: Register Successful");
                                        Log.d(TAG, "onComplete: " + mAuth.getCurrentUser().getUid());
                                        getFragmentManager().beginTransaction()
                                                .replace(R.id.containerView, new ForumFragment())
                                                .commit();
                                    }else{
                                        Log.d(TAG, "onComplete: Register Unsuccessful");
                                        Log.d(TAG, "onComplete: " +task.getException().getMessage());
                                    }
                                }
                            });
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new LoginFragment())
                    .commit();
            }
        });
        return view;
    }


}