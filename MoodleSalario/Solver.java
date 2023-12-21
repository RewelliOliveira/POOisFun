package MoodleSalario;
//1. Fiz tudo e passou em todos os testes
//2. Comecei a fazer sozinho, fiz boa parte, mas por conta do tempo pedi ajuda a meu amigo
//3. Aprendi como estruturar melhor o meu codigo e como usar de forma apropriada
//herença, mas ainda tenho um pouco de dificuldade
//4. Demorei mais de 4 horas juntando o tempo que eu fiquei fazendo na aula.

import java.util.*;

class Solver {
    public static void main(String[] args){
        System.out.println("side_by_side=080");
        Scanner scanner = new Scanner(System.in);

        UFC ufc = new UFC();
        label:
        while(true){
            try{
                String line = scanner.nextLine();
                System.out.println("$" + line);
                String[] ui = line.split(" ");
                switch (ui[0]) {
                    case "end":
                        break label;
                    case "addProf":
                        ufc.addFuncionario(new Professor(ui[1], ui[2]));
                        break;
                    case "addSta":
                        ufc.addFuncionario(new STA(ui[1], Integer.parseInt(ui[2])));
                        break;
                    case "addTer":
                        ufc.addFuncionario(new Tercerizado(ui[1], Integer.parseInt(ui[2]), ui[3]));
                        break;
                    case "rm":
                        ufc.rmFuncionario(ui[1]);
                        break;
                    case "showAll":
                        System.out.print(ufc);
                        break;
                    case "show":
                        System.out.println(ufc.getFuncionario(ui[1]));
                        break;
                    case "addDiaria":
                        ufc.getFuncionario(ui[1]).addDiaria();
                        break;
                    case "setBonus":
                        ufc.setBonus(Integer.parseInt(ui[1]));
                        break;
                    default:
                        System.out.print("fail: comando invalido");
                        break;
                }
            }catch (Exception me){
                System.out.println(me.getMessage());
            }
        }
    }
}