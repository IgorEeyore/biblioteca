package com.biblioteca.repository;

import java.util.ArrayList;
import java.util.List;

import com.biblioteca.model.Libro;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

@Repository
public class LibroRepository {

    // Arreglo que va a guardar mis libros
    private List<Libro> listaLibros = new ArrayList<>();

    // Metodo para inicializar los datos ficticios
    @PostConstruct
    public void init(){
        listaLibros.add(new Libro(1,"978-0134685991","Effective Java","Addison-wesley",2018,"Joshua Bloch"));
        listaLibros.add(new Libro(2,"978-1617294956","Spring in action","Manning",2020,"Craig Walls"));
        listaLibros.add(new Libro(3,"978-1491950357","Design Data-Intensive Application","O'Reilly",2017,"Martin Kleppmann"));
        listaLibros.add(new Libro(4,"978-0132350884","Clean Code","Prentice Hall",2008,"Robert C.Martin"));
        listaLibros.add(new Libro(5,"978-9569646638", "Fuego y Sangre", "Penguin Random House Grupo Editorial", 2018, "George R. R. Martin"));
        listaLibros.add(new Libro(6,"978-9563494150", "Quique Hache: El Mall Embrujado y Otras Historias", "Sm Ediciones", 2014, "Sergio Gomez"));
        listaLibros.add(new Libro(7,"978-1484256251", "Spring Boot Persistence Best Practices", "Apress", 2020, "Anghel Leonard"));
        listaLibros.add(new Libro(8,"978-9566075752", "Harry Potter y la piedra filosofal", "Salamandra", 2024, "J. K. Rowling"));
        listaLibros.add(new Libro(9,"978-0439139601", "Harry Potter y el prisionero de Azkaban", "Scholastic", 1999, "J. K. Rowling"));
        listaLibros.add(new Libro(10,"978-0439136365", "Harry Potter y el cáliz de fuego", "Scholastic", 2000, "J. K. Rowling"));
        listaLibros.add(new Libro(11,"978-0321127426", "Effective Java", "Addison - Wesley", 2008, "Joshua Bloch"));
        listaLibros.add(new Libro(12,"978-0134685991", "Clean Architecture", "Prentice Hall", 2017, "Robert C. Martin"));
        listaLibros.add(new Libro(13,"978-0201633610", "Design Patterns", "Addison - Wesley", 1994, "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides"));
        listaLibros.add(new Libro(14,"978-0132350884", "Clean Code", "Prentice Hall",2008, "Robert C. Martin"));
    }

    // Metodo que retorna todos los libros
    public List<Libro> getAllBooks(){
        return listaLibros;
    }

    // Metodo que retorna un libro por su ID
    public Libro getBookById(int id){
        for(Libro libro : listaLibros){
            if (libro.getId() == id) {
                return libro;
            }
        }
        return null;
    }

    // Metodo que retorna un libro por su isbn
    public Libro getBookByIsbn(String isbn){
        for(Libro libro : listaLibros){
            if (libro.getIsbn().equals(isbn)) { // Al ser un String no es '==', ahi se usa .equals()
                return libro;
            }
        }
        return null;
    }

    // Metodo para guardar un libro
    public Libro save(Libro lib){
        listaLibros.add(lib);
        return lib;
    }

    // Metodo para actualizar un libro
    public Libro updateBook(Libro lib){
        int id = 0;
        int idPosicion = 0;

        // Primero se busca el libro para tener el id y la posicion
        for(int i = 0;i<listaLibros.size();i++){
            if(listaLibros.get(i).getId() == lib.getId()){
                id = lib.getId();
                idPosicion = i;
            }
        }
        // Se crea una nueva instancia con el nuevo libro, se mantiene el id y se reemplaza el resto por lo nuevo
        Libro libro1 = new Libro();
        libro1.setId(id);
        libro1.setTitulo(lib.getTitulo());
        libro1.setAutor(lib.getAutor());
        libro1.setFechaPublicacion(lib.getFechaPublicacion());
        libro1.setEditorial(lib.getEditorial());
        libro1.setIsbn(lib.getIsbn());

        // Se reemplaza en la lista en la misma posicion y se entrega el libro recien actualizado
        listaLibros.set(idPosicion,libro1);
        return libro1;
    }

    // Metodo para eliminar un libro
    public void deleteBook(int id){
        
        // Primera forma de hacer esto
        Libro libro = getBookById(id);
        if (libro != null) {
            listaLibros.remove(libro);
        }

        // Segunda forma de hacerlo
        int idPosicion = 0;
        for(int i = 0;i<listaLibros.size();i++){
            if(listaLibros.get(i).getId() == id){
                idPosicion = i;
                break;
            }
        }
        if(idPosicion > 0){
            listaLibros.remove(idPosicion);
        }

        // Tercera forma de hacerlo
        listaLibros.removeIf(x -> x.getId() == id);
    }

    // Contamos la cantidad de libros que hay en la lista
    public int totalBooks(){
        return listaLibros.size();
    }
}
