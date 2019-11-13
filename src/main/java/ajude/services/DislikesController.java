package ajude.services;

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

@RestController
public class DislikesController {

	@Autowired
	private DislikeService dislikeService;
	
	public DislikesController() {
		super();
	}
	
	@PostMapping("auth/dislike")
	public ResponseEntity<Dislikes> addDislike(@RequestBody Dislikes dislikes, @RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<Dislikes>(dislikeService.addDislike(dislikes, token), HttpStatus.CREATED);
		} catch (Exception e) {
			if (e.getMessage().equals("usuario ja deu dislike")) {
				System.out.println(e.getMessage());
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@GetMapping("dislikes/campanha/{url}")
	public ResponseEntity<List<Dislikes>> getDislikesCampanha(@PathVariable String url) {
		try {
			return new ResponseEntity<List<Dislikes>>(dislikeService.getDislikesCamp(url), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("auth/dislikes/usuario")
	public ResponseEntity<List<Dislikes>> getDislikesUsuario(@RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<List<Dislikes>>(dislikeService.getDislikesUsu(token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("auth/dislike/campanha/remove/{url}")
	public ResponseEntity<Dislikes> deleteDislike(@RequestHeader("Authorization") String token, @PathVariable String url) {
		try {
			return new ResponseEntity<Dislikes>(dislikeService.deleteDislike(url, token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("auth/dislike/campanha/remove/{id}")
	public ResponseEntity<Dislikes> deleteDislikeById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		try {
			return new ResponseEntity<Dislikes>(dislikeService.deleteDislikeById(id, token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
