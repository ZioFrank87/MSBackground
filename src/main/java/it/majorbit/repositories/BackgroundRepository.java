package it.majorbit.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.majorbit.model.Background;

@Repository
public interface BackgroundRepository extends CrudRepository<Background,String> {
	


}