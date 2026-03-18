package com.biblioteca.service;


import com.biblioteca.model.Libro;
import com.biblioteca.repository.LibroRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public Libro updateBook(Libro libro){
        return libroRepository.updateBook(libro);
    }

    public String deleteBook(int id){
        libroRepository.deleteBook(id);
        return "Producto eliminado";
    }

    
}
