import sistema.Item;

public class Main {
    public static void main(String[] args) {
        for (Item item : Item.values()) {
            System.out.println(item);
            System.out.println();
        }
    }
}
