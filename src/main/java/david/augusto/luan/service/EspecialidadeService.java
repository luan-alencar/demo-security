package david.augusto.luan.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import david.augusto.luan.datatables.Datatable;
import david.augusto.luan.datatables.DatatablesColunas;
import david.augusto.luan.domain.Especialidade;
import david.augusto.luan.repository.EspecialidadeRepository;

@Service
public class EspecialidadeService {

	@Autowired
	private EspecialidadeRepository repository;
	
	@Autowired
	private Datatable datatables;

	@Transactional(readOnly = false)
	public void salvar(Especialidade especialidade) {
		repository.save(especialidade);
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarEspecialidades(HttpServletRequest request) {
		datatables.setRequest(request); // estou passsando o obj request para o Datatable
		datatables.setColunas(DatatablesColunas.ESPECIALIDADES); // constante que é para as colunas na Datatable
		Page<?> page = datatables.getSearch().isEmpty()
				? repository.findAll(datatables.getPageable())
				: repository.findAllByTitulo(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}
}
