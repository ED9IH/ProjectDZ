package entity;


import lombok.Data;


@Data
public class Person {

    private long id;

    private String name;

    private static Person person;


    public Person() {
    }

    public static Person getInstance() {
        if (person == null) {
            synchronized (Person.class) {
                if (person == null) person = new Person();
            }

        }
        return person;
    }


}



