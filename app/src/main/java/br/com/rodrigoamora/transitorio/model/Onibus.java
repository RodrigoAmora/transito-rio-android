package br.com.rodrigoamora.transitorio.model;

public class Onibus {

    private Double latidude, longitude, velocidade;
    private String linha, ordem, hora;

    public Double getLatidude() {
        return latidude;
    }

    public void setLatidude(Double latidude) {
        this.latidude = latidude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(Double velocidade) {
        this.velocidade = velocidade;
    }

    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

}
