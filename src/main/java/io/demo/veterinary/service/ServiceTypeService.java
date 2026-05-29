package io.demo.veterinary.service;

import io.demo.veterinary.entity.ServiceType;
import io.demo.veterinary.repository.ServiceTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeService extends BaseService<ServiceType, ServiceTypeRepository> {
}
