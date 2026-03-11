package io.github.fatec.repository;

import io.github.fatec.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Cliente salve(Cliente cliente);
    Optional<Cliente> buscar(String id);
    List<Cliente> listar();
    void deletar(String id);
}