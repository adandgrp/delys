package mx.com.coffeebean_lab.DeliverySwift;

/**
 * Created by cofeebeanslab on 11/07/16.
 */
import java.util.ArrayList;

public class arreglopedido {
    private String Numpedido, Direccion, nombre, repartidor, lat, lang;

    public arreglopedido() {
    }

    public arreglopedido(String Numpedido, String nombre ,String Direccion, String repartidor, String lat, String lang) {
        this.Numpedido = Numpedido;
        this.nombre = nombre;
        this.Direccion = Direccion;
        this.repartidor = repartidor;
        this.lat = lat;
        this.lang = lang;
    }

    public String getnumpedido() {
        return Numpedido;
    }
    public void Setnumpedido(String name) {
        this.Numpedido = name;
    }
    public String getDireccion() {
        return Direccion;
    }

    public void Setdireccion(String name) {
         this.Direccion = name;
    }
    public String getRepartidor() {
        return repartidor;
    }

    public void Setrepartidor(String name) {
        this.repartidor = name;
    }
    public String getlat() {
        return lat;
    }

    public void Setlat(String name) {
        this.lat = name;
    }
    public String getlang() {
        return lang;
    }

    public void Setlang(String name) {
        this.lang = name;
    }





}