package com.gestion.almacenes.app.domain.services_impls;

import com.gestion.almacenes.app.domain.entities.*;
import com.gestion.almacenes.app.domain.mappers.OrderProductMapper;
import com.gestion.almacenes.app.persistence.projections.OrderProductDetailHistoryProjection;
import com.gestion.almacenes.app.domain.services.OrderProductService;
import com.gestion.almacenes.app.persistence.repositories.*;
import com.gestion.almacenes.app.presentation.dtos.OrderProductDto;
import com.gestion.almacenes.app.presentation.filters.OrderProductFilter;
import com.gestion.almacenes.app.presentation.pojos.OrderProductPojo;
import com.gestion.almacenes.app.domain.enums.OrderProductTypeActionEnum;
import com.gestion.almacenes.app.domain.enums.StatusFlowEnum;
import com.gestion.almacenes.commons.util.GenericMapper;
import com.gestion.almacenes.commons.util.PagePojo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.gestion.almacenes.app.domain.services_impls.ExceptionsCustom.*;

@Service
@AllArgsConstructor
public class OrderProductServiceImpl implements
    OrderProductService {

  private final OrderProductRepository orderProductRepository;
  private final StorehouseRepository storeHouseRepository;
  private final OrderProductTypeRepository orderProductTypeRepository;
  private final GenericMapper<OrderProduct, OrderProductDto> genericMapper = new GenericMapper<>(
      OrderProduct.class);
  private final OrderProductMapper orderProductMapper;
  private final OrderProductDetailRepository orderProductDetailRepository;
  private final StockRepository stockRepository;
  private final SupplierRepository supplierRepository;
  private final OrderProductDetailHistoryRepository orderProductDetailHistoryRepository;

  @Override
  public List<OrderProductPojo> getAll() {

    return orderProductMapper.fromEntityListToPojoList(
            orderProductRepository.findAllByActiveIsTrue()
    );

  }

  @Override
  public OrderProductPojo create(OrderProductDto orderProductdto) {

    if (orderProductdto.getCode() != null && !orderProductdto.getCode().isEmpty()
        && orderProductRepository.existsByCodeAndActiveIsTrue(
        orderProductdto.getCode())) {
      errorProcess(
          "Ya existe el código (" + orderProductdto.getCode() + ")");
    }

    Storehouse storehouse = this.findStorehouseById(orderProductdto.getStorehouseId());
    OrderProductType orderProductType = this.findOrderProductTypeById(
        orderProductdto.getOrderProductTypeId());

    OrderProduct orderProduct = OrderProduct.builder()
        .code(
            (orderProductdto.getCode() == null) ? "S/C" : orderProductdto.getCode()
        )
        .description(orderProductdto.getDescription())
        .registrationDate(
            (orderProductdto.getRegistrationDate() == null) ? LocalDate.now()
                : orderProductdto.getRegistrationDate()
        )
        .registrationTime(
            (orderProductdto.getRegistrationTime() == null) ? LocalTime.now()
                : orderProductdto.getRegistrationTime()
        )
        .storehouse(storehouse)
        .orderProductType(orderProductType)
        .status(StatusFlowEnum.BORRADOR.name()).supplier(
                    (orderProductdto.getSupplierId()!=null)? this.findSupplierById(orderProductdto.getSupplierId()): null
            )
        .build();

    return orderProductMapper.fromEntityToPojo(
            orderProductRepository.save(orderProduct)
    );
  }



  @Override
  public OrderProductPojo update(Integer id, OrderProductDto orderProductdto) {
    OrderProduct orderProductFound = this.findOrderProductById(id);

    if(!Objects.equals(orderProductFound.getStatus(), StatusFlowEnum.BORRADOR.name())){
      errorProcess(String.format("No puede modificar su registro por que no se encuentra en estado (%s)",StatusFlowEnum.BORRADOR.name()));
    }

    if (orderProductRepository.existsByCodeAndIdNotAndActiveIsTrue(
        orderProductdto.getCode(), orderProductFound.getId())) {
      errorDuplicate(OrderProduct.class, "code", orderProductdto.getCode());
    }
    Storehouse storehouse = this.findStorehouseById(orderProductdto.getStorehouseId());
    OrderProductType orderProductType = this.findOrderProductTypeById(
        orderProductdto.getOrderProductTypeId());

    //orderProductFound = genericMapper.fromDto(orderProductdto);
    orderProductFound.setCode(orderProductdto.getCode());
    orderProductFound.setRegistrationDate(orderProductdto.getRegistrationDate());
    orderProductFound.setRegistrationTime(orderProductdto.getRegistrationTime());
    orderProductFound.setDescription(orderProductdto.getDescription());
    orderProductFound.setStorehouse(storehouse);
    orderProductFound.setOrderProductType(orderProductType);

    return orderProductMapper.fromEntityToPojo(
            orderProductRepository.save(orderProductFound)
    );
  }

  @Override
  public OrderProductPojo getById(Integer id) {
    return orderProductMapper.fromEntityToPojo(
            this.findOrderProductById(id)
    );
  }

  @Override
  public OrderProductPojo getByCode(String code) {
    OrderProduct orderProduct = orderProductRepository.findByCodeAndActiveTrue(code).orElseThrow(
            errorEntityNotFound(OrderProduct.class, "code", code)
    );
    return orderProductMapper.fromEntityToPojo(orderProduct);
  }

  @Override
  public void delete(Integer id) {
    OrderProduct orderProduct = this.findOrderProductById(id);

    if(!Objects.equals(orderProduct.getStatus(), StatusFlowEnum.BORRADOR.name())){
      errorProcess(String.format("No puede eliminar su registro por que no se encuentra en estado (%s)",StatusFlowEnum.BORRADOR.name()));
    }

    orderProductRepository.delete(orderProduct);
  }

  @Override
  public void disabled(Integer id) {
    OrderProduct orderProduct = this.findOrderProductById(id);
    if (Boolean.TRUE.equals(orderProduct.getActive())) {
      orderProduct.setActive(false);
      orderProductRepository.save(orderProduct);
    } else {
      errorAlreadyDeleted(OrderProduct.class, orderProduct.getId());
    }
  }

  @Override
  public PagePojo<OrderProductPojo> pageable(Integer pageNumber, Integer pageSize,
                                             String sortField, String sortOrder, OrderProductFilter filter) {

    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<OrderProduct> orderProductPage =
        orderProductRepository.pageable(filter, pageable);

    return orderProductMapper.toPagePojo(orderProductPage);
  }

  @Override
  public OrderProductPojo executeOrderProduct(OrderProductDto dto) {

    OrderProduct orderProduct = this.findOrderProductById(dto.getOrderProductId());
    String action = orderProduct.getOrderProductType().getAction();

    List<OrderProductDetail> orderProductDetails = orderProductDetailRepository.findByOrderProduct_IdAndActiveTrue(
        dto.getOrderProductId());

    //Validacion General
    this.validationExecuteOrderProduct(orderProductDetails, orderProduct);

    for (OrderProductDetail orderProductDetail : orderProductDetails) {
      Double quantityDetail = orderProductDetail.getQuantity();
      Stock stock = orderProductDetail.getStock();
      double newQuantityStock = Double.parseDouble("0");

      // Caso cuando ingresa un producto de almacen
      if (Objects.equals(action, OrderProductTypeActionEnum.RECEIPT.name())) {
        newQuantityStock = stock.getQuantityInStock() + quantityDetail;
      }
      // Caso cuando sale un producto de almacen
      else if (Objects.equals(action, OrderProductTypeActionEnum.DISPATCH.name())) {

        // Validamos que la cantidad del detalle solicitado sea menor a lo que se tiene en stock
        if (stock.getQuantityInStock() < quantityDetail) {
          DecimalFormat mf = new DecimalFormat("0.00");
          mf.setMinimumFractionDigits(2);
          String stingQuantityDetail = mf.format(quantityDetail);
          errorProcess(
              String.format(
                  "El almacen (%s) no tiene la cantidad de (Cant. %s) Items (%s) necesarios. Solo se tienen (%s)",
                  orderProduct.getStorehouse().getName(),
                  stingQuantityDetail,
                  orderProductDetail.getStock().getProduct().getName(),
                  orderProductDetail.getStock().getQuantityInStock().toString()
              )
          );
        }

        newQuantityStock = stock.getQuantityInStock() - quantityDetail;

      }else{
        errorProcess(
                String.format("El proceso no tiene la accion de %s o %s",
                        OrderProductTypeActionEnum.DISPATCH.value(),
                        OrderProductTypeActionEnum.RECEIPT.value())
        );
      }

      // Registro del histórico según el cálculo de precio ponderado
      OrderProductDetailHistory orderProductDetailHistory = this.registerOrderProductDetailHistoryByWeightedPrice(stock, orderProduct, orderProductDetail);


      stock.setQuantityInStock(newQuantityStock);
      stock.setUnitPrice(orderProductDetailHistory.getAveragePrice());
      stockRepository.save(stock);
    }

    orderProduct.setStatus(StatusFlowEnum.FINALIZADO.name());
    return orderProductMapper.fromEntityToPojo(
            orderProductRepository.save(orderProduct)
    );

  }

  private OrderProduct findOrderProductById(Integer id) {
    return orderProductRepository.findByIdAndActiveIsTrue(id).orElseThrow(
        errorEntityNotFound(OrderProduct.class, id)
    );
  }


  private Storehouse findStorehouseById(Integer storehouseId) {
    return storeHouseRepository.findByIdAndActiveIsTrue(storehouseId).orElseThrow(
        errorEntityNotFound(Storehouse.class, storehouseId)
    );
  }

  private OrderProductType findOrderProductTypeById(Integer orderProductTypeId) {
    return orderProductTypeRepository.findByIdAndActiveIsTrue(orderProductTypeId).orElseThrow(
        errorEntityNotFound(OrderProductType.class, orderProductTypeId)
    );
  }

  private void validationExecuteOrderProduct(List<OrderProductDetail> orderProductDetails,
      OrderProduct orderProduct) {
    if (orderProductDetails.isEmpty()) {
      errorProcess("No tiene items para realizar.");
    }

    if (Objects.equals(orderProduct.getStatus(), StatusFlowEnum.FINALIZADO.name())) {
      errorProcess("La orden ya fue procesada.");
    }
  }

  private OrderProductDetailHistory registerOrderProductDetailHistoryByWeightedPrice(Stock stock, OrderProduct orderProduct, OrderProductDetail orderProductDetail){

    // Obtenemos el ultimo registro segun el stock para tener los ultimos datos registrados
    Optional<OrderProductDetailHistoryProjection> optional = orderProductDetailHistoryRepository.findByStockIdAndActiveTrue(stock.getId());

    //En caso qué ya exista más de un registro del stock en el histórico
    if(optional.isPresent()){

      BigDecimal price = (Objects.equals(orderProduct.getOrderProductType().getAction(), OrderProductTypeActionEnum.RECEIPT.name()))?
              orderProductDetail.getPrice(): // en caso qué sea ingreso
              optional.get().getAveragePrice(); // en caso qué sea salida

      BigDecimal valuedBalance = optional.get().getValuedBalance(); //Saldo valorado
      BigDecimal valued = price.multiply(BigDecimal.valueOf(orderProductDetail.getQuantity())); // valorado de la compra
      BigDecimal physicalBalance = optional.get().getPhysicalBalance(); // saldo físico
      BigDecimal quantity = BigDecimal.valueOf(orderProductDetail.getQuantity()); // cantidad ingresada

      BigDecimal averagePrice = (valuedBalance.add(valued)).divide(physicalBalance.add(quantity), 4, RoundingMode.HALF_UP);


      OrderProductDetailHistory orderProductDetailHistory = new OrderProductDetailHistory();
      orderProductDetailHistory.setOrderProduct(orderProductDetail.getOrderProduct());
      orderProductDetailHistory.setOrderProductDetail(orderProductDetail);
      orderProductDetailHistory.setStock(stock);
      orderProductDetailHistory.setDateTimeRegister(LocalDateTime.now());
      //BigDecimal quantity = BigDecimal.valueOf(orderProductDetail.getQuantity());
      orderProductDetailHistory.setQuantity(quantity); // cantidad
      orderProductDetailHistory.setPrice(price); // precio
      orderProductDetailHistory.setAveragePrice(averagePrice); // precio promedio
      orderProductDetailHistory.setPhysicalBalance(
              (Objects.equals(orderProduct.getOrderProductType().getAction(), OrderProductTypeActionEnum.RECEIPT.name()))?
                      physicalBalance.add(quantity): // en caso qué sea ingreso
                      physicalBalance.subtract(quantity) // en caso qué sea salida
      ); // Saldo físico
      orderProductDetailHistory.setValuedBalance(
              (Objects.equals(orderProduct.getOrderProductType().getAction(), OrderProductTypeActionEnum.RECEIPT.name()))?
                      physicalBalance.add(quantity).multiply(averagePrice):
                      physicalBalance.subtract(quantity).multiply(averagePrice)
      );
      return orderProductDetailHistoryRepository.save(orderProductDetailHistory);
    }
    //En caso qué no exista registros en el stock del histórico
    else{


      BigDecimal price = (Objects.equals(orderProduct.getOrderProductType().getAction(), "DISPATCH"))
              ? stock.getUnitPrice() // En caso de que sea salida
              :orderProductDetail.getPrice(); // En caso de que sea Ingreso

      OrderProductDetailHistory orderProductDetailHistory = new OrderProductDetailHistory();
      orderProductDetailHistory.setOrderProduct(orderProductDetail.getOrderProduct());
      orderProductDetailHistory.setOrderProductDetail(orderProductDetail);
      orderProductDetailHistory.setStock(stock);
      orderProductDetailHistory.setDateTimeRegister(LocalDateTime.now());
      BigDecimal quantity = BigDecimal.valueOf(orderProductDetail.getQuantity());
      orderProductDetailHistory.setQuantity(quantity);
      orderProductDetailHistory.setPrice(price);
      orderProductDetailHistory.setAveragePrice(price);
      orderProductDetailHistory.setPhysicalBalance(quantity);
      orderProductDetailHistory.setValuedBalance(quantity.multiply(price));
      return orderProductDetailHistoryRepository.save(orderProductDetailHistory);

    }
  }

  private Supplier findSupplierById(Integer supplierId) {
    return supplierRepository.findByIdAndActiveIsTrue(supplierId).orElseThrow(
            errorEntityNotFound(Supplier.class, supplierId)
    );
  }

}