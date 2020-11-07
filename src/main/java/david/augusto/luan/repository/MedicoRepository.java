package david.augusto.luan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import david.augusto.luan.domain.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

	@Query("select m from Usuario m where m.usuario.id = :id")
	Optional<Medico> findByUsuarioId(@Param("id") Long id);

}
