package ajude.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import ajude.entities.Likes;
import ajude.services.LikesService;

@RestController
public class LikesController {

	@Autowired
	private LikesService likesService;

	
	@PostMapping("auth/like")
	public ResponseEntity<Likes> addLike(@RequestBody Likes like, @RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<Likes>(likesService.addLike(like, token), HttpStatus.CREATED);
		} catch (Exception e) {
			if (e.getMessage().equals("usuario ja deu like")) {
				System.out.println(e.getMessage());
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@GetMapping("likes/campanha/{url}")
	public ResponseEntity<List<Likes>> getLikesCampanha(@PathVariable String url) {
		try {
			return new ResponseEntity<List<Likes>>(likesService.getLikesCamp(url), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("auth/likes/usuario")
	public ResponseEntity<List<Likes>> getLikesUsuario(@RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<List<Likes>>(likesService.getLikesUsu(token), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	
	@DeleteMapping("auth/like/campanha/remove/{url}")
	public ResponseEntity<Likes> deleteLike(@RequestHeader("Authorization") String token, @PathVariable String url) {
		try {
			
			return new ResponseEntity<Likes>(likesService.deleteLike(url, token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

//	
//	@DeleteMapping("auth/like/campanha/remove/{id}")
//	public ResponseEntity<Likes> deleteLikeById(@RequestHeader("Authorization") String token, @PathVariable String url) {
//		try {
//			return new ResponseEntity<Likes>(likesService.deleteLikeByUrl(url, token), HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}

}