package david.augusto.luan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import david.augusto.luan.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.email like :email")
	Usuario findByEmail(@Param("email") String email);
}
