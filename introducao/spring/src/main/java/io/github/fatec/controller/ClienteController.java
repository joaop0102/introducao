package io.github.fatec.controller;

import io.github.fatec.controller.adapter.ClienteControllerAdapter;
import io.github.fatec.controller.dto.request.ClientRequest;
import io.github.fatec.controller.dto.response.ClienteResponse;
import io.github.fatec.entity.Cliente;
import io.github.fatec.repository.ClienteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ClienteController {

    public final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping("/cliente/cadastrar")
    public ClienteResponse salvar(@RequestBody ClientRequest request) {
        Cliente cliente = ClienteControllerAdapter.castRequest(request);
        Cliente clienteSalvo = clienteRepository.salve(cliente);
        return ClienteControllerAdapter.castResponse(clienteSalvo);
    }

    @GetMapping("/cliente/{id}")
    public ClienteResponse buscar(@PathVariable String id){
        Cliente cliente = clienteRepository.buscar(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return ClienteControllerAdapter.castResponse(cliente);
    }

    @GetMapping("/clientes")
    public List<ClienteResponse> Listar(){
        List<Cliente> clientes = clienteRepository.listar();
        return clientes.stream().map(ClienteControllerAdapter::castResponse).toList();
    }

    @PutMapping("/cliente/atualizar/{id}")
    public ClienteResponse atualizar(@PathVariable String id, @RequestBody ClientRequest request){

        Cliente clienteExistente = clienteRepository.buscar(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Cliente clienteAtualizado = new Cliente(
                clienteExistente.id(),
                request.nome(),
                request.endereco(),
                request.telefone()
        );

        Cliente clienteSalvo = clienteRepository.salve(clienteAtualizado);

        return ClienteControllerAdapter.castResponse(clienteSalvo);
    }

    @DeleteMapping("/cliente/deletar/{id}")
    public void deletar(@PathVariable String id){
        Cliente cliente = clienteRepository.buscar(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        clienteRepository.deletar(id);
    }
}
