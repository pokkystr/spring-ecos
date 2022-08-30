package com.example.elasticsearch.repository;

import com.example.elasticsearch.entity.MerchantElasticEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantSearchRepository extends ElasticsearchRepository<MerchantElasticEntity, String> {
    Optional<List<MerchantElasticEntity>> findByMerchantName(String shopName);

    Optional<List<MerchantElasticEntity>> findByMerchantNameLike(String shopName);

    Optional<List<MerchantElasticEntity>> findByMerchantNameIn(List<String> keys);

    Optional<List<MerchantElasticEntity>> findByMerchantName(String shopName, Sort sort);
}
