package com.example.elasticsearch;

import com.example.elasticsearch.component.SearchQueryComponent;
import com.example.elasticsearch.repository.MerchantSearchRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticSearchApplication implements CommandLineRunner {

	private final RestHighLevelClient highLevelClient;
	private final MerchantSearchRepository merchantSearchRepository;
	private final SearchQueryComponent searchQuery;


	public ElasticSearchApplication(RestHighLevelClient highLevelClient, MerchantSearchRepository merchantSearchRepository, SearchQueryComponent searchQuery) {
		this.highLevelClient = highLevelClient;
		this.merchantSearchRepository = merchantSearchRepository;
		this.searchQuery = searchQuery;
	}

	public static void main(String[] args) {
		SpringApplication.run(ElasticSearchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Custom Build param search
		SearchResponse response = highLevelClient.search(
				searchQuery.searchQuery(request
						, tokenRedis.getCampaign()
						, tokenRedis.getLatitude()
						, tokenRedis.getLongitude())
				, RequestOptions.DEFAULT);


		// search by param
		// ps. after findByXXX >>> XXX is match same field in elastic
		merchantSearchRepository.findByMerchantName("");
	}
}
