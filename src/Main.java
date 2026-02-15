import java.io.File;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


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
                    Statistics stats = new Statistics();

                    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                        String line;
                        int lineNumber = 0;
                        while ((line = reader.readLine()) != null) {
                            lineNumber++;
                            int length = line.length();
                            if (length > 1024) {
                                throw new LongLineException(
                                        "Ошибка: строка длиннее 1024 символов (файл: " + path + ", номер строки: "
                                                + lineNumber + ", длина: " + length + ")"
                                );
                            }
                            LogEntry entry = new LogEntry(line);
                            stats.addEntry(entry);
                        }
                        System.out.printf("Средний трафик в час: %.2f%n", stats.getTrafficRate());

                    } catch (LongLineException e) {
                        System.out.println(e.getMessage());
                    } catch (IOException e) {
                        System.out.println("Ошибка чтения файла: " + e.getMessage());
                    }
                }
            }
        }
    }
}

