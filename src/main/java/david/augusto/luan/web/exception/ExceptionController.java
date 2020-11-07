package david.augusto.luan.web.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice // essa anotação serve como se fosse um ouvindo na sua aplicação
public class ExceptionController {

	// metodo para capturar uma exceção
	@ExceptionHandler(UsernameNotFoundException.class)
	public ModelAndView usuarioNaoEncontradoException(UsernameNotFoundException ex) {
		ModelAndView model = new ModelAndView("error");
		model.addObject("status", 404); // vai retornar o cod do status
		model.addObject("error", "Operação não pode ser realizada.");
		model.addObject("message", ex.getMessage());
		return model;

	}
}
