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

	@Transactional(readOnly = false)
	public void editar(Medico medico) {
		// variavel persistente no JPA
		Medico m2 = medicoRepository.findById(medico.getId()).get();
		m2.setCrm(medico.getCrm());
		m2.setDtInscricao(medico.getDtInscricao());
		m2.setAgendamentos(medico.getAgendamentos());
		
		m2.setNome(medico.getNome());
		// as especialidades sao opcionais a cadastrar no envio do formulário
		if (!medico.getEspecialidades().isEmpty()) { // se ela n estiver vazio o usuario digitou uma especialidade
			m2.getEspecialidades().addAll(medico.getEspecialidades());
		}
	}

}
