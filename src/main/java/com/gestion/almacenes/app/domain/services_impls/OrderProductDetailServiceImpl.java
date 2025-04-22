package com.gestion.almacenes.app.domain.services_impls;

import com.gestion.almacenes.app.domain.entities.*;
import com.gestion.almacenes.app.domain.mappers.OrderProductDetailMapper;
import com.gestion.almacenes.app.domain.services.OrderProductDetailService;
import com.gestion.almacenes.app.persistence.repositories.OrderProductDetailRepository;
import com.gestion.almacenes.app.persistence.repositories.OrderProductRepository;
import com.gestion.almacenes.app.persistence.repositories.ProductRepository;
import com.gestion.almacenes.app.persistence.repositories.StockRepository;
import com.gestion.almacenes.app.presentation.dtos.OrderProductDetailDto;
import com.gestion.almacenes.app.presentation.filters.OrderProductDetailFilter;
import com.gestion.almacenes.app.presentation.pojos.OrderProductDetailPojo;
import com.gestion.almacenes.app.domain.enums.OrderProductTypeActionEnum;
import com.gestion.almacenes.app.domain.enums.StatusFlowEnum;
import com.gestion.almacenes.commons.exception.ValidationErrorException;
import com.gestion.almacenes.commons.util.GenericMapper;
import com.gestion.almacenes.commons.util.PagePojo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.gestion.almacenes.app.domain.services_impls.ExceptionsCustom.*;

