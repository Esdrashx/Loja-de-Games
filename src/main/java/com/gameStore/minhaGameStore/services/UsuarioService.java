package com.gameStore.minhaGameStore.services;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gameStore.minhaGameStore.dtos.UsuarioDTO;
import com.gameStore.minhaGameStore.dtos.UsuarioLoginDTO;
import com.gameStore.minhaGameStore.model.Usuario;
import com.gameStore.minhaGameStore.repository.UsuarioRepository;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repositoryU;
	
	public Optional<Object> cadastrarUsuario(Usuario novoUsuario) {

		return repositoryU.findByEmail(novoUsuario.getEmail()).map(usuarioExistente -> {
			return Optional.empty();
		}).orElseGet(() -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			String senhaEncoder = encoder.encode(novoUsuario.getSenha());
			novoUsuario.setSenha(senhaEncoder);

			return Optional.ofNullable(repositoryU.save(novoUsuario));
		});
	}

	public Optional<Usuario> atualizarUsuario(Long idUsuario, UsuarioDTO usuarioParaAtualizar) {
		return repositoryU.findById(idUsuario).map(usuarioExistente -> {
			usuarioExistente.setNome(usuarioParaAtualizar.getNome());
			usuarioExistente.setSenha(usuarioParaAtualizar.getSenha());
			return Optional.ofNullable(repositoryU.save(usuarioExistente));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}

	// metodo para um usuário criar um grupo
	/*public Optional<Grupo> criarGrupo(Long idUsuario, Grupo grupoParaSerCriado) {
		Optional<Object> grupoJaExistente = service.verificarGrupo(grupoParaSerCriado);

		if (grupoJaExistente.isPresent()) {
			return repositoryU.findById(idUsuario).map(usuarioExistente -> {
				grupoParaSerCriado.setCriador(usuarioExistente);
				return Optional.ofNullable(repositoryG.save(grupoParaSerCriado));
			}).orElseGet(() -> {
				return Optional.empty();
			});

		} else {
			return Optional.empty();

		}

	}
*/


	public Optional<UsuarioLoginDTO> logar(Optional<UsuarioLoginDTO> user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repositoryU.findByEmail(user.get().getEmail());

		if (usuario.isPresent()) {
			if (encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
				String auth = user.get().getEmail() + ":" + user.get().getSenha();
				byte[] encoderAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encoderAuth);

				user.get().setToken(authHeader);
				user.get().setEmail(usuario.get().getEmail());

				return user;
			} else {
				return null;
			}

		} else {
			return null;

		}

	}

}


