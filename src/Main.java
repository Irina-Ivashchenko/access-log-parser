import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Введите первое число: ");
        int firstNumber = new Scanner(System.in).nextInt();
        System.out.print("Введите второе число: ");
        int secondNumber = new Scanner(System.in).nextInt();
        System.out.println("Сумма введенных чисел: " + (firstNumber + secondNumber));
        System.out.println("Разность введенных чисел: " + (firstNumber - secondNumber));
        System.out.println("Произведение введенных чисел: " + (firstNumber * secondNumber));
        System.out.println("Частное введенных чисел: " + ((double)firstNumber / secondNumber));


    }
}
