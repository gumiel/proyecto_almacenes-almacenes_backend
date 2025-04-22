package com.gestion.almacenes.app.domain.services_impls;

import com.gestion.almacenes.app.domain.mappers.OrderProductDetailHistoryMapper;
import com.gestion.almacenes.app.persistence.projections.OrderProductDetailHistoryProjection;
import com.gestion.almacenes.app.domain.services.OrderProductDetailHistoryService;
import com.gestion.almacenes.app.persistence.repositories.OrderProductDetailHistoryRepository;
import com.gestion.almacenes.commons.util.PagePojo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class OrderProductDetailHistoryServiceImpl implements
    OrderProductDetailHistoryService {

  private final OrderProductDetailHistoryRepository orderProductDetailHistoryRepository;
  private final OrderProductDetailHistoryMapper orderProductDetailHistoryMapper;

  @Override
  public List<OrderProductDetailHistoryProjection> getAllByStockId(Integer id) {
    return orderProductDetailHistoryRepository.findAllByStockIdAndActiveTrue(id);
  }

  @Override
  public PagePojo<OrderProductDetailHistoryProjection> pageableByStockId(int page, int size,
      String sortField, String sortOrder, Integer stockId) {
    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<OrderProductDetailHistoryProjection> productPage = orderProductDetailHistoryRepository.pageableByStockId(stockId, pageable);
    return orderProductDetailHistoryMapper.fromEntity(productPage);
  }
}
