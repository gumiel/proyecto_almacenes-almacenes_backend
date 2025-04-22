package com.gestion.almacenes.app.domain.services_impls;

import com.gestion.almacenes.app.domain.entities.ConfigurationParameter;
import com.gestion.almacenes.app.domain.entities.Product;
import com.gestion.almacenes.app.domain.mappers.ConfigurationParameterMapper;
import com.gestion.almacenes.app.domain.services.ConfigurationParameterService;
import com.gestion.almacenes.app.persistence.repositories.ConfigurationParameterRepository;
import com.gestion.almacenes.app.presentation.dtos.ConfigurationParameterDto;
import com.gestion.almacenes.app.presentation.filters.ConfigurationParameterFilter;
import com.gestion.almacenes.app.presentation.pojos.ConfigurationParameterPojo;
import com.gestion.almacenes.commons.util.PagePojo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gestion.almacenes.app.domain.services_impls.ExceptionsCustom.errorAlreadyDeleted;
import static com.gestion.almacenes.app.domain.services_impls.ExceptionsCustom.errorEntityNotFound;

@Transactional
@Service
@AllArgsConstructor
public class ConfigurationParameterServiceImpl implements
        ConfigurationParameterService {

  private final ConfigurationParameterRepository configurationParameterRepository;
  private final ConfigurationParameterMapper configurationParameterMapper;

  @Override
  public List<ConfigurationParameterPojo> getAll() {
    return configurationParameterMapper.fromEntityListToPojoList(
            configurationParameterRepository.findByActiveTrue()
    );
  }

  @Override
  public ConfigurationParameterPojo create(ConfigurationParameterDto configurationParameterDto) {

    ConfigurationParameter configurationParameter = configurationParameterMapper.fromDtoToEntity(configurationParameterDto, null);

    return configurationParameterMapper.fromEntityToPojo(
            configurationParameterRepository.save(configurationParameter)
    );
  }


  @Override
  public ConfigurationParameterPojo update(Integer id, ConfigurationParameterDto configurationParameterDto) {
    ConfigurationParameter configurationParameterFound = this.findConfigurationParameterById(id);

    ConfigurationParameter configurationParameter = configurationParameterMapper.fromDtoToEntity(configurationParameterDto, configurationParameterFound);

    return configurationParameterMapper.fromEntityToPojo(
            configurationParameterRepository.save(configurationParameter)
    );

  }

  @Override
  public ConfigurationParameterPojo getById(Integer id) {
    return configurationParameterMapper.fromEntityToPojo(
            this.findConfigurationParameterById(id)
    );
  }


  @Override
  public void delete(Integer id) {
    ConfigurationParameter configurationParameter = this.findConfigurationParameterById(id);
    configurationParameterRepository.delete(configurationParameter);
  }

  @Override
  public void disabled(Integer id) {
    ConfigurationParameter configurationParameter = this.findConfigurationParameterById(id);
    if (Boolean.TRUE.equals(configurationParameter.getActive())) {
      configurationParameter.setActive(false);
      configurationParameterRepository.save(configurationParameter);
    } else {
      errorAlreadyDeleted(ConfigurationParameter.class, configurationParameter.getId());
    }
  }

  @Override
  public PagePojo<ConfigurationParameterPojo> pageable(Integer pageNumber, Integer pageSize, String sortField,
      String sortOrder, ConfigurationParameterFilter filter) {

    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<ConfigurationParameter> configurationParameterPage = configurationParameterRepository.findAll(filter, pageable);

    return configurationParameterMapper.toPagePojo(configurationParameterPage);
  }

  private ConfigurationParameter findConfigurationParameterById(Integer id) {
    return configurationParameterRepository.findById(id).orElseThrow(
        errorEntityNotFound(Product.class, id)
    );
  }

}
