package com.example.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "merchant", createIndex = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantElasticEntity {

    @Id
    @Field(type = FieldType.Text)
    private String merchantId;
    @Field(type = FieldType.Text)
    private String searchKeywords;
    @Field(type = FieldType.Text)
    private String partnerId;
    @Field(type = FieldType.Text)
    private String partnerName;
    @Field(type = FieldType.Text)
    private String merchantRefId;
    @Field(type = FieldType.Text)
    private String merchantKeyword;
    @Field(type = FieldType.Text)
    private String merchantName;
    @Field(type = FieldType.Text)
    private String merchantType;
    @Field(type = FieldType.Text)
    private String merchantCategoryCode;
    @Field(type = FieldType.Text)
    private String merchantSubCategoryCode;
    @Field(type = FieldType.Text)
    private String fullMerchantCategoryCode;

    @Field(type = FieldType.Text)
    private List<String> campaignEligible;

    @Field(type = FieldType.Text)
    private String linkUrl;
    @Field(type = FieldType.Text)
    private String linkType;

    @Field(type = FieldType.Text)
    private boolean isActive;

    @Field(type = FieldType.Auto)
    @GeoPointField
    private GeoPoint location;
}