@Service
@AllArgsConstructor
@Transactional
public class OrderProductDetailServiceImpl implements
        OrderProductDetailService {

    private final OrderProductDetailRepository orderProductDetailRepository;
    private final OrderProductRepository orderProductRepository;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final GenericMapper<OrderProductDetail, OrderProductDetailDto> genericMapper = new GenericMapper<>(
            OrderProductDetail.class);
    private final OrderProductDetailMapper orderProductDetailMapper;

    @Override
    public List<OrderProductDetailPojo> getAll() {
        return orderProductDetailMapper.fromEntityListToPojoList(
                orderProductDetailRepository.findAllByActiveIsTrue()
        );
    }

    @Override
    public OrderProductDetailPojo create(OrderProductDetailDto orderProductDetaildto) {

        // Obtenemos la cabecera que es la orden de producto
        OrderProduct orderProduct = this.findOrderProductById(
                orderProductDetaildto.getOrderProductId());

        if(!Objects.equals(orderProduct.getStatus(), StatusFlowEnum.BORRADOR.name())){
            errorProcess(String.format("No puede crear su registro por que no se encuentra en estado (%s) la orden",
                    StatusFlowEnum.BORRADOR.name()));
        }

        // Verificamos si no se finalizo la solicitud de orden de ingreso
        this.checkIfOrderIsFinalized(orderProduct);

        // Verificamos si no fue ya ingresado el mismo producto al almacen
        if (orderProductDetailRepository.existsByOrderProduct_IdAndStock_Storehouse_IdAndStock_Product_IdAndActiveTrue(
                orderProductDetaildto.getOrderProductId(),
                orderProduct.getStorehouse().getId(),
                orderProductDetaildto.getProductId())
        ) {
            //Product product = this.findProductById(orderProductDetaildto.getProductId());
            //throw new ValidationErrorException("Ya fue registrado en la orden el Item ("+product.getName()+")");
        }

        Stock stock = this.findStockByStorehouseIdAndProductId(orderProduct.getStorehouse(),
                    orderProductDetaildto.getProductId());



        BigDecimal price = (Objects.equals(orderProduct.getOrderProductType().getAction(), OrderProductTypeActionEnum.RECEIPT.name()))
                ? orderProductDetaildto.getPrice() : // si es un ingreso toma el precio de ingreso
                stock.getUnitPrice(); // si es una salida toma el precio del stock que es el último precio del ponderado

        // Creamos la entidad con los datos de stock (producto y almacén), cantidad ingresada y cabecera
        OrderProductDetail orderProductDetail = OrderProductDetail.builder()
                .stock(stock)
                .quantity(orderProductDetaildto.getQuantity())
                .orderProduct(orderProduct)
                .codeProduct(orderProductDetaildto.getCodeProduct())
                .expirationDateProduct(orderProductDetaildto.getExpirationDateProduct())
                .price(price)
                .build();

        // Guardamos la entidad el detalle

        return orderProductDetailMapper.fromEntityToPojo(
                orderProductDetailRepository.save(orderProductDetail)
        );
    }

    @Override
    public void createList(List<OrderProductDetailDto> orderProductDetailDtos) {
        for (OrderProductDetailDto orderProductDetailDto : orderProductDetailDtos) {
            create(orderProductDetailDto);
        }
    }

    @Override
    public OrderProductDetailPojo update(Integer id, OrderProductDetailDto orderProductDetailDto) {

        OrderProduct orderProduct = this.findOrderProductById(
                orderProductDetailDto.getOrderProductId());

        if(!Objects.equals(orderProduct.getStatus(), StatusFlowEnum.BORRADOR.name())){
            errorProcess(String.format("No puede modificar su registro por que no se encuentra en estado (%s) la orden",
                    StatusFlowEnum.BORRADOR.name()));
        }

        this.checkIfOrderIsFinalized(orderProduct);

        OrderProductDetail orderProductDetailFound = this.findOrderProductDetailById(id);

        orderProductDetailFound.setStock(
                this.findStockByStorehouseIdAndProductId(orderProduct.getStorehouse(),
                        orderProductDetailDto.getProductId())
        );
        orderProductDetailFound.setOrderProduct(orderProduct);
        orderProductDetailFound.setPrice(orderProductDetailDto.getPrice());
        orderProductDetailFound.setQuantity(orderProductDetailDto.getQuantity());


        return orderProductDetailMapper.fromEntityToPojo(
                orderProductDetailRepository.save(orderProductDetailFound)
        );
    }

    @Override
    public void delete(Integer id) {
        OrderProductDetail orderProductDetail = this.findOrderProductDetailById(id);

        if(!Objects.equals(orderProductDetail.getOrderProduct().getStatus(), StatusFlowEnum.BORRADOR.name())){
            errorProcess(String.format("No puede eliminar su registro por que no se encuentra en estado (%s) la orden",
                    StatusFlowEnum.BORRADOR.name()));
        }

        orderProductDetailRepository.delete(orderProductDetail);
    }

    @Override
    public void disabled(Integer id) {
        OrderProductDetail orderProductDetail = this.findOrderProductDetailById(id);
        if (Boolean.TRUE.equals(orderProductDetail.getActive())) {
            orderProductDetail.setActive(false);
            orderProductDetailRepository.save(orderProductDetail);
        } else {
            errorAlreadyDeleted(OrderProductDetail.class, orderProductDetail.getId());
        }
    }

    @Override
    public OrderProductDetailPojo getById(Integer id) {
        return orderProductDetailMapper.fromEntityToPojo(
                this.findOrderProductDetailById(id)
        );
    }

    @Override
    public PagePojo<OrderProductDetailPojo> getByPageAndFilters(Integer pageNumber, Integer pageSize,
                                                            String sortField, String sortOrder, String code, String name) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<OrderProductDetail> orderProductDetailPage = orderProductDetailRepository.findAll(
                pageable);

        return orderProductDetailMapper.toPagePojo(orderProductDetailPage);
    }

    @Override
    public PagePojo<OrderProductDetailPojo> search(int pageNumber, int pageSize, String sortField, String sortOrder, OrderProductDetailFilter filter) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    /*Page<OrderProductDetail> orderProductDetailPage = orderProductDetailRepository.findAll(
            pageable);*/
        Page<OrderProductDetail> orderProductDetailPage
                = orderProductDetailRepository.pageable(filter, pageable);

        return orderProductDetailMapper.toPagePojo(
                orderProductDetailPage
        );
    }

    private OrderProductDetail findOrderProductDetailById(Integer id) {

        return orderProductDetailRepository.findByIdAndActiveIsTrue(id).orElseThrow(
                errorEntityNotFound(OrderProductDetail.class, id)
        );
    }

    private OrderProduct findOrderProductById(Integer id) {
        return orderProductRepository.findByIdAndActiveIsTrue(id).orElseThrow(
                errorEntityNotFound(OrderProduct.class, id)
        );
    }

    private Stock findStockByStorehouseIdAndProductId(Storehouse storehouse, Integer productId) {
        Stock stock = null;
        try {
            stock = stockRepository.findByStorehouse_IdAndProduct_IdAndActiveTrue(storehouse.getId(), productId)
                    .orElseThrow(
                            errorEntityNotFound(Stock.class, storehouse.getId())
                    );
        } catch (Exception e) {
            errorProcess(
                    String.format("El producto ingresado no existe en el almacen (%s)",
                            storehouse.getName()
                    )
            );
        }
        return stock;
    }

    private void checkIfOrderIsFinalized(OrderProduct orderProduct) {
        if (Objects.equals(orderProduct.getStatus(), StatusFlowEnum.FINALIZADO.name())) {
            throw new ValidationErrorException(
                    String.format(
                            "Actualmente el estado de la orden esta en estado (%s) y no puede realizar esta operación",
                            StatusFlowEnum.FINALIZADO.name())
            );
        }
    }

    private Product findProductById(Integer productId) {

        return productRepository.findByIdAndActiveIsTrue(productId).orElseThrow(
                errorEntityNotFound(Product.class, productId)
        );
    }

    /**
     * Verificar si el total del detalle es igual a la cantidad de paquetes por lote
     * @param quantityTotal         Cantidad de productos de todos los paquetes por lote que se registrara
     * @param orderProductDetailDto Cantidad total del detalle de orden
     */
    private void checkIfTotalQuantityIsEqualToQuantityBatch(Double quantityTotal,
                                                            OrderProductDetailDto orderProductDetailDto) {
        if (!Objects.equals(quantityTotal, orderProductDetailDto.getQuantity())) {
            throw new ValidationErrorException(
                    String.format(
                            "La cantidad total (%s) es distinta a la cantidad por empaques (%s) que se envio.",
                            orderProductDetailDto.getQuantity(), quantityTotal)
            );
        }
    }


}
