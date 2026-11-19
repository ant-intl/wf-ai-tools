package {basePackage}.wf.model.request;

import java.util.List;

/** create_a_report request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CreateReportRequest {

    /** Required. Idempotency key. Use a unique value (e.g., UUID) for each creation. */
    private String requestId;

    /** Required. Type of report to generate. - `ACCOUNT_STATEMENTS_REPORT`: Account statement report for reconciliation, audit, and third-party verification. */
    private String reportType;

    /** Required. Output file format. - `CSV`: Comma-separated values format. - `XLSX`: Microsoft Excel (XLSX) format. - `PDF`: PDF format; supports needEsign and pdfFields. */
    private String fileFormat;

    /** Report language. Defaults to en_US when omitted. - `en_US`: English. - `zh_CN`: Simplified Chinese. */
    private String language;

    /** Start date of the report data range (inclusive), following the ISO 8601 standard. See Data Types. Provide fromDate and toDate together or omit both; toDate must be greater than or equal to fromDate. */
    private String fromDate;

    /** End date of the report data range (inclusive), following the ISO 8601 standard. See Data Types. Provide fromDate and toDate together or omit both. */
    private String toDate;

    /** Filter the report to specific currencies, following the ISO 4217 standard. See Data Types. Up to 10 currencies. Omit to include all currencies. */
    private List<String> currencies;

    /** Filter the report to specific transaction types. Omit to include all transaction types. See TransactionType. */
    private List<String> transactionTypes;

    /** Filter the report to specific balance types. Omit to include all balance types. See BalanceType. */
    private List<String> balanceTypes;

    /** Filter by budget account IDs. When provided, the BUDGET_BALANCE balance type is added automatically. */
    private List<String> budgetAccountIds;

    /** Whether the report requires an electronic signature. Defaults to false. Effective only when fileFormat is PDF. */
    private Boolean needEsign;

    /** Extra display columns for the PDF report. Effective only when fileFormat is PDF. Omit to use the default columns. - `accountingType`: Include the accounting type column. - `description`: Include the description column. */
    private List<String> pdfFields;

    /** Custom file name. A name is generated automatically when omitted. */
    private String fileName;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getReportType() { return reportType; }

    public void setReportType(String reportType) { this.reportType = reportType; }

    public String getFileFormat() { return fileFormat; }

    public void setFileFormat(String fileFormat) { this.fileFormat = fileFormat; }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }

    public String getFromDate() { return fromDate; }

    public void setFromDate(String fromDate) { this.fromDate = fromDate; }

    public String getToDate() { return toDate; }

    public void setToDate(String toDate) { this.toDate = toDate; }

    public List<String> getCurrencies() { return currencies; }

    public void setCurrencies(List<String> currencies) { this.currencies = currencies; }

    public List<String> getTransactionTypes() { return transactionTypes; }

    public void setTransactionTypes(List<String> transactionTypes) { this.transactionTypes = transactionTypes; }

    public List<String> getBalanceTypes() { return balanceTypes; }

    public void setBalanceTypes(List<String> balanceTypes) { this.balanceTypes = balanceTypes; }

    public List<String> getBudgetAccountIds() { return budgetAccountIds; }

    public void setBudgetAccountIds(List<String> budgetAccountIds) { this.budgetAccountIds = budgetAccountIds; }

    public Boolean getNeedEsign() { return needEsign; }

    public void setNeedEsign(Boolean needEsign) { this.needEsign = needEsign; }

    public List<String> getPdfFields() { return pdfFields; }

    public void setPdfFields(List<String> pdfFields) { this.pdfFields = pdfFields; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CreateReportRequest[REDACTED]"; }
}
