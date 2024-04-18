package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book   {
    private static Book book;

    private long id;

    private String nameBook;

    public Book() {
    }

    public static Book getInstance(){
        if(book==null){
            synchronized (Book.class){
                if(book==null)book=new Book();
            }
        }
        return book;
    }


}
