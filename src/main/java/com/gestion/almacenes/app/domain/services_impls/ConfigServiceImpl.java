package com.gestion.almacenes.app.domain.services_impls;

import com.gestion.almacenes.app.domain.entities.Config;
import com.gestion.almacenes.app.domain.mappers.ConfigMapper;
import com.gestion.almacenes.app.domain.services.ConfigService;
import com.gestion.almacenes.app.persistence.repositories.ConfigRepository;
import com.gestion.almacenes.app.presentation.dtos.ConfigDto;
import com.gestion.almacenes.app.presentation.filters.ConfigFilter;
import com.gestion.almacenes.app.presentation.pojos.ConfigPojo;
import com.gestion.almacenes.commons.config.CacheConfig;
import com.gestion.almacenes.commons.customValidations.ValidationBuilder;
import com.gestion.almacenes.commons.util.PagePojo;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gestion.almacenes.app.domain.services_impls.ExceptionsCustom.*;

@Service
@AllArgsConstructor
public class ConfigServiceImpl implements
    ConfigService {

  private final ConfigRepository configRepository;
  private final ConfigMapper configMapper;

  @Override
  public List<ConfigPojo> getAll() {
    return configMapper.fromEntityListToPojoList(configRepository.findAllByActiveIsTrue());
  }

  @Override
  public ConfigPojo create(ConfigDto configdto) {

    ValidationBuilder.create()
        .exist(
            configRepository.existsByCodeAndActiveIsTrue(configdto.getCode()),
            "Código duplicado."
        )
        .throwIfInvalid();

    if (configRepository.existsByCodeAndActiveIsTrue(configdto.getCode())) {
      errorDuplicateInFieldCode(ConfigDto.class, "code", configdto.getCode());
    }

    Config config = configMapper.fromDtoToEntity(configdto, null);

    return configMapper.fromEntityToPojo(
        configRepository.save(config)
    );
  }

  @Override
  public ConfigPojo update(Integer id, ConfigDto configdto) {
    Config configFound = this.findConfigById(id);
    if (configRepository.existsByCodeAndIdNotAndActiveIsTrue(configdto.getCode(),
        configFound.getId())) {
      errorDuplicateInFieldCode(ConfigDto.class, "code", configdto.getCode());
    }
    Config config = configMapper.fromDtoToEntity(configdto, configFound);

    return configMapper.fromEntityToPojo(
        configRepository.save(config)
    );
  }

  @Override
  public ConfigPojo getById(Integer id) {
    return configMapper.fromEntityToPojo(this.findConfigById(id));
  }

  @Cacheable(value = CacheConfig.USER_INFO_CACHE, unless = "#result == null")
  @Override
  public ConfigPojo getByCode(String code) {
    Config config = configRepository.findByCodeAndActiveTrue(code).orElseThrow(
        errorEntityNotFound(Config.class, "code", code)
    );
    return configMapper.fromEntityToPojo(config);
  }

  @Override
  public void delete(Integer id) {
    Config config = this.findConfigById(id);
    configRepository.delete(config);
  }

  @Override
  public void disabled(Integer id) {
    Config config = this.findConfigById(id);
    if (Boolean.TRUE.equals(config.getActive())) {
      config.setActive(false);
      configRepository.save(config);
    } else {
      errorAlreadyDeleted(Config.class, config.getId());
    }
  }


  @Override
  public PagePojo<ConfigPojo> pageable(Integer pageNumber, Integer pageSize,
                                       String sortField, String sortOrder, ConfigFilter filter) {

    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<Config> configPage = configRepository.pageable(filter, pageable);

    return configMapper.toPagePojo(configPage);
  }

  private Config findConfigById(Integer id) {
    return configRepository.findByIdAndActiveIsTrue(id).orElseThrow(
        errorEntityNotFound(Config.class, id)
    );
  }

  @Override
  public List<String> lista(String code, String name) {

    List<String> lista = configRepository.lista();
    return lista;

  }


}
