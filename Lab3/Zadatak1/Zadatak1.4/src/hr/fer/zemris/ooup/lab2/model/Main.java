package hr.fer.zemris.ooup.lab2.model;

public class Main {
    public static void main(String[] args) {
        Animal a = AnimalFactory.newInstance(args[0], args[1]);
        a.animalPrintGreeting();
        a.animalPrintMenu();
    }
}
