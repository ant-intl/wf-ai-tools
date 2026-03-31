package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.CardTemplateField;
import lombok.Data;

import java.util.List;

/**
 * Response for inquiryBeneficiaryTemplate API.
 * Contains card template field definitions for binding beneficiaries.
 */
@Data
public class InquiryBeneficiaryTemplateResponse {

    /**
     * API result
     */
    private Result result;

    /**
     * Unique response ID
     */
    private String responseId;

    /**
     * Standard card template field list
     */
    private List<CardTemplateField> cardTemplateData;

    /**
     * Local clearing network template fields
     */
    private List<CardTemplateField> localCardTemplateData;

    /**
     * Cross-border clearing network template fields
     */
    private List<CardTemplateField> crossBorderCardTemplateData;
}
