package hr.fer.zemris.ooup.lab2.model.plugins;

import hr.fer.zemris.ooup.lab2.model.Animal;

public class Tiger extends Animal {

    private String animalName;

    public Tiger(String name) {
		this.animalName = name;
    }

    @Override
    public String name() {
        return animalName;
    }

    @Override
    public String greet() {
        return "Pozdrav! Ja sam tigar.";
    }

    @Override
    public String menu() {
        return "Macja hrana.";
    }
}