package com.example.inclass08;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ForumRecyclerAdapter extends RecyclerView.Adapter<ForumRecyclerAdapter.ForumViewHolder> {
    ArrayList<Forum> forums;
    Context context;
    FirebaseAuth mAuth;

    public ForumRecyclerAdapter(ArrayList<Forum> data, Context context){
        this.forums = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent, false);

        ForumViewHolder forumViewHolder = new ForumViewHolder(view);
        return forumViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        Forum forum = forums.get(position);
        if(forum.getDate()==null){
            holder.date.setText(FieldValue.serverTimestamp().toString());
        }else{
            holder.date.setText(forum.getDate().toString());
        }

        holder.author.setText(forum.getAuthor());
        holder.title.setText(forum.getTitle());
        holder.post.setText(forum.getForum());

        if (holder.mAuth.getCurrentUser().getEmail().equalsIgnoreCase(holder.author.getText().toString())) {
            holder.trash.setVisibility(View.VISIBLE);
            holder.bool = true;
        } else {
            holder.trash.setVisibility(View.INVISIBLE);
        }


        holder.likePostCount(forum);
        holder.mContext = context;
        holder.forum = forums.get(position);

    }

    @Override
    public int getItemCount() {
        return forums.size();
    }

    public static class ForumViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView author;
        TextView numLikes;
        TextView post;
        TextView title;
        ImageView trash;
        ImageView unliked;
        FirebaseAuth mAuth;
        FirebaseFirestore db;
        boolean bool;
        boolean like;
        Context mContext;
        Forum forum;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            author = itemView.findViewById(R.id.author);
            numLikes = itemView.findViewById(R.id.numOflikes);
            post = itemView.findViewById(R.id.content);
            title = itemView.findViewById(R.id.title);
            trash = itemView.findViewById(R.id.trash);
            unliked = itemView.findViewById(R.id.unliked);
            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            bool = false;
            like = false;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.containerView, ForumDetailsFragment.newInstance(forum))
                            .addToBackStack(null)
                            .commit();
                }
            });

            unliked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likePostCount(forum);
                    unliked.setImageResource(R.drawable.like_favorite);
                }
            });



            trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bool = true){
                        deletePost();
                    }
                }
            });


        }

        private void deletePost(){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();

            db.collection("forums").document(title.getText().toString()).delete();
        }

        private void likePostCount(Forum forum){
            mAuth = FirebaseAuth.getInstance();
            HashMap<String, Object> map = new HashMap<>();


            if(map.containsKey(mAuth.getCurrentUser().getUid())){
                unliked.setImageResource(R.drawable.like_favorite);
                map.put(mAuth.getCurrentUser().getUid(), forum);
            }else{
                unliked.setImageResource(R.drawable.like_not_favorite);
            }

            int numbers = map.size();

            if(numbers == 0){
                numLikes.setText("0");
                unliked.setImageResource(R.drawable.like_not_favorite);
            } else if( numbers == 1){
                numLikes.setText("1");
            }else{
                numLikes.setText(numbers + "");
            }





        }

    }


}
