package ru.clevertec.house.util;

import org.springframework.stereotype.Component;
import ru.clevertec.house.model.entity.House;
import ru.clevertec.house.model.entity.Person;

import java.lang.reflect.Field;

@Component
public class Patcher {

    public static void personPatcher(Person existingPerson, Person incompletePerson) throws IllegalAccessException {
        Class<?> personClass = Person.class;
        Field[] personFields = personClass.getDeclaredFields();
        for (Field field : personFields) {
            field.setAccessible(true);
            Object value = field.get(incompletePerson);
            if (value != null) {
                field.set(existingPerson, value);
            }
            field.setAccessible(false);
        }
    }

    public static void housePatcher(House existingHouse, House incompleteHouse) throws IllegalAccessException {
        Class<?> houseClass = House.class;
        Field[] houseFields = houseClass.getDeclaredFields();
        for (Field field : houseFields) {
            field.setAccessible(true);
            Object value = field.get(incompleteHouse);
            if (value != null) {
                field.set(existingHouse, value);
            }
            field.setAccessible(false);
        }
    }
}
