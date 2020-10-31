package david.augusto.luan.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import david.augusto.luan.domain.Medico;

@Controller
@RequestMapping("medicos")
public class MedicoController {

	// abrir pagina de dados pessoais de medicos pelo MEDICO
		@GetMapping({"/dados"})
		public String abriPorMedico(Medico medico, ModelMap model) {
		
			return "medico/cadastro"; 
		}
}
