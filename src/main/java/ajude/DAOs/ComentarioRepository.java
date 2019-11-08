package ajude.DAOs;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import ajude.entities.Comentario;
import ajude.entities.Usuario;

public interface ComentarioRepository<T, ID extends Serializable> extends JpaRepository<Comentario, Long> {

}
