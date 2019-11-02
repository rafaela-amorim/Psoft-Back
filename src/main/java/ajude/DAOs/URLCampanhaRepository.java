package ajude.DAOs;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import ajude.entities.Campanha;
import ajude.entities.URLCampanha;

public interface URLCampanhaRepository<T, ID extends Serializable> extends JpaRepository<URLCampanha, String> {

}
