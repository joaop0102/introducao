package io.github.fatec.repository;

import io.github.fatec.entity.Cliente;
import io.github.fatec.repository.adapter.ClienteRepositoryAdapter;
import io.github.fatec.repository.mongo.ClienteRepositoryWithMongoDB;
import io.github.fatec.repository.orm.ClienteOrmMongo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteRepositoryWithMongoDB repository;

    public ClienteRepositoryImpl(ClienteRepositoryWithMongoDB repository) {
        this.repository = repository;
    }

    @Override
    public Cliente salve(Cliente cliente) {
        ClienteOrmMongo orm = ClienteRepositoryAdapter.castEntity(cliente);
        ClienteOrmMongo ormSave = repository.save(orm);
        return ClienteRepositoryAdapter.castOrm(ormSave);
    }

    @Override
    public Optional<Cliente> buscar(String id) {
        Optional<ClienteOrmMongo> orm = repository.findById(id);

        if(orm.isPresent()){
            Cliente cliente = ClienteRepositoryAdapter.castOrm(orm.get());
            return Optional.of(cliente);
        }
        return Optional.empty();
    }

    @Override
    public List<Cliente> listar(){
        List<ClienteOrmMongo> listaOrm = repository.findAll();
        return listaOrm.stream().map(ClienteRepositoryAdapter::castOrm).toList();
    }

    @Override public void deletar(String id){
        repository.deleteById(id);
    }

}
