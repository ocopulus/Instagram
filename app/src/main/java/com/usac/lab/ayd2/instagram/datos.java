package com.usac.lab.ayd2.instagram;

import java.util.ArrayList;

public class datos {

    private String contenido;
    private Long dislikes;
    private ArrayList<String> hashtags;
    private String imagen;
    private Long likes;
    private String nombre;

    public datos(String contenido, Long dislikes, ArrayList<String> hashtags, String imagen, Long likes, String nombre) {
        this.contenido = contenido;
        this.dislikes = dislikes;
        this.hashtags = hashtags;
        this.imagen = imagen;
        this.likes = likes;
        this.nombre = nombre;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getDislikes() {
        return dislikes;
    }

    public void setDislikes(Long dislikes) {
        this.dislikes = dislikes;
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<String> hashtags) {
        this.hashtags = hashtags;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
