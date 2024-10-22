package com.relgs.userapi.controller;

import com.github.javafaker.Faker;
import com.relgs.userapi.Factories;
import com.relgs.userapi.dto.UserDTO;
import jakarta.annotation.PostConstruct;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@RestController
@RequestMapping("/user")
public class UserController {

    private List<UserDTO> usuarios = new ArrayList<>();

    private UserDTO userDTO = new UserDTO();

    @PostConstruct
    public void initiateList() {
        IntStream.range(0, 200).forEach(i -> usuarios.add(Factories.generateUserDTO()));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{cpf}")
    public UserDTO getUsersFiltro(@PathVariable String cpf) {
        return usuarios
            .stream()
            .filter(userDTO -> userDTO.getCpf().equals(cpf))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));   
        
    }

    @PostMapping()
    public UserDTO postMethodName(@RequestBody @Valid UserDTO entity) {
        entity.setDataCadastro(LocalDateTime.now());
        usuarios.add(entity);
        return entity;
    }

    @DeleteMapping("/{cpf}")
    public boolean deleteByCpf(@PathVariable String cpf) {
        return usuarios
                .removeIf(userDTO -> userDTO.getCpf().equals(cpf));
    }


    

}
