package com.planificacion.service.impl;

import com.planificacion.dto.DeliveryRequestDTO;
import com.planificacion.dto.DeliveryItemDTO;
import com.planificacion.model.Delivery;
import com.planificacion.model.DeliveryDetail;
import com.planificacion.model.InventoryUnit;
import com.planificacion.model.ParameterDetail;
import com.planificacion.model.Persona;
import com.planificacion.model.Product;
import com.planificacion.model.Usuario;
import com.planificacion.repo.IDeliveryDetailRepo;
import com.planificacion.repo.IDeliveryRepo;
import com.planificacion.repo.IInventoryUnitRepo;
import com.planificacion.repo.IParameterDetailRepo;
import com.planificacion.repo.IPersonaRepo;
import com.planificacion.repo.IProductRepo;
import com.planificacion.repo.IUsuarioRepo;
import com.planificacion.service.IDeliveryService;
import com.planificacion.service.IInventoryMovementService;
import com.planificacion.service.IInventoryUnitService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class DeliveryServiceImpl implements IDeliveryService {

    @Autowired
    private IDeliveryRepo deliveryRepo;

    @Autowired
    private IDeliveryDetailRepo deliveryDetailRepo;

    @Autowired
    private IInventoryUnitRepo inventoryUnitRepo;

    @Autowired
    private IPersonaRepo personaRepo;

    @Autowired
    private IUsuarioRepo usuarioRepo;

    @Autowired
    private IProductRepo productRepository;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private IParameterDetailRepo parameterDetailRepo;
    
    @Autowired
    private IInventoryMovementService inventoryMovementService;
    
    @Autowired
    private IInventoryUnitService inventoryUnitService;
    

    @Override
    @Transactional
    public Delivery register(Delivery delivery) {
        Delivery savedDelivery = deliveryRepo.save(delivery);
        if (delivery.getDeliveryDetails() != null) {
            for (DeliveryDetail detail : delivery.getDeliveryDetails()) {
                detail.setDelivery(savedDelivery);
                deliveryDetailRepo.save(detail);
            }
        }
        return savedDelivery;
    }

    @Override
    public List<Delivery> listAll() {
        return deliveryRepo.findAll();
    }

    @Override
    public Delivery findById(Integer id) {
        return deliveryRepo.findById(id).orElse(null);
    }


	@Override
	@Transactional

	public Delivery registerMultipleDeliveries(DeliveryRequestDTO request) {

		Delivery delivery = convertToDeliveryEntity(request);

		Delivery savedDelivery = deliveryRepo.save(delivery);

		// ✅ Obtener el tipo de movimiento directamente del deliveryTypeDetail

		String movementType = savedDelivery.getDeliveryTypeDetail().getValue();

		if (savedDelivery.getDeliveryDetails() != null) {

			for (DeliveryDetail detail : savedDelivery.getDeliveryDetails()) {

				detail.setDelivery(savedDelivery);

				deliveryDetailRepo.save(detail);

				Integer productId = detail.getProduct().getId();

				Integer unidadAdministrativaId = detail.getInventoryUnit().getUnit().get(0).getIdUniAdm();

				Integer quantity = detail.getQuantity();

				try {

					if ("INGRESO".equalsIgnoreCase(movementType)) {

						inventoryUnitService.aumentarStock(productId, unidadAdministrativaId, quantity);

					} else if ("EGRESO".equalsIgnoreCase(movementType)) {

						inventoryUnitService.disminuirStock(productId, unidadAdministrativaId, quantity);

					} else {

						throw new IllegalArgumentException(
								"El tipo de entrega no tiene un valor válido (INGRESO/EGRESO).");

					}

				} catch (Exception e) {

					throw new RuntimeException("Error al actualizar el stock: " + e.getMessage(), e);

				}

			}

		}

		try {

			inventoryMovementService.createMovementFromDelivery(savedDelivery);

		} catch (Exception e) {

			throw new RuntimeException("Error al registrar el movimiento de inventario para la entrega", e);

		}

		return savedDelivery;

	}
    // <-- ¡MÉTODO MODIFICADO! -->
    private Delivery convertToDeliveryEntity(DeliveryRequestDTO request) {
        Delivery delivery = new Delivery();
        delivery.setDeliveryDate(request.getDeliveryDate());
        delivery.setDescription(request.getDescription());

        // Mapear Persona
        Persona person = personaRepo.findById(request.getPersonId())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + request.getPersonId()));
        delivery.setPerson(person);

        // Mapear Usuario
        Usuario user = usuarioRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.getUserId()));
        delivery.setUser(user);

        // Mapear DeliveryTypeDetail
        if (request.getDeliveryTypeDetailId() != null && request.getDeliveryTypeDetailId() > 0) {
            ParameterDetail deliveryTypeDetail = parameterDetailRepo.findById(request.getDeliveryTypeDetailId())
                    .orElseThrow(() -> new RuntimeException("Tipo de Entrega (ParameterDetail) no encontrado con ID: " + request.getDeliveryTypeDetailId()));
            delivery.setDeliveryTypeDetail(deliveryTypeDetail);
        } else {
            throw new IllegalArgumentException("Debe proporcionar un ID de Tipo de Entrega (ParameterDetail) válido.");
        }

        List<DeliveryDetail> details = new ArrayList<>();
        if (request.getItems() != null) {
            for (DeliveryItemDTO itemDTO : request.getItems()) {
                DeliveryDetail detail = new DeliveryDetail();
                detail.setQuantity(itemDTO.getQuantity());

                InventoryUnit inventoryUnit = inventoryUnitRepo.findById(itemDTO.getInventoryUnitId())
                        .orElseThrow(() -> new RuntimeException("Unidad de inventario no encontrada con ID: " + itemDTO.getInventoryUnitId()));
                detail.setInventoryUnit(inventoryUnit);

                Product product = productRepository.findById(itemDTO.getProductid())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + itemDTO.getProductid()));
                detail.setProduct(product);

                // ¡¡¡CAMBIO CRUCIAL AQUÍ!!!
                // Establece la relación bidireccional AQUI MISMO.
                // Aunque 'delivery' no tiene ID todavía, estamos construyendo la relación en memoria.
                detail.setDelivery(delivery); // <-- ¡MUEVE ESTA LÍNEA AQUI!
                
                details.add(detail);
            }
        }
        delivery.setDeliveryDetails(details); // Asigna la lista de detalles a la Delivery
        return delivery;
    }
}