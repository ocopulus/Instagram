package com.usac.lab.ayd2.instagram;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class usuario {
    public String correo;
    public Long edad;
    public String nombre;
    public String pass;

    public usuario() {
    }

    public usuario(String correo, Long edad, String nombre, String pass) {
        this.correo = correo;
        this.edad = edad;
        this.nombre = nombre;
        this.pass = pass;
    }
}
