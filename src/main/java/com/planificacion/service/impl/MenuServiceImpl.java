package com.planificacion.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planificacion.model.Menu;
import com.planificacion.model.Submenu;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IMenuRepo;
import com.planificacion.service.IMenuService;
import com.planificacion.service.ISubmenuService;


@Service
public class MenuServiceImpl extends CRUDImpl<Menu, Integer> implements IMenuService{

	@Autowired
	private IMenuRepo repo;
	@Autowired
    private ISubmenuService submenuService;

	@Override
	protected IGenericRepo<Menu, Integer> getRepo() {
		return repo;
	}
	
	 @Override
	    public List<Menu> listarMenuPorUsuario(String nombre) {
	        // 1. Obtiene la lista de menús principales que el usuario tiene acceso
	        List<Menu> mainMenus = repo.listarMenuPorUsuario(nombre);

	        // 2. Obtiene la lista completa de submenús del usuario
	        List<Submenu> userSubmenus = submenuService.listarSubmenuPorUsuario(nombre);
	        
	        // ✅ Filtra los submenús para que solo aquellos con roles sean incluidos
	        List<Submenu> filteredSubmenus = userSubmenus.stream()
	            .filter(submenu -> submenu.getRoles() != null && !submenu.getRoles().isEmpty())
	            .collect(Collectors.toList());

	        // 3. Agrupa los submenús FILTRADOS por el ID de su menú padre
	        Map<Integer, List<Submenu>> submenusByParent = filteredSubmenus.stream()
	            .collect(Collectors.groupingBy(submenu -> submenu.getParentMenu().getIdMenu()));

	        // 4. Recorre los menús principales y les asigna sus submenús filtrados
	        for (Menu menu : mainMenus) {
	            List<Submenu> children = submenusByParent.get(menu.getIdMenu());
	            if (children != null) {
	                menu.setSubmenus(children);
	            } else {
	                // Si no hay submenús filtrados, se establece una lista vacía
	                menu.setSubmenus(List.of()); 
	            }
	        }
	        return mainMenus;
	    }
}
