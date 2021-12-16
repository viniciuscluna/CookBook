package com.letscode.cookBook.view;

import com.letscode.cookBook.domain.Ingrediente;
import com.letscode.cookBook.domain.Receita;
import com.letscode.cookBook.domain.Rendimento;
import com.letscode.cookBook.enums.Categoria;
import com.letscode.cookBook.enums.TipoMedida;
import com.letscode.cookBook.enums.TipoRendimento;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NovaReceitaView {
    Scanner scanner;
    Receita receita;
    String nome;
    Categoria categoria;

    public NovaReceitaView() {
        this.scanner = new Scanner(System.in);
    }

    public void askNome() {
        System.out.println("Qual o nome da receita?");
        nome = scanner.nextLine();
        if (nome.isBlank()) {
            ScreenUtil.printTextLine("Nome inválido!");
            askNome();
        }
    }

    public void askCategoria() {
        System.out.println("Qual a categoria da receita?");
        for (Categoria cat : Categoria.values()) {
            System.out.printf("%d - %s %n", cat.ordinal(), cat.name());
        }
        int posicaoCategoria = scanner.nextInt();
        if (posicaoCategoria < 0 || posicaoCategoria >= Categoria.values().length) {
            ScreenUtil.printTextLine("Categoria inválida!");
            askCategoria();
        }
        categoria = Categoria.values()[posicaoCategoria];
    }

    public void askTempoPreparo() {
        System.out.println("Qual o tempo de preparo?");
        try {
            int tempoPreparo = scanner.nextInt();
            receita.setTempoPreparo(tempoPreparo);
        } catch (Exception ex) {
            ScreenUtil.printTextLine("Tempo de preparo inválido!");
            askTempoPreparo();
        }
    }

    public void askRendimento() {
        try {

            ScreenUtil.printTextLine("Qual a tipo de rendimento da receita?");
            for (TipoRendimento cat : TipoRendimento.values()) {
                System.out.printf("%d - %s %n", cat.ordinal(), cat.name());
            }

            int posicaoTipoRendimento = scanner.nextInt();
            if (posicaoTipoRendimento < 0 || posicaoTipoRendimento >= TipoRendimento.values().length) {
                ScreenUtil.printTextLine("Tipo de rendimento inválido!");
                askRendimento();
            }

            ScreenUtil.printTextLine("Qual a quantidade do rendimento?");
            int quantidade = scanner.nextInt();

            receita.setRendimento(new Rendimento(quantidade, TipoRendimento.values()[posicaoTipoRendimento]));

        } catch (Exception ex) {
            ScreenUtil.printTextLine("Rendimento inválido!");
            askRendimento();
        }

    }

    public void askIngredientes(){
        try {

            ScreenUtil.printTextLine("----INGREDIENTES----");
            List<Ingrediente> ingredienteList = new ArrayList<>();
            while (true) {
                ScreenUtil.printTextLine("Qual o nome do ingrediente?");
                String nome = scanner.next();
                if (nome.isBlank()) {
                    ScreenUtil.printTextLine("Nome inválido!");
                    askIngredientes();
                }

                ScreenUtil.printTextLine("Qual a tipo de medida desse ingrediente?");
                for (TipoMedida cat : TipoMedida.values()) {
                    System.out.printf("%d - %s %n", cat.ordinal(), cat.name());
                }

                int posicaoTipoMedida = scanner.nextInt();
                if (posicaoTipoMedida < 0 || posicaoTipoMedida >= TipoMedida.values().length) {
                    ScreenUtil.printTextLine("Tipo de medida inválida!");
                    askIngredientes();
                }

                ScreenUtil.printTextLine("Qual a quantidade desse ingrediente?");
                double quantidade = scanner.nextDouble();

                Ingrediente ingrediente = new Ingrediente(nome, quantidade,TipoMedida.values()[posicaoTipoMedida]);
                ingredienteList.add(ingrediente);

                ScreenUtil.printTextLine("Deseja adicionar mais um ingrediente (1 - Sim, 2 - Não)?");
                int decisao = scanner.nextInt();
                if(decisao == 2) break;

            }
            receita.setIngredientes(ingredienteList.toArray(Ingrediente[]::new));
        }
        catch (Exception ex){
            ScreenUtil.printTextLine("Ingrediente inválido!");
            askIngredientes();
        }
    }

    private void criarReceitaBase() {
        receita = new Receita(nome, categoria);
    }

    public Receita criaReceita(){
        ScreenUtil.clearScreen();
        ScreenUtil.printTextLine("--- INCLUSÃO DE RECEITA ---");
        askNome();
        askCategoria();
        criarReceitaBase();
        askRendimento();
        askIngredientes();
        return receita;
    }
}
