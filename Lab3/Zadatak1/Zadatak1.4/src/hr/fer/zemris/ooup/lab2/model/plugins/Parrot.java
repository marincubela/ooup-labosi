package hr.fer.zemris.ooup.lab2.model.plugins;

import hr.fer.zemris.ooup.lab2.model.Animal;

public class Parrot extends Animal {

    private String animalName;

    public Parrot(String name) {
		this.animalName = name;
    }

    @Override
    public String name() {
        return animalName;
    }

    @Override
    public String greet() {
        return "Pozdrav! Ja sam papiga.";
    }

    @Override
    public String menu() {
        return "Pticja hrana.";
    }
}