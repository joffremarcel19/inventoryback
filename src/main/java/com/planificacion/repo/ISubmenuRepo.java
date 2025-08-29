package com.planificacion.repo;

import com.planificacion.model.Submenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// ✅ Make ISubmenuRepo extend both JpaRepository and IGenericRepo
public interface ISubmenuRepo extends JpaRepository<Submenu, Integer>, IGenericRepo<Submenu, Integer> {

	@Query("SELECT sm FROM Submenu sm JOIN sm.roles r JOIN r.usuarios u WHERE u.username = :nombre")
    List<Submenu> listSubmenuByUsername(@Param("nombre") String nombre);
}