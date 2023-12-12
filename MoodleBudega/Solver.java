package MoodleBudega;
import java.util.*;

class Pessoa{
    private String nome;
    public Pessoa(){
    }
    public String toString(){
        return "";
    }

    //getter e setter
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
}

class Mercantil{
    private ArrayList<Pessoa> caixas;
    private ArrayList<Pessoa> esperando;

    public boolean validarIndice(int ind){
        return true;
    }

    public Mercantil(int qntCaixas){

    }
    public String toString(){
        return String.format("");
    }

    public void chegar(Pessoa pessoa){

    }
    public boolean chamar(int ind){
        return true;
    }
    public void finalizar(int ind){
    }


}