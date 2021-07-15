package com.gameStore.minhaGameStore.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gameStore.minhaGameStore.dtos.UsuarioDTO;
import com.gameStore.minhaGameStore.dtos.UsuarioLoginDTO;
import com.gameStore.minhaGameStore.model.Usuario;
import com.gameStore.minhaGameStore.repository.UsuarioRepository;
import com.gameStore.minhaGameStore.services.UsuarioService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

	private @Autowired UsuarioRepository repositoryU;

	private @Autowired UsuarioService service;

	@PostMapping("/cadastrar")
	public ResponseEntity<Object> postUsuario(@Valid @RequestBody Usuario novoUsuario) {

		Optional<Object> cadastrarUsuario = service.cadastrarUsuario(novoUsuario);

		if (cadastrarUsuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(novoUsuario.getEmail() + "\nEmail já foi cadastrado anteriormente!");

		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado.");
		}
	}

	@PostMapping("/logar")
	public ResponseEntity<UsuarioLoginDTO> autentication(@RequestBody Optional<UsuarioLoginDTO> user) {
		return service.logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PutMapping("/{id}/atualizar")
	public ResponseEntity<Usuario> putUsuario(@Valid @PathVariable(value = "id") Long id,
			@Valid @RequestBody UsuarioDTO usuarioParaAtualizar) {

		return service.atualizarUsuario(id, usuarioParaAtualizar)
				.map(usuarioAtualizado -> ResponseEntity.ok().body(usuarioAtualizado))
				.orElse(ResponseEntity.badRequest().build());

	}

	@GetMapping("/getAll") // Método para pegar tudo
	public ResponseEntity<List<Usuario>> findAll() {

		return ResponseEntity.ok(repositoryU.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable Long id) {

		return repositoryU.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Usuario>> encontrarPorNome(@PathVariable String nome) {

		return ResponseEntity.ok().body(repositoryU.findAllByNomeContainingIgnoreCase(nome));
	}

	@DeleteMapping("/{id}")
	public void deletarUsuario(@PathVariable long id) {

		repositoryU.deleteById(id);

	}
}
