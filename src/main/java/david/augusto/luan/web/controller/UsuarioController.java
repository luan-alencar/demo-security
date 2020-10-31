package david.augusto.luan.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import david.augusto.luan.domain.Usuario;

@Controller
@RequestMapping("u")
public class UsuarioController {

	// abrir cadastro de usuarios (medicos/admin/pacientes/)
	@GetMapping("/novo/cadastro/usuario")
	public String cadastroPorAdminParaAdminMedicoAdminPaciente(Usuario usuario) {
	
		return "usuario/cadastro"; 
	}
}
