package br.com.guriposa.exercicio1DevSuperior.repositories;

import br.com.guriposa.exercicio1DevSuperior.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
