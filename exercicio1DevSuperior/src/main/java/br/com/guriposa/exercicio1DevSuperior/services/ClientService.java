package br.com.guriposa.exercicio1DevSuperior.services;

import br.com.guriposa.exercicio1DevSuperior.dto.ClientDTO;
import br.com.guriposa.exercicio1DevSuperior.entities.Client;
import br.com.guriposa.exercicio1DevSuperior.repositories.ClientRepository;
import br.com.guriposa.exercicio1DevSuperior.services.exceptions.DatabaseException;
import br.com.guriposa.exercicio1DevSuperior.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> clients = clientRepository.findAll(pageRequest);
        return clients.map(ClientDTO::new);
    }
    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> obj = clientRepository.findById(id);
        Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO insert(ClientDTO clientDto) {
        Client entity = new Client();
        prepareEntityClient(clientDto, entity);
        entity = clientRepository.save(entity);
        return new ClientDTO(entity);
    }


    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDto) {
        try{
            Client entity = clientRepository.getOne(id);
            prepareEntityClient(clientDto, entity);
            entity = clientRepository.save(entity);
            return new ClientDTO(entity);

        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try{
            clientRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }
    private void prepareEntityClient(ClientDTO clientDto, Client entity) {
        entity.setName(clientDto.getName());
        entity.setCpf(clientDto.getCpf());
        entity.setBirthDate(clientDto.getBirthDate());
        entity.setIncome(clientDto.getIncome());
        entity.setChildren(clientDto.getChildren());
    }
}
