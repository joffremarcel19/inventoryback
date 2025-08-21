package com.planificacion.service;



import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ArchivoService {

	public void init();
	public void save(MultipartFile archivo);
	public Resource load(String name);
	public void deleteAll();
	public Stream<Path> loadAll();
	public String deleteFile(String name);
}
