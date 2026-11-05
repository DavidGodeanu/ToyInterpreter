package interpreter;

import interpreter.controller.Controller;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import java.util.Scanner;

public class Main {
    private static void runProgram(IStmt program) {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program);
        IRepository repo = new Repository(state);
        Controller ctrl = new Controller(repo, true);
        try {
            ctrl.allStep();
        } catch (Exception e) {
            System.out.println("Eroare de executie: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Meniu Toy Interpreter ---");
            System.out.println("1. Ex 1: int v; v=2; print(v)");
            System.out.println("2. Ex 2: int a; int b; a=2+3*5; b=a+1; print(b)");
            System.out.println("3. Ex 3: bool a; int v; a=true; (if a then v=2 else v=3); print(v)");
            System.out.println("0. Iesire");
            System.out.print("Alege un program: ");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> runProgram(Examples.EX1);
                case "2" -> runProgram(Examples.EX2);
                case "3" -> runProgram(Examples.EX3);
                case "0" -> {
                    System.out.println("La revedere!");
                    return;
                }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }
}