package david.augusto.luan.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import david.augusto.luan.domain.Medico;
import david.augusto.luan.domain.Usuario;
import david.augusto.luan.service.MedicoService;
import david.augusto.luan.service.UsuarioService;

@Controller
@RequestMapping("medicos")
public class MedicoController {

	@Autowired
	private MedicoService service;

	@Autowired
	private UsuarioService usuarioService;

	// abrir pagina de dados pessoais de medicos pelo MEDICO
	@GetMapping({ "/dados" })
	public String abriPorMedico(Medico medico, ModelMap model, @AuthenticationPrincipal User user) {
		// se ele não tem ID ele clicou no link
		if (medico.hasNotId()) {
			medico = service.buscarPorEmail(user.getUsername());
			model.addAttribute("medico", medico);
		}
		return "medico/cadastro";
	}

	// salvar medico
	@PostMapping({ "/salvar" })
	public String salvar(Medico medico, RedirectAttributes attr, @AuthenticationPrincipal User user // recursos do
																									// spring security
	) {
		// se o médico nãotem id e nem o usuario tem id significa que o insert ta sendo
		// pelo login de médico e tem que buscar pelo id de usuario usando username
		// desse medico
		if (medico.hasNotId() && medico.getUsuario().hasNotId()) {
			Usuario usuario = usuarioService.buscarPorEmail(user.getUsername());
			medico.setUsuario(usuario);
		}
		service.salvar(medico);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		// retorna para pag uma variavel medico cm o objeto medico
		// quando o formulario for re-aberto cm a resposta ele javai estar com o
		// formulario preenchido
		attr.addFlashAttribute("medico", medico);
		return "redirect:/medicos/dados";
	}

	// editar medico
	@PostMapping({ "/editar" })
	public String editar(Medico medico, RedirectAttributes attr) {
		service.editar(medico);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		attr.addFlashAttribute("medico", medico);
		return "redirect:/medicos/dados";
	}

	// excluir especialidades
	@GetMapping({ "/id/{idMed}/excluir/especializacao/{idEsp}" })
	public String excluir(@PathVariable("idMed") Long idMed, @PathVariable("idEsp") Long idEsp,
			RedirectAttributes attr) {
		service.excluirEspecialidadePorMedico(idMed, idEsp);
		attr.addFlashAttribute("sucesso", "Especialidade removida com sucesso!");
		return "redirect:/medicos/dados";
	}
}
