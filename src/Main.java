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
                    int totalLines = 0;
                    int yandexCount = 0;
                    int googleCount = 0;
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
                            totalLines++;

                            String agent = extractAgentFromUserAgent(line);
                            if ("YandexBot".equals(agent)) {
                                yandexCount++;
                            } else if ("Googlebot".equals(agent)) {
                                googleCount++;
                            }
                        }

                        if (totalLines == 0) {
                            System.out.println("Файл пустой.");
                        } else {
                            double yandexShare = (yandexCount * 100.0) / totalLines;
                            double googleShare = (googleCount * 100.0) / totalLines;

                            System.out.println("Всего запросов (строк): " + totalLines);
                            System.out.println("YandexBot: " + yandexCount + " (" + String.format("%.2f", yandexShare) + "%)");
                            System.out.println("Googlebot: " + googleCount + " (" + String.format("%.2f", googleShare) + "%)");
                        }
                    } catch (LongLineException e) {
                        System.out.println(e.getMessage());
                    } catch (IOException e) {
                        System.out.println("Ошибка чтения файла: " + e.getMessage());
                    }
                }
            }
        }
    }
                private static String extractAgentFromUserAgent (String line){
                    int open = line.indexOf('(');
                    int close = line.indexOf(')', open + 1);
                    if (open == -1 || close == -1) {
                        return null;
                    }

                    String inBrackets = line.substring(open + 1, close);
                    String[] parts = inBrackets.split(";");
                    if (parts.length < 2) {
                        return null;
                    }

                    String second = parts[1].trim();
                    int slash = second.indexOf('/');
                    if (slash == -1) {
                        return second;
                    }
                    return second.substring(0, slash);
                }
}

