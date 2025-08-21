package com.planificacion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planificacion.model.ResetToken;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IResetTokenRepo;
import com.planificacion.service.IResetTokenService;



@Service
public class ResetTokenServiceImpl extends CRUDImpl<ResetToken, Integer> implements IResetTokenService {
	
	@Autowired
	private IResetTokenRepo repo;

	@Override
	protected IGenericRepo<ResetToken, Integer> getRepo() {
	
		return repo;
	}

	@Override
	public ResetToken findByToken(String token) {
		// TODO Auto-generated method stub
		return repo.findByToken(token);
	}

	@Override
	public void guardar(ResetToken token) {
		repo.save(token);		
	}

	@Override
	public void eliminar(ResetToken token) {
		repo.delete(token);
		
	}
	
}
