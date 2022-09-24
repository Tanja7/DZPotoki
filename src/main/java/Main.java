import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        String products[] = {"Хлеб", "Яблоки", "Молоко", "Морковь", "Конфеты"};
        int[] prices = new int[]{60, 130, 80, 20, 300};

        ClientLog log = new ClientLog();

        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        Basket basket = new Basket(products, prices);

        File file = new File("basket.json");
        if (file.exists()) {
            Basket basket1 = Basket.loadFromJsonFile(file);
            if (basket1 != null)
                basket = basket1;
        }


        while (true) {
            System.out.println("Выберите товар и количество или введите end");
            String input = scanner.nextLine();

            if ("end".equals(input)) {
                break;
            } else {
                String[] parts = input.split(" ");
                int productNumber = Integer.parseInt(parts[0]) - 1; //номер продукта со сканера
                int productCount = Integer.parseInt(parts[1]); // кол-во продукта со сканера
                basket.addToCart(productNumber, productCount);
                log.log(productNumber, productCount);
                try {
                    basket.saveJson(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        basket.printCart();

        File logFile = new File("log.csv");
        log.exportAsCSV(logFile);
    }
}
