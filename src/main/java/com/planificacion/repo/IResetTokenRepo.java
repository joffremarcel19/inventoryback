package com.planificacion.repo;

import com.planificacion.model.ResetToken;

public interface IResetTokenRepo extends IGenericRepo<ResetToken, Integer> {
	
	ResetToken findByToken(String token);
}
