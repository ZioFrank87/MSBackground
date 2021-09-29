package it.majorbit.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import it.majorbit.model.Background;
import it.majorbit.service.BackgroundService;
import it.majorbit.util.Auth;
import it.majorbit.service.ErroreService;

@Controller
public class BackgroundController {

	@Autowired
	public BackgroundService backgroundService;
	
	@Autowired
	public ErroreService erroreService;

	@PostMapping("register_background")
	public @ResponseBody ResponseEntity<Object> registerBackground(@RequestBody Map<String,String> params,@RequestHeader Map<String, String> header){

		if (Auth.isAuthorized(header)) {

			String encryptedString = params.get("r");

			String encryptionKey = Auth.getEncryptionKey(header);

			String decryptedString = Auth.decryptByEncryptionKey(encryptedString,encryptionKey);

			Map<String,Object> map = new Gson().fromJson(decryptedString,Map.class);

			Background background = new Background();

			background.setImage((String)map.get("image"));
			backgroundService.registerBackground(background);

			String messageToBeCrypted = "Sfondo creato con successo";
			String cryptedMessage = Auth.cryptByEncryptionKey(messageToBeCrypted,encryptionKey);
			
			return ResponseEntity.status(HttpStatus.OK).body(cryptedMessage);

		}
		else {

			Map<String,Object> error = new HashMap<String,Object>(); //mappa di errore generata nel caso in cui l'utente non abbia effettuato il logIn

			error.put("hasError", true);
			error.put("message", erroreService.readErrore("BACKGROUND_UNAUTHORIZED_ERROR").getTextIta());
		
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}

	}


	@GetMapping("read_background")
	public @ResponseBody ResponseEntity<Object> readBackground(@RequestParam String id, @RequestHeader Map<String, String> header){

		if (Auth.isAuthorized(header)) {

			String encryptionKey = Auth.getEncryptionKey(header);
			
			id = id.replace(" ","+");
					
			String decryptedId = Auth.decryptByEncryptionKey(id,encryptionKey);

			Background background = backgroundService.readBackground(decryptedId);

			if(background!=null) { 

				String CryptedJsonString = Auth.cryptByEncryptionKey(background,encryptionKey);

				return ResponseEntity.status(HttpStatus.OK).body(CryptedJsonString);
			}

			else {

				Map<String,Object> error = new HashMap<String,Object>(); //mappa di errore generata nel caso in cui lo sfondo cercato non esista
				error.put("hasError", true);
				error.put("message", erroreService.readErrore("BACKGROUND_NOT_EXISTING_ERROR").getTextIta());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

			}

		} else {

			Map<String,Object> error = new HashMap<String,Object>(); //mappa di errore generata nel caso in cui l'utente non abbia effettuato il logIn

			error.put("hasError", true);
			error.put("message", erroreService.readErrore("BACKGROUND_UNAUTHORIZED_ERROR").getTextIta()); 

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}
	}



	@DeleteMapping("delete_background")
	public @ResponseBody ResponseEntity<Object> deleteBackground(@RequestParam String id, @RequestHeader Map<String, String> header){

		if (Auth.isAuthorized(header)) {  //controlla se l'utente è loggato

			String encryptionKey = Auth.getEncryptionKey(header);
			
			id = id.replace(" ","+");

			String decryptedId = Auth.decryptByEncryptionKey(id,encryptionKey);

			Background backgroundToBeDeleted = backgroundService.readBackground(decryptedId);

			if(backgroundToBeDeleted!=null) {

				backgroundService. deleteGroup(backgroundToBeDeleted);

				String messageToBeCrypted = "Sfondo eliminato";
				String cryptedMessage = Auth.cryptByEncryptionKey(messageToBeCrypted,encryptionKey); 

				return ResponseEntity.status(HttpStatus.OK).body(cryptedMessage);
			}	

			else {

				Map<String,Object> error = new HashMap<String,Object>(); //mappa di errore generata nel caso in cui il gruppo non esiste

				error.put("hasError", true);
				error.put("message", erroreService.readErrore("BACKGROUND_NOT_EXISTING_ERROR").getTextIta());

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
			}

		} else {

			Map<String,Object> error = new HashMap<String,Object>(); //mappa di errore generata nel caso in cui l'utente non è loggato
			error.put("hasError", true);
			error.put("message", erroreService.readErrore("BACKGROUND_UNAUTHORIZED_ERROR").getTextIta());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}

	}


	
	@PutMapping("update_background")
	public @ResponseBody ResponseEntity<Object> updateBackground(@RequestBody Map<String,String> params, @RequestHeader Map<String, String> header) { 
		if (Auth.isAuthorized(header)) { //verifica che l'utente sia loggato
			
			String encryptedString = params.get("r");

			String encryptionKey = Auth.getEncryptionKey(header);

			String decryptedString = Auth.decryptByEncryptionKey(encryptedString,encryptionKey);

			Map<String,Object> map = new Gson().fromJson(decryptedString,Map.class);
			
			String decryptedId = (String)map.get("id");
			
			Background backgroundToBeUpdated = backgroundService.readBackground(decryptedId);

			if(backgroundToBeUpdated!=null) {

				backgroundToBeUpdated.setImage((String)map.get("new image")); 
				backgroundToBeUpdated.setCost(Integer.parseInt((String)map.get("cost")));

				backgroundService.registerBackground(backgroundToBeUpdated); 
				
				String messageToBeCrypted = "Sfondo aggiornato";
				String cryptedMessage = Auth.cryptByEncryptionKey(messageToBeCrypted,encryptionKey); 

				return ResponseEntity.status(HttpStatus.OK).body(cryptedMessage);
			}	

			else {

				Map<String,Object> error = new HashMap<String,Object>(); //mappa di errore generata nel caso in cui il gruppo non esista
				error.put("hasError", true);
				error.put("message", erroreService.readErrore("BACKGROUND_NOT_EXISTING_ERROR").getTextIta());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
			}

		}

		else {

			Map<String,Object> error = new HashMap<String,Object>(); //mappa di errore generata nel caso in cui l'utente non abbia effettuato il login
			error.put("hasError", true);
			error.put("message", erroreService.readErrore("BACKGROUND_UNAUTHORIZED_ERROR").getTextIta());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}
	}
}
