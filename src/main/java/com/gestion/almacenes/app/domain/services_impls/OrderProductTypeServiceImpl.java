package com.gestion.almacenes.app.domain.services_impls;

import com.gestion.almacenes.app.domain.entities.OrderProductType;
import com.gestion.almacenes.app.domain.mappers.OrderProductTypeMapper;
import com.gestion.almacenes.app.domain.services.OrderProductTypeService;
import com.gestion.almacenes.app.persistence.repositories.OrderProductTypeRepository;
import com.gestion.almacenes.app.presentation.dtos.OrderProductTypeDto;
import com.gestion.almacenes.app.presentation.filters.OrderProductTypeFilter;
import com.gestion.almacenes.app.presentation.pojos.OrderProductTypePojo;
import com.gestion.almacenes.commons.util.PagePojo;
import com.sun.management.OperatingSystemMXBean;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.lang.management.ManagementFactory;
import java.util.List;

import static com.gestion.almacenes.app.domain.services_impls.ExceptionsCustom.*;

@Service
@AllArgsConstructor
public class OrderProductTypeServiceImpl implements
    OrderProductTypeService {

  private final OrderProductTypeRepository orderProductTypeRepository;
  private final ModelMapper modelMapper = new ModelMapper();
  private final OrderProductTypeMapper orderProductTypeMapper;


  @Override
  public List<OrderProductTypePojo> getAll() {
    OperatingSystemMXBean osBean =
        (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    List<OrderProductType> list = orderProductTypeRepository.findAllByActiveIsTrue();
    stopWatch.stop();
    System.out.println("Tiempo de ejecución: " + stopWatch.getTotalTimeMillis() + " ms");


    double cpuLoad = osBean.getSystemCpuLoad() * 100;
    long freeMemory = Runtime.getRuntime().freeMemory();
    long totalMemory = Runtime.getRuntime().totalMemory();
    long usedMemory = totalMemory - freeMemory;

    System.out.println("Uso de CPU: " + cpuLoad + " %");
    System.out.println("Memoria utilizada: " + (usedMemory / (1024 * 1024)) + " MB");
    return orderProductTypeMapper.fromEntityListToPojoList(list);
  }

  @Override
  public OrderProductTypePojo create(OrderProductTypeDto orderProductTypedto) {

    if (orderProductTypeRepository.existsByCodeAndActiveIsTrue(orderProductTypedto.getCode())) {
//      throw new DuplicateException(OrderProductType.class.getSimpleName(), "code", "");
      errorDuplicateInFieldCode(OrderProductTypeDto.class, "code", orderProductTypedto.getCode());
    }
    OrderProductType orderProductType = new OrderProductType();
    modelMapper.map(orderProductTypedto, orderProductType);
    return orderProductTypeMapper.fromEntityToPojo(
            orderProductTypeRepository.save(orderProductType)
    );
  }

  @Override
  public OrderProductTypePojo update(Integer id, OrderProductTypeDto orderProductTypedto) {
    OrderProductType orderProductTypeFound = this.findOrderTypeById(id);
    if (orderProductTypeRepository.existsByCodeAndIdNotAndActiveIsTrue(
        orderProductTypedto.getCode(), orderProductTypeFound.getId())) {
//      throw new DuplicateException("OrderType", "code", "");
      errorDuplicateInFieldCode(OrderProductTypeDto.class, "code", orderProductTypedto.getCode());
    }
    modelMapper.map(orderProductTypedto, orderProductTypeFound);
    return orderProductTypeMapper.fromEntityToPojo(
            orderProductTypeRepository.save(orderProductTypeFound)
    );
  }

  @Override
  public OrderProductTypePojo getById(Integer id) {
    return orderProductTypeMapper.fromEntityToPojo(
            this.findOrderTypeById(id)
    );
  }

  @Override
  public OrderProductTypePojo getByCode(String code) {
    if(orderProductTypeRepository.countByCodeAndActiveTrue(code)>1)
      errorProcess("Existe más de 1 resultado");

    OrderProductType orderProductType = orderProductTypeRepository.findByCodeAndActiveTrue(code).orElseThrow(
            errorEntityNotFound(OrderProductType.class, "code", code));

    return orderProductTypeMapper.fromEntityToPojo(
            orderProductType
    );
  }

  @Override
  public void delete(Integer id) {
    OrderProductType orderProductType = this.findOrderTypeById(id);
    orderProductTypeRepository.delete(orderProductType);
  }

  @Override
  public void disabled(Integer id) {
    OrderProductType orderProductType = this.findOrderTypeById(id);
    if (Boolean.TRUE.equals(orderProductType.getActive())) {
      orderProductType.setActive(false);
      orderProductTypeRepository.save(orderProductType);
    } else {
      errorAlreadyDeleted(OrderProductType.class, orderProductType.getId());
    }
  }

  @Override
  public PagePojo<OrderProductTypePojo> pageable(Integer pageNumber, Integer pageSize,
                                                 String sortField, String sortOrder, OrderProductTypeFilter filter) {

    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<OrderProductType> orderTypePage = orderProductTypeRepository.pageable(filter, pageable);

    return orderProductTypeMapper.toPagePojo(orderTypePage);
  }

  private OrderProductType findOrderTypeById(Integer id) {
    return orderProductTypeRepository.findByIdAndActiveIsTrue(id).orElseThrow(
        errorEntityNotFound(OrderProductType.class, id)
    );
  }

}
