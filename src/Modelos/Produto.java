package Modelos;
import java.io.Serializable;

public class Produto implements Serializable {
    private int codigo;
    private String nome;
    private String modelo;
    private String marca;
    private String estado;
    private double preco;
    private int foiDeletado;
    
    public Produto() {
        
    }
    
    public Produto(int codigo, String nome, String modelo, String marca, String estado, double preco, int foiDeletado) {
        this.codigo = codigo;
        this.nome = nome;
        this.modelo = modelo;
        this.marca = marca;
        this.estado = estado;
        this.preco = preco;
        this.foiDeletado = foiDeletado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getFoiDeletado() {
        return foiDeletado;
    }

    public void setFoiDeletado(int foiDeletado) {
        this.foiDeletado = foiDeletado;
    }
    
    @Override
    public String toString() {
        return String.format(
        "Codigo: %d\nNome: %s\nModelo: %s\nMarca: %s\nEstado: %s\nPreco: %.2f\nDeletado: %s\n",
            this.getCodigo(),this.getNome(),this.getModelo(),this.getMarca(),this.getEstado(),
            this.getPreco(),(this.getFoiDeletado() == -1 ? "Sim" : "Não")
        );
    }
}
