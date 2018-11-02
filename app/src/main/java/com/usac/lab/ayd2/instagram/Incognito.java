package com.usac.lab.ayd2.instagram;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Incognito extends AppCompatActivity {

    private DatabaseReference mDatabase;

    RecyclerView VistaDinamica;
    ArrayList<datos> Lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incognito);

        VistaDinamica = (RecyclerView) findViewById(R.id.view);
        VistaDinamica.setLayoutManager(new LinearLayoutManager(this));

        Lista = new ArrayList<datos>();

        final AdapatadorRecycler myadaptadorRecycler = new AdapatadorRecycler(Lista);
        VistaDinamica.setAdapter(myadaptadorRecycler);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference publicaciones = mDatabase.child("publicacion");

        publicaciones.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                publicacion Publ = dataSnapshot.getValue(publicacion.class);
                System.out.println("=======================================");
                System.out.println(Publ.contenido);
                for(String tag : Publ.hashtags){
                    System.out.println("Tag: "+tag);
                }
                datos miPub = new datos(Publ.contenido, Publ.dislikes, Publ.hashtags,
                        Publ.imagen, Publ.likes, Publ.nombre);
                Lista.add(miPub);
                myadaptadorRecycler.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
