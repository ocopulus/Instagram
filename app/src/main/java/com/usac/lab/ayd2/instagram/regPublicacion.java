package com.usac.lab.ayd2.instagram;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class regPublicacion extends Fragment {

    private Button btnChoose, btnUpload;
    private ImageView imageView;
    private EditText txtContenido, txtHashtag;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;

    //Storage en google Cloud
    FirebaseStorage storage;
    StorageReference storageReference;

    //Base de Datos
    DatabaseReference mFirebaseDatabaseReference;

    public regPublicacion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment2
        View vista = inflater.inflate(R.layout.fragment_reg_publicacion, container, false);
        //Initialize Views
        btnChoose = (Button) vista.findViewById(R.id.btnChoose);
        btnUpload = (Button) vista.findViewById(R.id.btnUpload);
        imageView = (ImageView) vista.findViewById(R.id.imgView);
        txtContenido = (EditText) vista.findViewById(R.id.txtContenido);
        txtHashtag = (EditText)  vista.findViewById(R.id.hashtag);

        //Inicializando el Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImagen();
            }
        });
        
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImagen();
            }
        });
        return vista;
    }

    private void UploadImagen() {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("myPhotos/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(final Uri uri) {
                                    Query query = mFirebaseDatabaseReference.child("usuarios").orderByChild("correo").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                                            {
                                                Map<String, Object> usua = (Map<String, Object>) postSnapshot.getValue();
                                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                                DatabaseReference publicaciones = mDatabase.child("publicacion");
                                                publicacion pub = new publicacion();
                                                pub.contenido = txtContenido.getText().toString();
                                                pub.dislikes = (long) 0;
                                                pub.fecha = (long) 61615661;
                                                String [] tags = txtHashtag.getText().toString().split(",");
                                                pub.hashtags = new ArrayList<String>();
                                                for (int i = 0; i < tags.length; i++){
                                                    pub.hashtags.add(tags[i]);
                                                }
                                                pub.imagen = uri.toString();
                                                pub.likes = (long) 0;
                                                pub.nombre = usua.get("nombre").toString();
                                                pub.usuario = postSnapshot.getKey();
                                                publicaciones.push().setValue(pub);
                                                Toast.makeText(getContext(), "Uploaded Finish", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void ChooseImagen() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
