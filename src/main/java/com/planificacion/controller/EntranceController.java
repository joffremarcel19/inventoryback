package com.planificacion.controller;

import com.planificacion.model.DetailEntrance;
import com.planificacion.model.Entrance;
import com.planificacion.service.IDetailEntranceService;
import com.planificacion.service.IEntranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/entrances")
@Transactional
public class EntranceController {

    @Autowired
    private IEntranceService entranceService;

    @Autowired
    private IDetailEntranceService detailEntranceService;

    @PostMapping
    public ResponseEntity<?> registerEntranceAndEnroll(@RequestBody RegistrationRequest request) {
        Entrance entrance = new Entrance();
        entrance.setCity(request.getCity());
        entrance.setIdentity(request.getIdentity());
        entrance.setLastName(request.getLastName());
        entrance.setNames(request.getNames());
        entrance.setPhone(request.getPhone());
        entrance.setEmail(request.getEmail());

        Entrance registeredEntrance = null;
        List<DetailEntranceResponseForRequest> enrollmentResponses = new ArrayList<>();
        List<String> enrollmentErrors = new ArrayList<>();
        HttpStatus status = HttpStatus.CREATED; // Default status para éxito

        try {
            registeredEntrance = entranceService.registerEntrance(entrance);
            if (request.getDetailEntrance() != null && !request.getDetailEntrance().isEmpty()) {
                for (DetailEntranceInfo detailInfo : request.getDetailEntrance()) {
                    try {
                        DetailEntrance registration = detailEntranceService.registerToConcert(
                                detailInfo.getConcert().getId(),
                                registeredEntrance.getId(),
                                detailInfo.getToken(),
                                detailInfo.getStatus()
                        );
                        enrollmentResponses.add(new DetailEntranceResponseForRequest(registration));
                    } catch (Exception e) {
                        enrollmentErrors.add(e.getMessage());
                        if (e.getMessage().startsWith("Concert not found")) {
                            status = HttpStatus.BAD_REQUEST; // Cambiar status si hay error de concierto no encontrad
                        } else if (e.getMessage().startsWith("El token '") && e.getMessage().endsWith("' ya está en uso. No se puede registrar.")) {
                            status = HttpStatus.BAD_REQUEST; // Cambiar status si hay error de token duplicado
                        } else {
                            status = HttpStatus.INTERNAL_SERVER_ERROR; // Otro error
                        }
                    }
                }
            }

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(registeredEntrance.getId())
                    .toUri();

            RegistrationResult response = new RegistrationResult();
            response.setId(registeredEntrance.getId());
            response.setCity(registeredEntrance.getCity());
            response.setIdentity(registeredEntrance.getIdentity());
            response.setLastName(registeredEntrance.getLastName());
            response.setNames(registeredEntrance.getNames());
            response.setPhone(registeredEntrance.getPhone());
            response.setEmail(registeredEntrance.getEmail());
            response.setDetailEntrance(enrollmentResponses);
            response.setErrors(enrollmentErrors);

            return ResponseEntity.status(status).location(location).body(response);

        } catch (Exception mainException) {
            mainException.printStackTrace();
            String errorMessage = "Error al registrar la entrada principal: " + mainException.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEntranceById(@PathVariable Integer id) {
        Optional<Entrance> entranceOptional = entranceService.getEntranceById(id);
        if (entranceOptional.isPresent()) {
            return ResponseEntity.ok(entranceOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la entrada con el ID: " + id);
        }
    }
    @GetMapping
    public ResponseEntity<List<Entrance>> getAllEntrances() {
        List<Entrance> entrances = entranceService.getAllEntrances();
        return ResponseEntity.ok(entrances);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entrance> updateEntrance(@PathVariable Integer id, @RequestBody Entrance entrance) {
        Entrance updatedEntrance = entranceService.updateEntrance(id, entrance);
        return updatedEntrance != null ? ResponseEntity.ok(updatedEntrance) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrance(@PathVariable Integer id) {
        entranceService.deleteEntrance(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{entranceId}/add-concerts")
    public ResponseEntity<?> addConcertsToEntrance(
            @PathVariable Integer entranceId,
            @RequestBody List<DetailEntranceInfo> detailEntrancesToAdd) { // Usamos la misma clase DetailEntranceInfo
        Optional<Entrance> entranceOptional = entranceService.getEntranceById(entranceId);
        if (!entranceOptional.isPresent()) {
            return ((BodyBuilder) ResponseEntity.notFound()).body("No se encontró la entrada con el ID: " + entranceId);
        }
        Entrance entrance = entranceOptional.get();
        List<DetailEntranceResponseForRequest> newRegistrations = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        HttpStatus status = HttpStatus.OK;

        for (DetailEntranceInfo detailInfo : detailEntrancesToAdd) {
            try {
                // Verificar si ya existe un registro para esta entrada y concierto
                Optional<DetailEntrance> existingRegistration = detailEntranceService.findByEntranceIdAndConcertId(
                        entrance.getId(),
                        detailInfo.getConcert().getId()
                );
                if (!existingRegistration.isPresent()) {
                    // Si no existe, registrar al nuevo concierto
                    DetailEntrance registration = detailEntranceService.registerToConcert(
                            detailInfo.getConcert().getId(),
                            entrance.getId(),
                            detailInfo.getToken(),
                            detailInfo.getStatus()
                    );
                    newRegistrations.add(new DetailEntranceResponseForRequest(registration));
                } else {
                    errors.add("La entrada con ID " + entranceId + " ya está registrada al concierto con ID " + detailInfo.getConcert().getId());
                }
            } catch (Exception e) {
                errors.add("Error al registrar al concierto con ID " + detailInfo.getConcert().getId() + ": " + e.getMessage());
                status = HttpStatus.BAD_REQUEST;
            }
        }

        // Obtener la lista completa de inscripciones actualizadas para la respuesta
        List<DetailEntrance> updatedDetails = detailEntranceService.getRegistrationsByEntranceId(entranceId);
        List<DetailEntranceResponseForRequest> updatedDetailResponses = updatedDetails.stream()
                .map(DetailEntranceResponseForRequest::new)
                .collect(Collectors.toList());

        AddConcertsResponse response = new AddConcertsResponse(); // ¡Aquí se crea la instancia de AddConcertsResponse!
        response.setEntranceId(entranceId);
        response.setDetailEntrance(updatedDetailResponses); // Devolver la lista completa actualizada
        response.setErrors(errors);

        return ResponseEntity.status(status).body(response);
    }

    
    
    @GetMapping("/by-identity/{identity}")
    public ResponseEntity<?> getEntrancesByIdentityWithDetails(@PathVariable String identity) {
        List<Entrance> entrances = entranceService.getEntrancesByIdentity(identity);

        if (entrances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron entradas con la identidad: " + identity);
        }

        Entrance entrance = entrances.get(0);
        List<DetailEntrance> details = detailEntranceService.getRegistrationsByEntranceId(entrance.getId());
        List<DetailEntranceResponseForRequest> detailResponses = details.stream()
                .map(DetailEntranceResponseForRequest::new)
                .collect(Collectors.toList());

        RegistrationResult response = new RegistrationResult();
        response.setId(entrance.getId());
        response.setCity(entrance.getCity());
        response.setIdentity(entrance.getIdentity());
        response.setLastName(entrance.getLastName());
        response.setNames(entrance.getNames());
        response.setPhone(entrance.getPhone());
        response.setEmail(entrance.getEmail());
        response.setDetailEntrance(detailResponses);
        response.setErrors(new ArrayList<>());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/by-token/{token}")
    public ResponseEntity<?> getEntranceByToken(@PathVariable String token) {
        Optional<DetailEntrance> detailEntranceOptional = detailEntranceService.getDetailEntranceByTokenWithEntrance(token);

        if (detailEntranceOptional.isPresent()) {
            Entrance entrance = detailEntranceOptional.get().getEntrance();
            // Crear una lista que contenga solo el DetailEntrance encontrado por el token
            List<DetailEntranceResponseForRequest> detailResponses = new ArrayList<>();
            detailResponses.add(new DetailEntranceResponseForRequest(detailEntranceOptional.get()));

            RegistrationResult response = new RegistrationResult();
            response.setId(entrance.getId());
            response.setCity(entrance.getCity());
            response.setIdentity(entrance.getIdentity());
            response.setLastName(entrance.getLastName());
            response.setNames(entrance.getNames());
            response.setPhone(entrance.getPhone());
            response.setEmail(entrance.getEmail());
            response.setDetailEntrance(detailResponses); // Asignar la lista filtrada
            response.setErrors(new ArrayList<>());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ninguna entrada asociada al token: " + token);
        }
    }
    
    //nuevo metodo para estraer el token 
    @PutMapping("/detail/{detailEntranceId}")
    public ResponseEntity<String> markDetailEntranceAsIngresado(@PathVariable Integer detailEntranceId) {
        try {
            DetailEntrance updatedDetailEntrance = detailEntranceService.markAsIngresado(detailEntranceId);
            return ResponseEntity.ok("Ingreso exitoso al concierto.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    

    // Clase interna para representar la información de detailEntrance en la response del POST (ORIGINAL)
    public static class DetailEntranceResponse {
        private Integer id;
        private String token;
        private Integer concertId;
        private String concertName;
        private LocalDateTime concertDate;
        private String concertStatus;

        public DetailEntranceResponse(DetailEntrance detailEntrance) {
            this.id = detailEntrance.getId();
            this.token = detailEntrance.getToken();
            this.concertId = detailEntrance.getConcert().getId();
            this.concertName = detailEntrance.getConcert().getName();
            this.concertDate = detailEntrance.getConcert().getDate();
            this.concertStatus = detailEntrance.getConcert().getStatus();
        }

        public Integer getId() {
            return id;
        }

        public String getToken() {
            return token;
        }

        public Integer getConcertId() {
            return concertId;
        }

        public String getConcertName() {
            return concertName;
        }

        public LocalDateTime getConcertDate() {
            return concertDate;
        }

        public String getConcertStatus() {
            return concertStatus;
        }
    }

    // Clase interna para la respuesta completa del registro (POST) (MODIFICADO PARA LA BÚSQUEDA)
    public static class RegistrationResult {
        private Integer id;
        private String city;
        private String identity;
        private String lastName;
        private String names;
        private String phone;
        private String email;
        private List<DetailEntranceResponseForRequest> detailEntrance;
        private List<String> errors;


        public Integer getId() { // Cambiamos el getter a "getId"
            return id;
        }

        public void setId(Integer id) { // Cambiamos el setter a "setId"
            this.id = id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getNames() {
            return names;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<DetailEntranceResponseForRequest> getDetailEntrance() {
            return detailEntrance;
        }

        public void setDetailEntrance(List<DetailEntranceResponseForRequest> detailEntrance) {
            this.detailEntrance = detailEntrance;
        }

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }
    }

    // Clase interna para representar los detalles de la inscripción con la info del concierto para el GET /details
    public static class EntranceDetailResponse {
        private Integer id;
        private String token;
        private Integer concertId;
        private String concertName;
        private LocalDateTime concertDate;
        private String concertStatus;

        public EntranceDetailResponse(DetailEntrance detailEntrance) {
            this.id = detailEntrance.getId();
            this.token = detailEntrance.getToken();
            this.concertId = detailEntrance.getConcert().getId();
            this.concertName = detailEntrance.getConcert().getName();
            this.concertDate = detailEntrance.getConcert().getDate();
            this.concertStatus = detailEntrance.getConcert().getStatus();
        }

        public Integer getId() {
            return id;
        }

        public String getToken() {
            return token;
        }

        public Integer getConcertId() {
            return concertId;
        }

        public String getConcertName() {
            return concertName;
        }

        public LocalDateTime getConcertDate() {
            return concertDate;
        }

        public String getConcertStatus() {
            return concertStatus;
        }
    }

    // Clase interna para la respuesta del GET /details
    public static class EntranceWithDetailsResponse {
        private Entrance entrance;
        private List<EntranceDetailResponse> enrollments;

        public Entrance getEntrance() {
            return entrance;
        }

        public void setEntrance(Entrance entrance) {
            this.entrance = entrance;
        }

        public List<EntranceDetailResponse> getEnrollments() {
            return enrollments;
        }

        public void setEnrollments(List<EntranceDetailResponse> enrollments) {
            this.enrollments = enrollments;
        }
    }

    // Clase interna para el request body del registro y enrolamiento (MODIFICADO)
    public static class RegistrationRequest {
        private String city;
        private String identity;
        private String lastName;
        private String names;
        private String phone;
        private String email;
        private List<DetailEntranceInfo> detailEntrance;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getNames() {
            return names;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<DetailEntranceInfo> getDetailEntrance() {
            return detailEntrance;
        }

        public void setDetailEntrance(List<DetailEntranceInfo> detailEntrance) {
            this.detailEntrance = detailEntrance;
        }
    }

    // Clase interna para representar la información de detailEntrance en el request (MODIFICADO)
    public static class DetailEntranceInfo {
        private ConcertInfo concert;
        private String status;
        private String token;

        public ConcertInfo getConcert() {
            return concert;
        }

        public void setConcert(ConcertInfo concert) {
            this.concert = concert;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    // Clase interna para representar la información del concierto dentro de DetailEntranceInfo
    public static class ConcertInfo {
        private Integer id;
        private LocalDateTime date;
        private String status;
        private String urlImage;
        private String name; // Agregamos el campo name

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUrlImage() {
            return urlImage;
        }

        public void setUrlImage(String urlImage) {
            this.urlImage = urlImage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // Clase interna para la respuesta de detailEntrance para que coincida con la request (MODIFICADO)
    public static class DetailEntranceResponseForRequest {
        private Integer id;
        private ConcertInfo concert;
        private String status;
        private String token;

        public DetailEntranceResponseForRequest(DetailEntrance detailEntrance) {
            this.id = detailEntrance.getId();
            this.concert = new ConcertInfo();
            this.concert.setId(detailEntrance.getConcert().getId());
            this.concert.setDate(detailEntrance.getConcert().getDate());
            this.concert.setStatus(detailEntrance.getConcert().getStatus());
            this.concert.setUrlImage(detailEntrance.getConcert().getUrlImage());
            this.concert.setName(detailEntrance.getConcert().getName()); // Agregamos el nombre del concierto
            this.status = detailEntrance.getStatus();
            this.token = detailEntrance.getToken();
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public ConcertInfo getConcert() {
            return concert;
        }

        public void setConcert(ConcertInfo concert) {
            this.concert = concert;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
 // Clase interna para la respuesta del endpoint PUT para añadir conciertos
    public static class AddConcertsResponse {
        private Integer entranceId;
        private List<DetailEntranceResponseForRequest> detailEntrance;
        private List<String> errors;

        public Integer getEntranceId() {
            return entranceId;
        }

        public void setEntranceId(Integer entranceId) {
            this.entranceId = entranceId;
        }

        public List<DetailEntranceResponseForRequest> getDetailEntrance() {
            return detailEntrance;
        }

        public void setDetailEntrance(List<DetailEntranceResponseForRequest> detailEntrance) {
            this.detailEntrance = detailEntrance;
        }

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }
    }
}