package david.augusto.luan.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import david.augusto.luan.domain.Medico;

@Controller
@RequestMapping("medicos")
public class MedicoController {

	// abrir pagina de dados pessoais de medicos pelo MEDICO
		@GetMapping({"/dados"})
		public String abriPorMedico(Medico medico, ModelMap model) {
		
			return "medico/cadastro"; 
		}
		
		// salvar medico
		@GetMapping({"/salvar"})
		public String salvar(Medico medico, RedirectAttributes attr) {
		
			return "medico/cadastro"; 
		}
		
		// editar medico
		@GetMapping({"/editar"})
		public String editar(Medico medico, RedirectAttributes attr) {
		
			return "medico/cadastro"; 
		}

}
