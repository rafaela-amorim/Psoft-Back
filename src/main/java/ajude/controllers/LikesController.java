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

import ajude.entities.Dislikes;
import ajude.entities.Likes;
import ajude.services.JWTService;
import ajude.services.LikesService;

@RestController
public class LikesController {

	@Autowired
	private LikesService likesService;
	@Autowired
	private JWTService jwtService;
	
	public LikesController() {
		super();
	}
	
	@PostMapping("auth/like")
	public ResponseEntity<Likes> addLike(@RequestBody Likes like, @RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<Likes>(likesService.addLike(like, token), HttpStatus.CREATED);
		} catch (Exception e) {
			if (e.getMessage().equals("usuario ja deu like"))
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@PostMapping("auth/dislike")
	public ResponseEntity<Dislikes> addDislike(@RequestBody Dislikes dislikes, @RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<Dislikes>(likesService.addDislike(dislikes, token), HttpStatus.CREATED);
		} catch (Exception e) {
			if (e.getMessage().equals("usuario ja deu dislike"))
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@GetMapping("likes/campanha/{id}")
	public ResponseEntity<List<Likes>> getLikesCampanha(@PathVariable long id) {
		try {
			return new ResponseEntity<List<Likes>>(likesService.getLikesCamp(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("dislikes/campanha/{id}")
	public ResponseEntity<List<Dislikes>> getDislikesCampanha(@PathVariable long id) {
		try {
			return new ResponseEntity<List<Dislikes>>(likesService.getDislikesCamp(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("likes/usuario")
	public ResponseEntity<List<Likes>> getLikesUsuario(@RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<List<Likes>>(likesService.getLikesUsu(token), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("dislikes/usuario")
	public ResponseEntity<List<Dislikes>> getDislikesUsuario(@RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<List<Dislikes>>(likesService.getDislikesUsu(token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("auth/like/campanha/remove/{id}")
	public ResponseEntity<Likes> deleteLike(@RequestHeader("Authorization") String token, @PathVariable long id) {
		try {
			return new ResponseEntity<Likes>(likesService.deleteLike(id, token), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("auth/dislike/campanha/remove/{id}")
	public ResponseEntity<Dislikes> deleteDislike(@RequestHeader("Authorization") String token, @PathVariable long id) {
		try {
			return new ResponseEntity<Dislikes>(likesService.deleteDislike(id, token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("auth/like/remove/{id}")
	public ResponseEntity<Likes> deleteLikeById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		try {
			return new ResponseEntity<Likes>(likesService.deleteLikeById(id, token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("auth/dislike/remove/{id}")
	public ResponseEntity<Dislikes> deleteDislikeById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		try {
			return new ResponseEntity<Dislikes>(likesService.deleteDislikebyId(id, token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}












