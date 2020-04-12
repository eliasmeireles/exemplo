package com.alura.leilaoservice.controller;

import com.alura.leilaoservice.model.Leilao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LeilaoController {

    private ListaLeilao listaLeilao;

    public LeilaoController() {
        listaLeilao = ListaLeilao.getGetInstance();
    }

    @GetMapping("/")
    public String home() {
        return "Api mock leiloes";
    }

    @GetMapping(value = "/leilao")
    public ResponseEntity<List<Leilao>> listaLeiloes() {
        return new ResponseEntity<>(listaLeilao.leiloes(), HttpStatus.OK);
    }

    @PostMapping(value = "/leilao")
    public ResponseEntity<Leilao> add(@RequestBody Leilao leilao) {
        return new ResponseEntity<>(listaLeilao.add(leilao), HttpStatus.CREATED);
    }

    @GetMapping("/reset")
    public String reset() {
        listaLeilao.reset();
        return "Lista de leilões foi resetada!";
    }
}
