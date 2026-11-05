package interpreter;

import interpreter.controller.Controller;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.expression.ArithExp;
import interpreter.model.expression.ValueExp;
import interpreter.model.expression.VarExp;
import interpreter.model.state.PrgState;
import interpreter.model.statement.*;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import java.util.Scanner;

public class Main {
    private static IStmt createEx1() {
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
    }

    private static IStmt createEx2() {
        return new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
                                new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));
    }

    private static IStmt createEx3() {
        return new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
    }

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
                case "1" -> runProgram(createEx1());
                case "2" -> runProgram(createEx2());
                case "3" -> runProgram(createEx3());
                case "0" -> {
                    System.out.println("La revedere!");
                    return;
                }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }
}