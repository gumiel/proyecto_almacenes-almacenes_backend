package com.gestion.almacenes.app.domain.services_impls;

import com.gestion.almacenes.app.domain.services.CacheService;
import com.gestion.almacenes.commons.config.CacheConfig;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CacheServiceImpl implements CacheService {

  private final CacheManager cacheManager;

  @Override
  public void cleanCache() {
    this.cleanCacheParameters();
  }

  private void  cleanCacheParameters(){
    Optional.ofNullable(CacheConfig.USER_INFO_CACHE).flatMap(name -> Optional.ofNullable(this.cacheManager.getCache(name)))
        .ifPresent(org.springframework.cache.Cache::clear);
  }

}
