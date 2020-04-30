package com.evolution.config.token;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.evolution.model.seguranca.AcessoUsuario;
import com.evolution.model.seguranca.Usuario;
import com.evolution.repository.seguranca.AcessoUsuarioRepository;
import com.evolution.repository.seguranca.UsuarioRepository;
import com.evolution.security.UsuarioSistema;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AcessoUsuarioRepository acessoUsuarioRepository;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();

		Map<String, Object> addInfo = new HashMap<>();

		if (usuarioSistema.getUsuario().getDataUltimoAcesso() != null) {
			Locale local = new Locale("pt", "BR");
			DateFormat formato = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy 'às' HH:mm", local);
			addInfo.put("dataUltimoAcesso", formato.format(usuarioSistema.getUsuario().getDataUltimoAcesso()));
		} else {
			Date data = new Date();
			Locale local = new Locale("pt", "BR");
			DateFormat formato = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy 'às' HH:mm", local);
			addInfo.put("dataUltimoAcesso", formato.format(data));
		}

		addInfo.put("email", usuarioSistema.getUsuario().getEmail());
		addInfo.put("nome", usuarioSistema.getUsuario().getNome());
		addInfo.put("usuario", usuarioSistema.getUsuario().getId().toString());
		addInfo.put("departamento", usuarioSistema.getUsuario().getDepartamento().getId().toString());
		addInfo.put("quantidadeAcesso", usuarioSistema.getUsuario().getQuantidadeAcesso().toString());

		// ultimo acesso baseado no login
		AcessoUsuario acesso = new AcessoUsuario();
		acesso.setUsuario(usuarioSistema.getUsuario());
		acessoUsuarioRepository.save(acesso);

		Usuario ultimoAcesso = usuarioSistema.getUsuario();
		ultimoAcesso.setDataUltimoAcesso(new Date());
		ultimoAcesso.setQuantidadeAcesso(ultimoAcesso.getQuantidadeAcesso() + 1l);
		usuarioRepository.save(ultimoAcesso);

		addInfo.put("dataToken", accessToken.getExpiration().getTime());

		Object obj = accessToken.getRefreshToken();

		Class<?> classe = obj.getClass();
		Field[] campos = classe.getDeclaredFields();

		for (Field campo : campos) {
			try {
				if (campo.getName().equals("expiration")) {
					campo.setAccessible(true);

					Date dataRefreshToken = (Date) campo.get(obj);

					addInfo.put("dataRefreshToken", dataRefreshToken.getTime());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addInfo);
		return accessToken;
	}

	public void imprimeAtributoseValoresPojo(Object object) {
		Class<?> classe = object.getClass();
		Field[] campos = classe.getDeclaredFields();

		String nomeAtributo = "";
		Object valorAtributo = null;
		for (Field campo : campos) {
			try {
				nomeAtributo = campo.getName();
				campo.setAccessible(true);
				valorAtributo = campo.get(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(nomeAtributo + ": " + valorAtributo);
		}
	}

}
