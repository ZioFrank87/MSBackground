package it.majorbit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.majorbit.model.Background;
import it.majorbit.repositories.BackgroundRepository;

@Service
public class BackgroundService {
	
	@Autowired
	private BackgroundRepository backgroundRepository;


	public Background readBackground(String code) {
		return backgroundRepository.findById(code).orElse(null);
	}


	public void registerBackground(Background background) {
		backgroundRepository.save(background);
	}


	public void deleteGroup(Background background) {
		backgroundRepository.delete(background);
	}


}
