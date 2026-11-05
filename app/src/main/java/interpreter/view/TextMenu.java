package interpreter.view;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;

import java.util.Scanner;

public class TextMenu {
    private IStmt selectedProgram = null;

    private void runProgram(IStmt program) {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program);
        IRepository repo = new Repository(state);
        Controller ctrl = new Controller(repo, true);
        try {
            ctrl.allStep();
        } catch (Exception e) {
            System.out.println("Eroare de executie: " + e.getMessage());
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Meniu Toy Interpreter ---");
            if (selectedProgram != null) {
                System.out.println("Program selectat: DA");
            } else {
                System.out.println("Program selectat: NICIUNUL");
            }
            System.out.println("1. Alege programul");
            System.out.println("2. Ruleaza programul ales");
            System.out.println("0. Iesire");
            System.out.print("Alege o optiune: ");

            if (!scanner.hasNextLine()) {
                break;
            }
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> {
                    System.out.println("\nPrograme disponibile:");
                    System.out.println("1. Ex 1: int v; v=2; print(v)");
                    System.out.println("2. Ex 2: int a; int b; a=2+3*5; b=a+1; print(b)");
                    System.out.println("3. Ex 3: bool a; int v; a=true; (if a then v=2 else v=3); print(v)");
                    System.out.print("Selecteaza programul: ");

                    if (!scanner.hasNextLine()) break;
                    String progInput = scanner.nextLine();
                    switch (progInput) {
                        case "1" -> {
                            selectedProgram = Examples.EX1;
                            System.out.println("S-a selectat Ex 1.");
                        }
                        case "2" -> {
                            selectedProgram = Examples.EX2;
                            System.out.println("S-a selectat Ex 2.");
                        }
                        case "3" -> {
                            selectedProgram = Examples.EX3;
                            System.out.println("S-a selectat Ex 3.");
                        }
                        default -> System.out.println("Program invalid!");
                    }
                }
                case "2" -> {
                    if (selectedProgram == null) {
                        System.out.println("Nu este niciun program selectat! Alege mai intai un program (optiunea 1).");
                    } else {
                        runProgram(selectedProgram);
                    }
                }
                case "0" -> {
                    System.out.println("La revedere!");
                    return;
                }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }
}