package david.augusto.luan.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import david.augusto.luan.domain.Perfil;
import david.augusto.luan.domain.Usuario;
import david.augusto.luan.service.UsuarioService;

@Controller
@RequestMapping("u")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	// abrir cadastro de usuarios (medicos/admin/pacientes/)
	@GetMapping("/novo/cadastro/usuario")
	public String cadastroPorAdminParaAdminMedicoAdminPaciente(Usuario usuario) {

		return "usuario/cadastro";
	}

	@GetMapping("/lista")
	public String listarUsuarios() {

		return "usuario/lista";
	}

	@GetMapping("/datatables/server/usuarios")
	public ResponseEntity<?> listarUsuariosDatatables(HttpServletRequest request) {

		return ResponseEntity.ok(service.buscarTodos(request));
	}

	// salvar o cadastro de usuarios por administrador
	@SuppressWarnings("unlikely-arg-type")
	@PostMapping("/cadastro/salvar")
	public String salvarUsuarios(Usuario usuario, RedirectAttributes redirect) {
		List<Perfil> perfis = usuario.getPerfis();
		// se for maior q dois ja sabe que esta cadastrando 3 perfis e nao pode
		if (perfis.size() > 2 || perfis.contains(Arrays.asList(new Perfil(1L), new Perfil(3L)))
				|| perfis.contains(Arrays.asList(new Perfil(2L), new Perfil(3L)))) { 
			redirect.addFlashAttribute("falha", "Paciente não pode ser Admin e/ou Médico");
			redirect.addFlashAttribute("usuario", usuario);
			
		} else {
			service.salvarUsuario(usuario);
			redirect.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		}
		return "redirect:/u/novo/cadastro/usuario"; // vai direcionar ao primeiro metodo	
	}
}
