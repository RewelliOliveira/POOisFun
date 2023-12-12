package Aula05;
import java.util.Scanner;

class ContaBanco {
    public int numConta;
    protected String tipo;
    private String dono;
    private float saldo;
    private boolean status;



    public void estadoAtual(){
        System.out.println("Numero da conta: " + this.getNumConta());
        System.out.println("Tipo da conta: " + this.getTipo());
        System.out.println("Proprietario: " + this.getDono());
        System.out.println("Saldo da conta: " + this.getSaldo());
        System.out.println("Estado da conta: " + this.getStatus());
    }

    public void abrirConta(String t) {
        setTipo(t);
        setStatus(true);
        if("CC".equals(t)){
            this.setSaldo(50);
        }else if("CP".equals(t)){
            this.setSaldo(150);
        }
    }

    public void fecharConta() {
        if(getSaldo() == 0){
            setStatus(false);
        }else{
            System.out.println("Impossivel fechar conta");
        }
    }

    public void depositar(float v) {
        if(getStatus()){
            setSaldo(getSaldo() + v);
        }else{
            System.out.println("impossivel depositar, conta fechada");
        }
    }

    public void sacar(float v) {
        if(getStatus() && getSaldo() >= v){
            setSaldo(getSaldo() - v);
        }else{
            System.out.println("impossivel sacar");
        }
    }

    public void pagarMensal(String cp) {
        int v = 0;
        if("CC".equals(getTipo())){
            v = 12;
        }else if("CP".equals(getTipo())){
            v = 20;
        }
        if(this.getStatus() && this.getSaldo() >= v){
            setSaldo(getSaldo() - v);
        }else{
            System.out.println("Impossivel pagar mensalidade");
        }
    }

    public ContaBanco() {
        saldo = 0;
        status = false;
    }

    public int getNumConta() {
        return numConta;
    }

    public void setNumConta(int numConta) {
        this.numConta = numConta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        ContaBanco p1 = new ContaBanco();
        p1.setNumConta(1);
        p1.setDono("Matheus");
        p1.abrirConta("CP");
        p1.sacar(150);
        p1.fecharConta();
        p1.estadoAtual();
    }
}


