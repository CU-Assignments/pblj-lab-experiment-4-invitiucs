import java.util.*;

class Card {
    String suit;
    String rank;

    Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return Objects.equals(rank, card.rank) && Objects.equals(suit, card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}

public class CardCollectionSystem {
    private static Map<String, Set<Card>> cardMap = new HashMap<>();

    public static void addCard(String rank, String suit) {
        cardMap.putIfAbsent(suit, new HashSet<>());
        Card card = new Card(rank, suit);
        if (cardMap.get(suit).contains(card)) {
            System.out.println("Error: Card \"" + card + "\" already exists.");
        } else {
            cardMap.get(suit).add(card);
            System.out.println("Card added: " + card);
        }
    }

    public static void findCardsBySuit(String suit) {
        if (cardMap.containsKey(suit) && !cardMap.get(suit).isEmpty()) {
            cardMap.get(suit).forEach(System.out::println);
        } else {
            System.out.println("No cards found for " + suit + ".");
        }
    }

    public static void displayAllCards() {
        if (cardMap.isEmpty() || cardMap.values().stream().allMatch(Set::isEmpty)) {
            System.out.println("No cards found.");
        } else {
            cardMap.values().forEach(set -> set.forEach(System.out::println));
        }
    }

    public static void removeCard(String rank, String suit) {
        if (cardMap.containsKey(suit) && cardMap.get(suit).remove(new Card(rank, suit))) {
            System.out.println("Card removed: " + rank + " of " + suit);
        } else {
            System.out.println("Error: Card \"" + rank + " of " + suit + "\" not found.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Add Card\n2. Find Cards by Suit\n3. Display All Cards\n4. Remove Card\n5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Card Rank: ");
                    String rank = scanner.nextLine();
                    System.out.print("Enter Card Suit: ");
                    String suit = scanner.nextLine();
                    addCard(rank, suit);
                    break;
                case 2:
                    System.out.print("Enter Suit to Find: ");
                    String findSuit = scanner.nextLine();
                    findCardsBySuit(findSuit);
                    break;
                case 3:
                    displayAllCards();
                    break;
                case 4:
                    System.out.print("Enter Card Rank to Remove: ");
                    String removeRank = scanner.nextLine();
                    System.out.print("Enter Card Suit to Remove: ");
                    String removeSuit = scanner.nextLine();
                    removeCard(removeRank, removeSuit);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
