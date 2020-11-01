package david.augusto.luan.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import david.augusto.luan.domain.Especialidade;
import david.augusto.luan.service.EspecialidadeService;

@Controller
@RequestMapping("especialidades")
public class EspecialidadeController {

	@Autowired
	private EspecialidadeService service;

	@GetMapping({ "", "/" })
	public String abrirEspecialidades(Especialidade especialidade) {

		return "especialidade/especialidade";
	}

	@PostMapping("/salvar")
	public String salvar(Especialidade especialdidade, RedirectAttributes attr) {
		service.salvar(especialdidade);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso");
		return "redirect:/especialidades";
	}

	@GetMapping("datatables/server")
	public ResponseEntity<?> getEspecialidades(HttpServletRequest request) {
		return ResponseEntity.ok(service.buscarEspecialidades(request));
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> getEspecialidade(HttpServletRequest request) {
		return ResponseEntity.ok(service.buscarEspecialidades(request));
	}

	
}
