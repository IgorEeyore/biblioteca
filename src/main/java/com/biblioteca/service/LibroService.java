package com.biblioteca.service;


import com.biblioteca.model.Libro;
import com.biblioteca.repository.LibroRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// Servicios
@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> getBooks(){
        return libroRepository.getAllBooks();
    }

    public Libro saveBook(Libro libro){
        return libroRepository.save(libro);
    }

    public Libro getBookById(int id){
        return libroRepository.getBookById(id);
    }

    public Libro getBookByIsbn(String isbn){
        return libroRepository.getBookByIsbn(isbn);
    }

    public Libro updateBook(Libro libro){
        return libroRepository.updateBook(libro);
    }

    public String deleteBook(int id){
        libroRepository.deleteBook(id);
        return "Producto eliminado";
    }

    // Primera forma que retorna la cantidad de los libros
    // Esta accion la hace el SERVICE
    public int totalBooksV1(){
        return libroRepository.getAllBooks().size();
    }

    public long totalBookYear(int fechaPublicacion){
        return libroRepository.getAllBooks().stream().filter(libro -> libro.getFechaPublicacion() == fechaPublicacion).count();
    }

    public Optional<Libro> getOldBook(){
        return libroRepository.getAllBooks().stream().min(Comparator.comparingInt(Libro::getFechaPublicacion));
    }

    public Map<Integer, Long> countBookByYearGrouped(){
        return libroRepository.getAllBooks().stream().collect(Collectors.groupingBy(Libro::getFechaPublicacion, Collectors.counting()));
    }
}