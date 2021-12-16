package com.letscode.cookBook.view;

import com.letscode.cookBook.controller.Catalogo;
import com.letscode.cookBook.domain.Receita;
import com.letscode.cookBook.enums.Categoria;

import java.util.Scanner;

public class CatalogoView {
    public CatalogoView() {
        controller = new Catalogo();
        scanner = new Scanner(System.in);
    }

    private final Receita NONE_FOUND = new Receita("Nenhuma receita encontrada", Categoria.PRATO_UNICO);
    private Receita receita;
    Scanner scanner;
    Catalogo controller;
    private int curIndex = -1;

    private void showHeader() {
        ScreenUtil.printTextLine("", 80, true, '=');
        ScreenUtil.printTextLine("#### #### #### #  #  ###  #### #### #  #", 80, true, ' ');
        ScreenUtil.printTextLine("#    #  # #  # # #   #  # #  # #  # # # ", 80, true, ' ');
        ScreenUtil.printTextLine("#    #  # #  # ##    ###  #  # #  # ##  ", 80, true, ' ');
        ScreenUtil.printTextLine("#    #  # #  # # #   #  # #  # #  # # # ", 80, true, ' ');
        ScreenUtil.printTextLine("#### #### #### #  #  ###  #### #### #  #", 80, true, ' ');
        ScreenUtil.printTextLine("", 80, true, '=');
    }

    private void showReceita(Receita receita) {
        System.out.println(receita.toString());
    }

    private void showAnterior() {
        if (curIndex > 0) {
            this.receita = controller.getReceita(curIndex - 1);
            if (receita != null) curIndex--;
        }
        show();
    }

    private void showSeguinte() {
        this.receita = controller.getReceita(curIndex + 1);
        if (receita != null) curIndex++;
        show();
    }

    private void add() {
        controller.add(new NovaReceitaView().criaReceita());
        this.receita = controller.getRandom();
        show();
    }

    private void search() throws InterruptedException {
        ScreenUtil.printTextLine("Digite o nome da receita");
        Receita receita = controller.getReceita(scanner.next());
        if (receita == null) {
            ScreenUtil.printTextLine("Receita não encontrada");
            Thread.sleep(2000);
        }
        else this.receita = receita;
        show();
    }

    private void del() {
        if (curIndex >= 0) {
            controller.del(receita.getNome());
        }
        show();
    }

    public void show() {
        showHeader();
        showReceita(receita == null ? NONE_FOUND : receita);
        ScreenUtil.printTextLine("", 80, true, '=');
        ScreenUtil.printTextLine("P: Receita anterior", 80, true);
        ScreenUtil.printTextLine("N: Receita seguinte", 80, true);
        ScreenUtil.printTextLine("+: Adicionar nova receita", 80, true);
        ScreenUtil.printTextLine("-: Remover receita", 80, true);
        ScreenUtil.printTextLine("S: Pesquisar receita", 80, true);
        ScreenUtil.printTextLine("", 80, true, '=');
        ScreenUtil.printTextLine("#: ", 80);
        String option;
        do {
            option = new Scanner(System.in).next();
            switch (option.toUpperCase()) {
                case "P":
                    showAnterior();
                    break;
                case "N":
                    showSeguinte();
                    break;
                case "+":
                    add();
                    break;
                case "-":
                    del();
                    break;
                case "S":
                    try {
                        search();
                    }
                    catch (InterruptedException e) {

                    }
                    break;
                default:
                    ScreenUtil.printTextLine("Opção inválida", 80);
                    ScreenUtil.printTextLine("#: ", 80);
            }
        } while (true);
    }
}
