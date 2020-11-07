package david.augusto.luan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import david.augusto.luan.domain.Medico;
import david.augusto.luan.repository.MedicoRepository;

@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;

	public Medico buscarPorUsuarioId(Long id) {
		
		return medicoRepository.findByUsuarioId(id).orElse(new Medico());
	}

}
