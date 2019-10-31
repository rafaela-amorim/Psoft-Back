package ajude.DAOs;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import ajude.entities.Campanha;


public interface CampanhaRepository<T, ID extends Serializable> extends JpaRepository<Campanha, Long>  {

}
