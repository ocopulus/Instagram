package com.usac.lab.ayd2.instagram;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class publicacionesFragment extends Fragment {

    private DatabaseReference mDatabase;

    RecyclerView VistaDinamica;
    ArrayList<datos> Lista;

    public publicacionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_publicaciones, container, false);
        VistaDinamica = (RecyclerView) vista.findViewById(R.id.recyclerView);
        VistaDinamica.setLayoutManager(new LinearLayoutManager(vista.getContext()));
        Lista = new ArrayList<datos>();
        final AdapatadorRecycler myadaptadorRecycler = new AdapatadorRecycler(Lista);
        VistaDinamica.setAdapter(myadaptadorRecycler);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference publicaciones = mDatabase.child("publicacion");

        publicaciones.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                publicacion Publ = dataSnapshot.getValue(publicacion.class);
                /*System.out.println("=======================================");
                System.out.println(Publ.contenido);
                for(String tag : Publ.hashtags){
                    System.out.println("Tag: "+tag);
                }*/
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

        return vista;
    }

}
