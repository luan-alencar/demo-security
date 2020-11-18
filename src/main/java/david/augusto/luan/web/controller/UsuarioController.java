package david.augusto.luan.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import david.augusto.luan.domain.Medico;
import david.augusto.luan.domain.Perfil;
import david.augusto.luan.domain.PerfilTipo;
import david.augusto.luan.domain.Usuario;
import david.augusto.luan.service.MedicoService;
import david.augusto.luan.service.UsuarioService;

@Controller
@RequestMapping("u")
public class UsuarioController {

	/*
	 * Uma excessão de status 500 iNTERNAL SERVER ERROR envia uma requisição, essa
	 * requisição é aceita pela aplicação, então a requisição chega nos Controllers
	 * e entra nos Controllers e assim cada chamada de m[etodo lá dentro vai
	 * chamando cada parte da aplicação então a requisição.
	 */

	@Autowired
	private UsuarioService service;

	@Autowired
	private MedicoService serviceMedico;

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
	@PostMapping("/cadastro/salvar")
	public String salvarUsuarios(Usuario usuario, RedirectAttributes redirect) {
		List<Perfil> perfis = usuario.getPerfis();
		// se for maior q dois ja sabe que esta cadastrando 3 perfis e nao pode
		if (perfis.size() > 2 || perfis.containsAll(Arrays.asList(new Perfil(1L), new Perfil(3L)))
				|| perfis.containsAll(Arrays.asList(new Perfil(2L), new Perfil(3L)))) {
			redirect.addFlashAttribute("falha", "Paciente não pode ser Admin e/ou Médico");
			redirect.addFlashAttribute("usuario", usuario);

		} else {
			try {
				service.salvarUsuario(usuario);
				redirect.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
			} catch (DataAccessException e) {
				redirect.addFlashAttribute("falha", "Cadastro não realizado, email já existente!");
			}

		}
		return "redirect:/u/novo/cadastro/usuario"; // vai direcionar ao primeiro metodo
	}

	// pre edição de credenciais de usuarios
	@GetMapping("/editar/credenciais/usuario/{id}")
	public ModelAndView preEditarCredenciais(@PathVariable("id") Long id) {

		return new ModelAndView("usuario/cadastro", "usuario", service.buscarPorId(id));
	}

	/*
	 * Pre edição de credenciais de usuarios
	 * 
	 * este metodo vai conter duas variaveis : - id : referente ao id do usuario -
	 * perfisId : do tipo array para long que vai conter um array de perfis
	 */
	@GetMapping("/editar/dados/usuario/{id}/perfis/{perfis}")
	public ModelAndView preEditarCadastroDadosPessoais(@PathVariable("id") Long usuarioId,
			@PathVariable("perfis") Long[] perfisId) {

		Usuario us = service.buscarPorIdEPerfil(usuarioId, perfisId);

		// Eu tenho que ser ADMIN
		if (us.getPerfis().contains(new Perfil(PerfilTipo.ADMIN.getCod()))
				// mas não posso ser MEDICO
				&& !us.getPerfis().contains(new Perfil(PerfilTipo.MEDICO.getCod()))) {
			// Se o acesso cair nesse IF então o usuario é apenas ADMIN
			return new ModelAndView("usuario/cadastro", "usuario", us);

			// Basta ele ser apenas medico para cair aqui
		} else if (us.getPerfis().contains(new Perfil(PerfilTipo.MEDICO.getCod()))) {

			// consulta
			Medico medico = serviceMedico.buscarPorUsuarioId(usuarioId);
			return medico.hasNotId() ? new ModelAndView("medico/cadastro", "medico", new Medico(new Usuario(usuarioId)))
					: new ModelAndView("medico/cadastro", "medico", medico);

			// Aqui na parte de paciente nós nao vamos enviar a requisição para a área de
			// paciente
		} else if (us.getPerfis().contains(new Perfil(PerfilTipo.PACIENTE.getCod()))) {
			ModelAndView model = new ModelAndView("error");
			model.addObject("status", 403); // vai retornar o cod do status
			model.addObject("error", "Área restrita!");
			model.addObject("message", "Os dados de pacientes são restritos a ele.");
			return model;
		}

		return new ModelAndView("redirect:/u/lista");
	}

	@GetMapping("/editar/senha")
	public String abrirEditarSenha(@PathVariable("id") Long id) {

		return "usuario/editar-senha";
	}

	@PostMapping("/confirmar/senha")
	public String editarSenha(@RequestParam("senha1") String s1, @RequestParam("senha2") String s2,
			@RequestParam("senha3") String s3, @AuthenticationPrincipal User user, RedirectAttributes attr) {

		if (!s1.equals(s2)) {
			attr.addFlashAttribute("falha", "Senhas não conferem, tente novamente"); // caso a senha não confere então
																						// vai redirecionar a url de
																						// editar senha
			return "redirect:/u/editar/senha";
		}

		Usuario u = service.buscarPorEmail(user.getUsername());
		if (!UsuarioService.isSenhaCorreta(s3, u.getSenha())) {
			attr.addFlashAttribute("falha", "Senha atual não confere, tente novamente");
			return "redirect:/u/editar/senha";
		}

		// caso de tudo certo e não entre em if algum então vai redefinir a nova senha
		service.alterarSenha(u, s1);
		attr.addFlashAttribute("sucesso", "Senha alterada com sucesso.");
		return "redirect:/u/editar/senha";
	}

}
