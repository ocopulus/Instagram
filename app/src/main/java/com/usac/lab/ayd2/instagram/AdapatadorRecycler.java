package com.usac.lab.ayd2.instagram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class AdapatadorRecycler extends RecyclerView.Adapter<AdapatadorRecycler.ViewHolderPublicaciones> {

    List<datos> ListaObjetos;

    public AdapatadorRecycler(List<datos> listaObjetos) {
        ListaObjetos = listaObjetos;
    }

    @NonNull
    @Override
    public ViewHolderPublicaciones onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemlistview, null, false);
        return new ViewHolderPublicaciones(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPublicaciones viewHolderPublicaciones, int i) {
        viewHolderPublicaciones.contenido.setText(ListaObjetos.get(i).getContenido());
        String hash = "HashTag: ";
        for (String tag : ListaObjetos.get(i).getHashtags()){
            hash += tag +",";
        }
        viewHolderPublicaciones.hashtag.setText(hash);
        viewHolderPublicaciones.usuario.setText(ListaObjetos.get(i).getNombre());
        viewHolderPublicaciones.likes.setText("Likes: "+ListaObjetos.get(i).getLikes().toString());
        viewHolderPublicaciones.dislikes.setText("DisLike: "+ListaObjetos.get(i).getDislikes().toString());
        new DownloadImageTask(viewHolderPublicaciones.imagen).execute(ListaObjetos.get(i).getImagen());
    }

    @Override
    public int getItemCount() {
        return ListaObjetos.size();
    }

    public class ViewHolderPublicaciones extends RecyclerView.ViewHolder {

        ImageView imagen;
        TextView contenido;
        TextView hashtag;
        TextView usuario;
        TextView likes;
        TextView dislikes;

        public ViewHolderPublicaciones(@NonNull View itemView) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.imagen);
            contenido = (TextView) itemView.findViewById(R.id.lbContenido);
            hashtag = (TextView) itemView.findViewById(R.id.lbHashtag);
            usuario = (TextView) itemView.findViewById(R.id.lbUsuario);
            likes = (TextView) itemView.findViewById(R.id.lbLikes);
            dislikes = (TextView) itemView.findViewById(R.id.lbDisLike);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
