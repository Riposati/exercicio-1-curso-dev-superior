package br.com.guriposa.exercicio1DevSuperior.resources;

import br.com.guriposa.exercicio1DevSuperior.dto.ClientDTO;
import br.com.guriposa.exercicio1DevSuperior.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientDTO>>findAllPaged(

            @RequestParam(value="page", defaultValue = "0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value="direction", defaultValue = "ASC") String direction,
            @RequestParam(value="orderBy", defaultValue = "name") String orderBy

    ) {
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);

        Page<ClientDTO> list = clientService.findAllPaged(pageRequest);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO>findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(clientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClientDTO>insert(@RequestBody ClientDTO categoryDto) {
        categoryDto = clientService.insert(categoryDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{id}")
                .buildAndExpand(categoryDto.getId()).toUri();
        return ResponseEntity.created(uri).body(categoryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO>update(@PathVariable("id") Long id, @RequestBody ClientDTO clientsDto) {
        return ResponseEntity.ok().body(clientService.update(id,clientsDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable("id") Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
