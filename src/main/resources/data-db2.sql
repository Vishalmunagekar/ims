INSERT INTO APP_ROLES (ID, DESCRIPTION, ROLE_NAME) VALUES(1, 'User Description', 'USER');
INSERT INTO APP_ROLES (ID, DESCRIPTION, ROLE_NAME) VALUES(2, 'Admin Description', 'ADMIN');

INSERT INTO APP_USER (ID, CREATED_AT, LAST_MODIFIED, CONTACT, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME) VALUES(1, '2022-04-15 00:35:17.676000', '2022-04-15 00:35:17.676000', '7843052772', 'Vishal', 'Munagekar', 'qwertyui', 'vishalm');
INSERT INTO APP_USER (ID, CREATED_AT, LAST_MODIFIED, CONTACT, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME) VALUES(2, '2022-04-15 00:35:21.529000', '2022-04-15 00:35:21.529000', '7843052773', 'Tony', 'Stark', 'qwertyui', 'tonys');

INSERT INTO USER_ROLES (USER_ID, ROLE_ID) VALUES(1, 1);
INSERT INTO USER_ROLES (USER_ID, ROLE_ID) VALUES(1, 2);
INSERT INTO USER_ROLES (USER_ID, ROLE_ID) VALUES(2, 2);

INSERT INTO PRODUCT (ID, CREATED_AT, LAST_MODIFIED, CODE, NAME, TOTAL_COST, "TYPE") VALUES(1, '2022-04-15 00:35:18.591000', '2022-04-15 00:35:18.591000', '01', 'IPHONE', 50000.0, 'R');
INSERT INTO PRODUCT (ID, CREATED_AT, LAST_MODIFIED, CODE, NAME, TOTAL_COST, "TYPE") VALUES(2, '2022-04-15 00:35:19.120000', '2022-04-15 00:35:19.120000', '02', 'ONEPLUS', 30000.0, 'L');
INSERT INTO PRODUCT (ID, CREATED_AT, LAST_MODIFIED, CODE, NAME, TOTAL_COST, "TYPE") VALUES(3, '2022-04-15 00:35:22.197000', '2022-04-15 00:35:22.197000', '03', 'PATANJALI', 3000.0, 'C');
INSERT INTO PRODUCT (ID, CREATED_AT, LAST_MODIFIED, CODE, NAME, TOTAL_COST, "TYPE") VALUES(4, '2022-04-15 00:35:22.696000', '2022-04-15 00:35:22.696000', '04', 'WATER', 2000.0, 'M');


INSERT INTO ORDERS (ID, CREATED_AT, LAST_MODIFIED, "DATE", DESCRIPTION, EXTERNAL_ACCOUNT_NUMBER, INTERNAL_ACCOUNT_NUMBER, STATUS) VALUES(1, '2022-04-15 00:35:19.662000', '2022-04-15 00:35:19.662000', '2022-04-15 00:35:19.628000', 'Order01 Description', '221533644789', '221533648652', 'finalized');
INSERT INTO ORDERS (ID, CREATED_AT, LAST_MODIFIED, "DATE", DESCRIPTION, EXTERNAL_ACCOUNT_NUMBER, INTERNAL_ACCOUNT_NUMBER, STATUS) VALUES(2, '2022-04-15 00:35:23.182000', '2022-04-15 00:35:23.182000', '2022-04-15 00:35:23.181000', 'Order02 Description', '221533644789', '221533648652', 'finalized');


INSERT INTO ORDER_DETAIL (ID, CREATED_AT, LAST_MODIFIED, DISCOUNT, PRICE, QUANTITY, PRODUCT_ID, ORDER_ID) VALUES(1, '2022-04-15 00:35:20.024000', '2022-04-15 00:35:20.024000', 0.15, 50000.0, 1, 1, 1);
INSERT INTO ORDER_DETAIL (ID, CREATED_AT, LAST_MODIFIED, DISCOUNT, PRICE, QUANTITY, PRODUCT_ID, ORDER_ID) VALUES(2, '2022-04-15 00:35:20.388000', '2022-04-15 00:35:20.388000', 0.15, 60000.0, 2, 2, 1);
INSERT INTO ORDER_DETAIL (ID, CREATED_AT, LAST_MODIFIED, DISCOUNT, PRICE, QUANTITY, PRODUCT_ID, ORDER_ID) VALUES(3, '2022-04-15 00:35:23.512000', '2022-04-15 00:35:23.512000', 0.15, 3000.0, 1, 3, 2);
INSERT INTO ORDER_DETAIL (ID, CREATED_AT, LAST_MODIFIED, DISCOUNT, PRICE, QUANTITY, PRODUCT_ID, ORDER_ID) VALUES(4, '2022-04-15 00:35:23.848000', '2022-04-15 00:35:23.848000', 0.15, 4000.0, 2, 4, 2);
