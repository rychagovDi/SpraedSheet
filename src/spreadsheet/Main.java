package spreadsheet;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int rows = 0;
        int columns = 0;
        
        try{
        rows = howManyRows();
        columns = howManyColumns();
        }
        catch (InputMismatchException e){
        }

        if (rows <= 0 || columns <= 0 || rows > 15000 || columns > 15000) {
            System.out.println("#Недопустимое значение");
        } else {
        new View(rows, columns);
        }
    }

    public static int howManyRows() {
        Scanner in = new Scanner(System.in);
        System.out.println("How many rows do you want?");
        int rows = in.nextInt();
        return rows;

    }

    public static int howManyColumns() {
        Scanner in = new Scanner(System.in);
        System.out.println("How many columns do you want?");
        int columns = in.nextInt();
        return columns;

    }
}
