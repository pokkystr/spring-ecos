package com.example.elasticsearch.component;

import com.krungthai.api.merchantsearch.configuration.properties.FoodFilterConfiguration;
import com.krungthai.api.merchantsearch.controller.merchantsearch.model.MerchantSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SearchQueryComponent {
    private final FoodFilterConfiguration foodFilterConfiguration;

    @Value("${search.index}")
    private String index;
    @Value("${search.radius}")
    private int radius;
    @Value("${search.size}")
    private int sizeOfSearch;
    @Value("${search.fuzziness}")
    private int fuzziness;
    @Value("${exclude-partner}")
    private List<String> excludePartner;

    public SearchQueryComponent(FoodFilterConfiguration foodFilterConfiguration) {
        this.foodFilterConfiguration = foodFilterConfiguration;
    }

    public SearchRequest nextSearchQuery(MerchantSearchRequest request, String campaign, double latitude, double longitude, int nextPage) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder query = QueryBuilders.boolQuery();

        /*build must and must not*/
        query = builderMust(request, campaign);

        query.filter(QueryBuilders.geoDistanceQuery("location")
                .point(latitude, longitude)
                .distance(radius, DistanceUnit.KILOMETERS));

        query.filter(QueryBuilders.matchQuery("isActive", "true"));
        query.filter(QueryBuilders.matchQuery("status.keyword", "SUCCESS"));

        /*set order*/
//        searchSourceBuilder.query(query)
//                .sort(new ScoreSortBuilder().order(SortOrder.DESC));

        /*set order GeoDistance*/
        searchSourceBuilder
                .query(query)
                .sort(new GeoDistanceSortBuilder("location", new GeoPoint(latitude, longitude))
                        .order(SortOrder.ASC)
                        .unit(DistanceUnit.KILOMETERS));

        /*set pageSize*/
        int fromPage = sizeOfSearch * ( nextPage - 1 );
        searchSourceBuilder
                .from(fromPage)
                .size(sizeOfSearch);

        searchRequest.source(searchSourceBuilder);

        log.info("query search next - " + searchRequest.source().toString());
        return searchRequest;
    }

    public SearchRequest searchQuery(MerchantSearchRequest request, String campaign, double latitude, double longitude) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder query = QueryBuilders.boolQuery();

        /*build must and must not*/
        query = builderMust(request, campaign);

        /*build filter*/
        query.filter(QueryBuilders.geoDistanceQuery("location")
                .point(latitude, longitude)
                .distance(radius, DistanceUnit.KILOMETERS));

        query.filter(QueryBuilders.matchQuery("isActive", "true"));
        query.filter(QueryBuilders.matchQuery("status.keyword", "SUCCESS"));

        /*set order*/
//        searchSourceBuilder.query(query)
//                .sort(new ScoreSortBuilder().order(SortOrder.DESC));

        /*set order GeoDistance*/
        searchSourceBuilder
                .query(query)
                .sort(new GeoDistanceSortBuilder("location", new GeoPoint(latitude, longitude))
                        .order(SortOrder.ASC)
                        .unit(DistanceUnit.KILOMETERS));

        /*set pageSize default from 0 */
        searchSourceBuilder
                .from(0)
                .size(sizeOfSearch);

        /*select fields
        searchSourceBuilder.fetchSource(( "merchantKeyword" +
                ",partnerName" +
                ",merchantName" +
                ",fullMerchantCategoryCode" +
                ",location" ).split(","), null);*/

        searchRequest.source(searchSourceBuilder);

        log.info("query search - " + searchRequest.source().toString());
        return searchRequest;
    }

    private BoolQueryBuilder builderMust(MerchantSearchRequest request, String campaign) {
        BoolQueryBuilder query = QueryBuilders.boolQuery();

        /*must*/
        if (request.getKeyword() != null && !request.getKeyword().equals("")) {
            query.must(QueryBuilders.matchQuery("searchKeywords", request.getKeyword())
                    .operator(Operator.AND)
                    .fuzziness(Fuzziness.build(fuzziness)));
        }
        if (campaign != null && !campaign.equals("")) {
            query.must(QueryBuilders.termQuery("campaignEligible.keyword", campaign));
        }
        if (request.getFoodCategories() != null && request.getFoodCategories().size() > 0) {
            query.must(buildSearchFoodCategories(request.getFoodCategories()));
        }
        if (request.getPartners() != null && request.getPartners().size() > 0) {
            query.must(QueryBuilders.termsQuery("partnerName.keyword", request.getPartners()));
        }

        /*mustNot*/
        query.mustNot(QueryBuilders.termsQuery("partnerName.keyword", excludePartner));

        return query;
    }

    private TermsQueryBuilder buildSearchFoodCategories(List<String> foodCategories) {
        List<String> resultCategoriesCodes = new ArrayList<>();
        foodCategories.forEach(val -> {
            switch (val) {
                case "FOODFILTER1":
                    resultCategoriesCodes.addAll(foodFilterConfiguration.getFoodFilter1());
                    break;
                case "FOODFILTER2":
                    resultCategoriesCodes.addAll(foodFilterConfiguration.getFoodFilter2());
                    break;
                case "FOODFILTER3":
                    resultCategoriesCodes.addAll(foodFilterConfiguration.getFoodFilter3());
                    break;
                case "FOODFILTER4":
                    resultCategoriesCodes.addAll(foodFilterConfiguration.getFoodFilter4());
                    break;
                case "FOODFILTER5":
                    resultCategoriesCodes.addAll(foodFilterConfiguration.getFoodFilter5());
                    break;
                case "FOODFILTER6":
                    resultCategoriesCodes.addAll(foodFilterConfiguration.getFoodFilter6());
                    break;
                case "FOODFILTER7":
                    resultCategoriesCodes.addAll(foodFilterConfiguration.getFoodFilter7());
                    break;
                case "FOODFILTER8":
                    resultCategoriesCodes.addAll(foodFilterConfiguration.getFoodFilter8());
                    break;
            }
        });

        return QueryBuilders.termsQuery("fullMerchantCategoryCode", resultCategoriesCodes);
    }

}
