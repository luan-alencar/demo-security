package david.augusto.luan.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import david.augusto.luan.datatables.Datatable;
import david.augusto.luan.datatables.DatatablesColunas;
import david.augusto.luan.domain.Perfil;
import david.augusto.luan.domain.Usuario;
import david.augusto.luan.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private Datatable datatables;

	// testa se o email ja esta cadastrado
	@Transactional(readOnly = true)
	public Usuario buscarPorEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true) // tudo que tiver dentro desse metodo vai ser gerenciado por uma transacao
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {// recebe uma String como
																								// argumento e ela vai
																								// ser o email q o
																								// usuario digitou no
																								// formulario
		Usuario usuario = buscarPorEmail(username);
		// User e uma classe do spring que implementa UserDetaisl
		return new User(usuario.getEmail(), usuario.getSenha(),
				AuthorityUtils.createAuthorityList(getAuthorities(usuario.getPerfis())));
	}

	private String[] getAuthorities(List<Perfil> perfis) {
		String[] authorities = new String[perfis.size()];
		for (int i = 0; i < perfis.size(); i++) {
			authorities[i] = perfis.get(i).getDesc();
		}
		return authorities;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarTodos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.USUARIOS);
		Page<Usuario> page = datatables.getSearch().isEmpty() ? usuarioRepository.findAll(datatables.getPageable())
				: usuarioRepository.findByEmailOrPerfil(datatables.getSearch(), datatables.getPageable());

		return datatables.getResponse(page);
	}

	@Transactional(readOnly = false)
	public void salvarUsuario(Usuario usuario) {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		// quando o usuario for salvo no banco de dados ele ja sera salvo cm a senha
		// criptografada
		usuarioRepository.save(usuario);
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {

		return usuarioRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorIdEPerfil(Long usuarioId, Long[] perfisId) {

		return usuarioRepository.findByIdAndPerfil(usuarioId, perfisId)
				// se existir um obj Usuario dentro do Optional ele retorna um obj Usuario desde
				// que a consulta tenha retornado dados ao usuario
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não existente"));
	}

	public static boolean isSenhaCorreta(String senhaDigitada, String senhaArmazenada) {

		return new BCryptPasswordEncoder().matches(senhaDigitada, senhaArmazenada);
	}

	@Transactional(readOnly = false)
	public void alterarSenha(Usuario usuario, String senha) {
		// mudando a senha no objeto usuario
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
		// passando o objeto usuario já alterada e criptografada 
		usuarioRepository.save(usuario);
	}
}
