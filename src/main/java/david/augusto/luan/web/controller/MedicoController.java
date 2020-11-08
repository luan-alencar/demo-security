package david.augusto.luan.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import david.augusto.luan.domain.Medico;
import david.augusto.luan.service.MedicoService;

@Controller
@RequestMapping("medicos")
public class MedicoController {

	@Autowired
	private MedicoService service;

	// abrir pagina de dados pessoais de medicos pelo MEDICO
	@GetMapping({ "/dados" })
	public String abriPorMedico(Medico medico, ModelMap model) {

		return "medico/cadastro";
	}

	// salvar medico
	@GetMapping({ "/salvar" })
	public String salvar(Medico medico, RedirectAttributes attr) {
		service.salvar(medico);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		// retorna para pag uma variavel medico cm o objeto medico
		// quando o formulario for re-aberto cm a resposta ele javai estar com o
		// formulario preenchido
		attr.addFlashAttribute("medico", medico);
		return "redirect:/medico/dados";
	}

	// editar medico
	@GetMapping({ "/editar" })
	public String editar(Medico medico, RedirectAttributes attr) {
		service.editar(medico);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		// retorna para pag uma variavel medico cm o objeto medico
		// quando o formulario for re-aberto cm a resposta ele javai estar com o
		// formulario preenchido
		attr.addFlashAttribute("medico", medico);
		return "redirect:/medico/dados";
	}

}
