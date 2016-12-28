CREATE HBASE TABLE GOSALESDW.SLS_SALES_FACT
( 
ID int,
ORDER_DAY_KEY        int, 
ORGANIZATION_KEY     int, 
EMPLOYEE_KEY         int, 
RETAILER_KEY         int, 
RETAILER_SITE_KEY    int, 
PRODUCT_KEY          int, 
PROMOTION_KEY        int, 
ORDER_METHOD_KEY     int, 
SALES_ORDER_KEY      int, 
SHIP_DAY_KEY         int, 
CLOSE_DAY_KEY        int, 
QUANTITY             int, 
UNIT_COST            decimal(19,2), 
UNIT_PRICE           decimal(19,2), 
UNIT_SALE_PRICE      decimal(19,2), 
GROSS_MARGIN         double, 
SALE_TOTAL           decimal(19,2), 
GROSS_PROFIT         decimal(19,2) 
)
COLUMN MAPPING
(
key        mapped by (ID), 
cf_data:cq_ORDER_DAY_KEY     mapped by (ORDER_DAY_KEY), 
cf_data:cq_ORGANIZATION_KEY     mapped by (ORGANIZATION_KEY), 
cf_data:cq_EMPLOYEE_KEY         mapped by (EMPLOYEE_KEY), 
cf_data:cq_RETAILER_KEY         mapped by (RETAILER_KEY), 
cf_data:cq_RETAILER_SITE_KEY    mapped by (RETAILER_SITE_KEY), 
cf_data:cq_PRODUCT_KEY          mapped by (PRODUCT_KEY), 
cf_data:cq_PROMOTION_KEY        mapped by (PROMOTION_KEY), 
cf_data:cq_ORDER_METHOD_KEY     mapped by (ORDER_METHOD_KEY), 
cf_data:cq_SALES_ORDER_KEY      mapped by (SALES_ORDER_KEY), 
cf_data:cq_SHIP_DAY_KEY         mapped by (SHIP_DAY_KEY), 
cf_data:cq_CLOSE_DAY_KEY        mapped by (CLOSE_DAY_KEY), 
cf_data:cq_QUANTITY             mapped by (QUANTITY), 
cf_data:cq_UNIT_COST            mapped by (UNIT_COST), 
cf_data:cq_UNIT_PRICE           mapped by (UNIT_PRICE), 
cf_data:cq_UNIT_SALE_PRICE      mapped by (UNIT_SALE_PRICE), 
cf_data:cq_GROSS_MARGIN         mapped by (GROSS_MARGIN), 
cf_data:cq_SALE_TOTAL           mapped by (SALE_TOTAL), 
cf_data:cq_GROSS_PROFIT         mapped by (GROSS_PROFIT) 
);
