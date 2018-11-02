package com.usac.lab.ayd2.instagram;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class publicacion {
    public String contenido;
    public Long dislikes;
    public Long fecha;
    public ArrayList<String> hashtags;
    public String imagen;
    public Long likes;
    public String nombre;
    public String usuario;

    public publicacion() {
    }

    public publicacion(String contenido, Long dislikes, Long fecha, ArrayList<String> hashtags, String imagen, Long likes, String nombre, String usuario) {
        this.contenido = contenido;
        this.dislikes = dislikes;
        this.fecha = fecha;
        this.hashtags = hashtags;
        this.imagen = imagen;
        this.likes = likes;
        this.nombre = nombre;
        this.usuario = usuario;
    }
}
