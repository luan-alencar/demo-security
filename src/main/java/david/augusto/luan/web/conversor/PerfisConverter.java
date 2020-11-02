package david.augusto.luan.web.conversor;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import david.augusto.luan.domain.Perfil;

@Component
public class PerfisConverter implements Converter<String[], List<Perfil>> {

	@Override
	public List<Perfil> convert(String[] source) {
		// TODO Auto-generated method stub
		return null;
	}

}
