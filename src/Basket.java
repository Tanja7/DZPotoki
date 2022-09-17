import java.io.*;

public class Basket {
    String[] products;
    int[] prices;
    int sumProducts = 0; // общая сумма покупки
    int[] count; // массив для хранения количества продуктов

    // конструктор, принимающий массив цен и названий продуктов;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        count = new int[products.length];
    }

    // метод добавления количества определенного продукта в корзину;
    public void addToCart(int productNumber, int productCount) {
        count[productNumber] += productCount;
        int currentPrice = prices[productNumber]; // цена продукта по номеру
        int sum = (productCount * currentPrice); // сумма покупки
        sumProducts += sum;
    }

    // метод вывода на экран покупательской корзины

    public void printCart() {
        System.out.println("Ваша корзина: ");
        for (int i = 0; i < products.length; i++) {
            if (count[i] > 0) {

                System.out.println(products[i] + " " + count[i] + " шт. "
                        + prices[i] + "руб./шт. " + count[i] * prices[i] + " руб. в сумме");
            }
        }
        System.out.println("Итого: " + sumProducts + " руб.");
    }

    //  метод сохранения корзины в текстовый файл
    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(textFile)) {

            for (String product : products) {
                writer.print(product + " ");
            }
            writer.println();
            for (int i = 0; i < products.length; i++) {
                writer.print(prices[i] + " ");
            }
            writer.println();
            for (int i = 0; i < products.length; i++) {
                writer.print(count[i] + " ");
            }

            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена

    public static Basket loadFromTxtFile(File textFile) {
        String[] products;
        int[] prices1;
        int[] counts1;
        Basket basket = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            products = reader.readLine().split(" ");
            String[] prices = reader.readLine().split(" ");
            String[] counts = reader.readLine().split(" ");
            prices1 = new int[products.length];
            counts1 = new int[products.length];
            for (int i = 0; i < products.length; i++) {
                prices1[i] = Integer.parseInt(prices[i]);
                counts1[i] = Integer.parseInt(counts[i]);
            }
            basket = new Basket(products, prices1);
            for (int i = 0; i < products.length; i++) {
                basket.addToCart(i, counts1[i]);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return basket;
    }

}
