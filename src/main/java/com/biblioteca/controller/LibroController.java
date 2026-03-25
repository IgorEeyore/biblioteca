package com.biblioteca.controller;

import com.biblioteca.model.Libro;
import com.biblioteca.service.LibroService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    Map<String,Object> response = new HashMap<>();

    // Se agrega un '?' diciendo que no se sabe que va a devolver de manera estricta, si no, que
    // va a devolver algo y solo yo lo se
    @GetMapping
    public ResponseEntity<?> getBooks(){
        List<Libro> libros = libroService.getBooks();
        
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
    public ResponseEntity<?> getBookById(@PathVariable int id){

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.OK.value());
        response.put("data", libroService.getBookById(id));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<?> getBookByIsbn(@PathVariable String isbn) {

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.OK.value());
        response.put("data", libroService.getBookByIsbn(isbn));

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/anio/{fechaPublicacion}")
    public ResponseEntity<?> getBookByYear(@PathVariable int fechaPublicacion) {

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.OK.value());
        response.put("anio", fechaPublicacion);
        response.put("total", libroService.totalBookYear(fechaPublicacion));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/antiguo")
    public ResponseEntity<?> getOldBook(){
        Optional<Libro> oldBook = libroService.getOldBook();

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.OK.value());
        if (oldBook.isPresent()) {
            response.put("message", "Libro mas antiguo encontrado");
            response.put("data", oldBook.get());
            response.put("anio_publicacion", oldBook.get().getFechaPublicacion());
        }else{
            response.put("message", "No hay libros registrados");
            response.put("data", null);
        }

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/estadisticas/anios")
    public ResponseEntity<?> getBooksStatic(){
        Map<Integer, Long> estadisticas = libroService.countBookByYearGrouped();

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.OK.value());
        response.put("estadisticas", estadisticas);

        return ResponseEntity.ok(response);
    }

    @PostMapping // El @RequestBody exige como parametro un documento JSON en el postman donde se ingresan los datos correspondientes
    public ResponseEntity<?> saveBook(@RequestBody Libro libro){

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.OK.value());
        response.put("data", libroService.saveBook(libro));

        return ResponseEntity.ok(response);
    }

    // En este caso se usan ambos porque el metodo es para actualizar un dato, se requiere el @path para escoger la id a editar
    // y luego el @request para colocar los datos que van a reemplazar los antiguos
    @PutMapping("{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id, @RequestBody Libro libro){

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.OK.value());
        response.put("data", libroService.updateBook(libro));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id){

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.OK.value());
        response.put("data", libroService.deleteBook(id));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/total")
    public ResponseEntity<?> totalBooksV2(){

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.OK.value());
        response.put("total", libroService.totalBooksV1());

        return ResponseEntity.ok(response);
    }

    //@PathVariable para obtener un id exacto
    //@RequestBody para ingresar un documento JSON, sirve a la hora de ingresar un nuevo registro de datos

    //@GetMapping para acceder a un dato de la lista, se necesita el parametro del id correspondiente que se obtiene con el @PathVariable como parametro
    //@PostMapping para ingresar datos a la hora de hacer una consulta, se requiere el @RequestBody
    //@PutMapping para la edicion de un dato, en los parametros de este se requiere el @PathVariable y @RequestBody
    //@DeleteMapping para la eliminacion de un dato, requiere el @PathVariable
}