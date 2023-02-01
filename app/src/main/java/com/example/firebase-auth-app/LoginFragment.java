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


public class LoginFragment extends Fragment {
    public EditText editEmail;
    public EditText editPassword;
    public Button login_Button;
    public TextView create_New;
    private FirebaseAuth mAuth;
    final private String TAG = "demo";



    public LoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        editEmail = view.findViewById(R.id.register_Email);
        editPassword = view.findViewById(R.id.register_Password);
        login_Button = view.findViewById(R.id.edit_Submit);
        create_New = view.findViewById(R.id.create_NewAcc);

        getActivity().setTitle("Login");

        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String password= editPassword.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(getActivity(), "Email is empty", Toast.LENGTH_SHORT).show();
                } else if(password.isEmpty()){
                    Toast.makeText(getActivity(), "Password is empty", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Log.d("TAG", "onComplete: Login successful");
                                        getFragmentManager().beginTransaction()
                                                .replace(R.id.containerView, new ForumFragment())
                                                .commit();
                                    }else{
                                        Log.d(TAG, "onComplete: Login Unsuccessful");
                                        Log.d(TAG, "onComplete: " + task.getException().getMessage());
                                    }
                                }
                            });
                }
            }
        });

        create_New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.containerView, new RegisterFragment())
                        .commit();
            }
        });

        return view;

    }

}