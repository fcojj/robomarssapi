package br.com.contaazul.francisco.robomarsapi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.contaazul.francisco.robomarsapi.model.Mars;
import br.com.contaazul.francisco.robomarsapi.model.Position;

@RestController
@RequestMapping("/rest/mars")
public class RobotResource {

	@Autowired
	private Mars mars;
	
	@PostMapping("/{command}")
	public ResponseEntity<String> execute(@PathVariable String command) throws MethodArgumentNotValidException {
		
		Position currentRobotPosition =  mars.getCurrentPositionOfRobot(command);
		
		if(currentRobotPosition == null) {
			return ResponseEntity.badRequest().body("");
		}
		
		return ResponseEntity.ok().body(currentRobotPosition.toString());
	}
}
