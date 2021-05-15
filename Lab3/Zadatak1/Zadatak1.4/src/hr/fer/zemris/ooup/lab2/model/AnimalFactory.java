package hr.fer.zemris.ooup.lab2.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class AnimalFactory {
    public static Animal newInstance(String animalKind, String name) {
        Class<Animal> clazz = null;
        Constructor<?> ctr = null;
        Animal animal = null;
        try {
            clazz = (Class<Animal>) Class.forName("hr.fer.zemris.ooup.lab2.model.plugins." + animalKind);
            ctr = clazz.getConstructor(String.class);
            animal = (Animal) ctr.newInstance(name);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return animal;
    }

}
