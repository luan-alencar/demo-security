package david.augusto.luan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import david.augusto.luan.domain.Medico;
import david.augusto.luan.repository.MedicoRepository;

@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;

	@Transactional(readOnly = true)
	public Medico buscarPorUsuarioId(Long id) {
		
		return medicoRepository.findByUsuarioId(id).orElse(new Medico());
	}

	@Transactional(readOnly = false)
	public void salvar(Medico medico) {
		medicoRepository.save(medico);
		
	}

	public void editar(Medico medico) {
		// TODO Auto-generated method stub
		
	}

}
