package Entidades;

public class Usuario {
private String nombreUsuario;
private Boolean verificado;
private Integer cantidadTweets;
private Integer cantidadFavoritos;



    public Usuario(String nombreUsuario, Boolean verificado, Integer cantidadTweets, Integer cantidadFavoritos) {
        this.nombreUsuario = nombreUsuario;
        this.verificado = verificado;
        this.cantidadTweets = cantidadTweets;
        this.cantidadFavoritos = cantidadFavoritos;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    public Integer getCantidadTweets() {
        return cantidadTweets;
    }

    public void setCantidadTweets(Integer cantidadTweets) {
        this.cantidadTweets = cantidadTweets;
    }

    public Integer getCantidadFavoritos() {
        return cantidadFavoritos;
    }

    public void setCantidadFavoritos(Integer cantidadFavoritos) {
        this.cantidadFavoritos = cantidadFavoritos;
    }
}
