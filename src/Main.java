import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int correctFileCount = 0;

        while (true) {
            System.out.print("Введите путь к файлу: ");
            String path = scanner.nextLine();

            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();

            if (!fileExists) {
                System.out.println("Файл не существует.");
                continue;
            } else {
                if (isDirectory) {
                    System.out.println("Указанный путь ведет к папке, а не к файлу.");
                    continue;
                } else {
                    correctFileCount++;
                    System.out.println("Путь указан верно");
                    System.out.println("Это файл номер " + correctFileCount);
                }
            }
        }
    }
}
