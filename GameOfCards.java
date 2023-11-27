// Write a Program DeckOfCards.java, to initialize deck of cards having suit
// ("Clubs", "Diamonds", "Hearts", "Spades") & Rank ("2", "3", "4", "5", "6", "7", 
// "8","9", "10", "Jack", "Queen", "King", "Ace"). Shuffle the cards using Random
// method and then distribute 9 Cards to 4 Players and Print the Cards received by
// the 4 Players using 2D Array. Extend the above program to create a Player 
// Object having Deck of Cards, and having ability to Sort by Rank and maintain the 
// cards in a Queue implemented using Linked List. Do not use any Collection Library.
// Further the Players are also arranged in Queue. Finally Print the Player and the 
// Cards received by each Player.

import java.util.Random;

class Card {
    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public String toString() {
        return rank + " of " + suit;
    }
}

class DeckOfCards {
    private Card[] deck;
    private int currentCard;
    private final String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
    private final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

    public DeckOfCards() {
        deck = new Card[52];
        currentCard = 0;

        int cardCount = 0;
        for (String suit : suits) {
            for (String rank : ranks) {
                deck[cardCount] = new Card(suit, rank);
                cardCount++;
            }
        }
    }

    public void shuffle() {
        Random random = new Random();
        for (int i = deck.length - 1; i > 0; i--) {
            int randIndex = random.nextInt(i + 1);
            Card temp = deck[i];
            deck[i] = deck[randIndex];
            deck[randIndex] = temp;
        }
    }

    public Card[] deal(int numCards) {
        if (currentCard + numCards > deck.length) {
            return null; // Return null if there are not enough cards in the deck
        }

        Card[] dealtCards = new Card[numCards];
        for (int i = 0; i < numCards; i++) {
            dealtCards[i] = deck[currentCard];
            currentCard++;
        }
        return dealtCards;
    }
}

class Player {
    private Queue cards;

    public Player() {
        cards = new Queue();
    }

    public void receiveCards(Card[] receivedCards) {
        for (Card card : receivedCards) {
            cards.enqueue(card);
        }
    }

    public void sortCardsByRank() {
        cards.sortByRank();
    }

    public Queue getCards() {
        return cards;
    }
}

class Queue {
    private Node front;
    private Node rear;

    private class Node {
        Card card;
        Node next;

        Node(Card card) {
            this.card = card;
            next = null;
        }
    }

    public Queue() {
        front = null;
        rear = null;
    }

    public void enqueue(Card card) {
        Node newNode = new Node(card);
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

	public Card peek() {
        if (front == null) {
            return null;
        }
		Card dequeued = front.card;
        return dequeued;
	}

    public Card dequeue() {
        if (front == null) {
            return null;
        }

        Card dequeued = front.card;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        return dequeued;
    }

    public void sortByRank() {
        if (front == null || front.next == null) {
            return;
        }

        Node current = front;
        while (current != null) {
            Node index = current.next;
            while (index != null) {
                if (compareRank(current.card, index.card) > 0) {
                    Card temp = current.card;
                    current.card = index.card;
                    index.card = temp;
                }
                index = index.next;
            }
            current = current.next;
        }
    }

    private int compareRank(Card card1, Card card2) {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        int rank1 = 0;
        int rank2 = 0;

        for (int i = 0; i < ranks.length; i++) {
            if (card1.getRank().equals(ranks[i])) {
                rank1 = i;
            }
            if (card2.getRank().equals(ranks[i])) {
                rank2 = i;
            }
        }
        return Integer.compare(rank1, rank2);
    }
}

public class GameOfCards {
    public static void main(String[] args) {
        DeckOfCards deck = new DeckOfCards();
        deck.shuffle();

        Player[] players = new Player[4];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }

        int cardsPerPlayer = 9;
        for (int i = 0; i < cardsPerPlayer; i++) {
            for (Player player : players) {
                Card[] cards = deck.deal(1);
                player.receiveCards(cards);
            }
        }

        for (Player player : players) {
            player.sortCardsByRank();
            Queue playerCards = player.getCards();
            System.out.println("Player's cards:");
            while (playerCards.peek() != null) {
                System.out.println(playerCards.dequeue());
            }
            System.out.println();
        }
    }
}
