package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private long id;

    private String nameBook;

    private static final class BookHolder {
        static final Book book = new Book();
    }

    public static Book getInstance(){
        return BookHolder.book;
    }


}
