package com.biblioteca.controller;

import com.biblioteca.model.Libro;
import com.biblioteca.service.LibroService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
// Estos imports son para el crud de la maquina
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

// Estos para los @ antes de la clase
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Estos para los parametros en los metodos del crud
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

// Para dar una respuesta solida a una solicitud
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/libros") // Direccion URL del "localhost" acompañado con el puerto, ej: "localhost:8080/api/v1/libros"
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    // Se agrega un '?' diciendo que no se sabe que va a devolver de manera estricta, si no, que
    // va a devolver algo y solo yo lo se
    public ResponseEntity<?> getBooks(){
        List<Libro> libros = libroService.getBooks();
        Map<String,Object> response = new HashMap<>();
        
        if (libros == null || libros.isEmpty()){
            response.put("timestamp", LocalDateTime.now());
            response.put("status", HttpStatus.NO_CONTENT.value());
            response.put("message", "No hay libros registrados");
            response.put("data", null);
            return ResponseEntity.ok(response);
        }
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.OK.value());
        response.put("data", libros);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}") // Cada vez que se pone el id se requiere un @Path como parametro
    public Libro getBookById(@PathVariable int id){
        return libroService.getBookById(id);
    }

    @PostMapping // El @RequestBody exige como parametro un documento JSON en el postman donde se ingresan los datos correspondientes
    public Libro saveBook(@RequestBody Libro libro){
        return libroService.saveBook(libro);
    }

    @PutMapping("{id}") // En este caso se usan ambos porque el metodo es para actualizar un dato, se requiere el @path para escoger la id a editar
                        // y luego el @request para colocar los datos que van a reemplazar los antiguos
    public Libro updateBook(@PathVariable int id, @RequestBody Libro libro){
        return libroService.updateBook(libro);
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable int id){
        return libroService.deleteBook(id);
    }

    @GetMapping("/total")
    public int totalBooksV2(){
        return libroService.totalBooksV2();
    }

    //@PathVariable para obtener un id exacto
    //@RequestBody para ingresar un documento JSON, sirve a la hora de ingresar un nuevo registro de datos

    //@GetMapping para acceder a un dato de la lista, se necesita el parametro del id correspondiente que se obtiene con el @PathVariable como parametro
    //@PostMapping para ingresar datos a la hora de hacer una consulta, se requiere el @RequestBody
    //@PutMapping para la edicion de un dato, en los parametros de este se requiere el @PathVariable y @RequestBody
    //@DeleteMapping para la eliminacion de un dato, requiere el @PathVariable
}
