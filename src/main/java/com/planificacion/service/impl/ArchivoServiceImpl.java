package com.planificacion.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.planificacion.service.ArchivoService;

public class ArchivoServiceImpl implements ArchivoService {
	
	private final Path root = Paths.get("uploads");
	
	@Override
	public void init() {
		try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("No se puede inicializar la carpeta uploads");
        }
		
	}

	@Override
	public void save(MultipartFile archivo) {
		 try {
	            //copy (que queremos copiar, a donde queremos copiar)
	            Files.copy(archivo.getInputStream(), 
	                       this.root.resolve(archivo.getOriginalFilename()));
	        } catch (IOException e) {
	            throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
	        }
	}

	@Override
	public Resource load(String name) {
		 try {
	            Path file = root.resolve(name);
	            Resource resource = new UrlResource(file.toUri());

	            if(resource.exists() || resource.isReadable()){
	                return resource;
	            }else{
	                throw new RuntimeException("No se puede leer el archivo ");
	            }

	        }catch (MalformedURLException e){
	            throw new RuntimeException("Error: " + e.getMessage());
	        }
	}

	@Override
	public void deleteAll() {
	     FileSystemUtils.deleteRecursively(root.toFile());		
	}

	@Override
	public Stream<Path> loadAll() {
		  try{
	            return Files.walk(this.root,1).filter(path -> !path.equals(this.root))
	                    .map(this.root::relativize);
	        }catch (RuntimeException | IOException e){
	            throw new RuntimeException("No se pueden cargar los archivos ");
	        }
	}

	@Override
	public String deleteFile(String name) {
		 try {
	            Boolean delete = Files.deleteIfExists(this.root.resolve(name));
	                return "Borrado";
	        }catch (IOException e){
	            e.printStackTrace();
	            return "Error Borrando ";
	        }
	}

}
