package ajude.DAOs;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import ajude.entities.Doacao;

public interface DoacaoRepository<T, ID extends Serializable> extends JpaRepository<Doacao, Long> {

}
