DROP TABLE IF EXISTS SH_Customer;

-- Customer 客戶表格

CREATE TABLE IF NOT EXISTS SH_Customer (
cmid    				int  AUTO_INCREMENT NOT NULL,    	--  客戶編號
name					varchar(20), 
unpaid_order_amount 	Decimal,      		-- 未付款之訂購金額，不能超過5000元
Rejected				char(1),			-- 'Y' 拒絕往來戶
PRIMARY KEY (cmid)
); 

INSERT INTO SH_Customer VALUES (null, '張君雅', 200, ' ');
INSERT INTO SH_Customer VALUES (null, '劉立方', 2500, ' ');

DROP TABLE IF EXISTS SH_Product;

-- Product	產品表格	

CREATE TABLE IF NOT EXISTS  SH_Product (
pdid    					int  AUTO_INCREMENT NOT NULL,   	--  產品編號 
productName					varchar(20),	-- 產品名稱 
stock		 				int,			-- 庫存數量，庫存數量 - 訂購數量  不能小於 0
PRIMARY KEY (pdid)
);

INSERT INTO SH_Product VALUES (null, '原子筆', 45);
INSERT INTO SH_Product VALUES (null, '粉蠟筆', 45);
INSERT INTO SH_Product VALUES (null, '立可白', 45);
INSERT INTO SH_Product VALUES (null, '直尺', 45);


DROP TABLE IF EXISTS SH_Campaign;

-- Campaign 銷售活動表格
											 
CREATE TABLE IF NOT EXISTS  SH_Campaign (
adid    					int  		NOT NULL,   -- 	廣告編號
pdid		    			int  		NOT NULL,   -- 	產品編號
startDateTime				Datetime  	NOT NULL,   -- 	開始日期時間
endDateTime	    			Datetime  	NOT NULL,   -- 	結束日期時間
productPrice				int			NOT NULL, 	-- 	產品價格

PRIMARY KEY (adid, pdid)
);

INSERT INTO SH_Campaign VALUES (1, 1, '2017-05-20 00:00:00',  '2017-05-31 23:59:59', 32);
INSERT INTO SH_Campaign VALUES (1, 2, '2017-05-20 00:00:00',  '2017-05-31 23:59:59', 50);
INSERT INTO SH_Campaign VALUES (1, 3, '2017-05-20 00:00:00',  '2017-05-31 23:59:59', 30);
INSERT INTO SH_Campaign VALUES (1, 4, '2017-05-20 00:00:00',  '2017-05-31 23:59:59', 10);
INSERT INTO SH_Campaign VALUES (2, 1, '2017-07-28 02:00:00',  '2017-08-10 22:30:00', 28);
INSERT INTO SH_Campaign VALUES (2, 2, '2017-07-28 02:00:00',  '2017-08-10 22:30:00', 45);
INSERT INTO SH_Campaign VALUES (2, 3, '2017-07-28 02:00:00',  '2017-08-10 22:30:00', 20);
INSERT INTO SH_Campaign VALUES (2, 4, '2017-07-28 02:00:00',  '2017-08-10 22:30:00', 8);
INSERT INTO SH_Campaign VALUES (3, 1, '2017-08-15 00:00:00',  '2017-08-31 23:59:59', 32);
INSERT INTO SH_Campaign VALUES (3, 2, '2017-08-15 00:00:00',  '2017-08-31 23:59:59', 50);
INSERT INTO SH_Campaign VALUES (3, 3, '2017-08-15 00:00:00',  '2017-08-31 23:59:59', 30);
INSERT INTO SH_Campaign VALUES (3, 4, '2017-08-15 00:00:00',  '2017-08-31 23:59:59', 10);


DROP TABLE IF EXISTS SH_Advertise;

-- Advertise 	廣告表格

CREATE TABLE SH_Advertise (
adid    					int AUTO_INCREMENT	NOT NULL,   -- 	廣告編號
`status`    				int  				NOT NULL,   -- 	廣告狀態：1 行銷，2 結束，3 終止，-1: 暫停
description					VARCHAR(32)  		NOT NULL,   -- 	廣告說明
PRIMARY KEY (adid)
);

INSERT INTO SH_Advertise (adid, `status`, description) VALUES (1, 1, '十周年慶大特賣' );
INSERT INTO SH_Advertise (adid, `status`, description) VALUES (2, 1, '慶祝父親節暨年中慶' );

DROP TABLE IF EXISTS SH_Orders;

-- Orders 訂單表格

CREATE TABLE SH_Orders (
odid					int AUTO_INCREMENT	NOT NULL,   												-- 	訂單編號
cmid					int  		NOT NULL,   												-- 	客戶編號
orderDate				datetime,											-- 	訂單日期時間
amount					NUMERIC(12,2), 												-- 	訂單總金額
shippingAddress			varchar(50),	--	發貨地址
BNO						varchar(10),	--	客戶統一編號
InvoiceTitle			varchar(50),	--	客戶發票抬頭
PRIMARY KEY (odid)
);

-- OrderItem	訂單名細表格	

DROP TABLE IF EXISTS SH_OrderItem;
--

CREATE TABLE SH_OrderItem (
oiid				int  AUTO_INCREMENT	NOT NULL, 								-- 	訂單明細編號
odid				int  		NOT NULL, 		-- 	訂單編號
adid    			int  		NOT NULL, 	   	--  廣告代號
pdid    			int  		NOT NULL, 	   	--  產品編號
quantity   			int,					   	--  產品數量
price    			int,						--  產品單價
PRIMARY KEY (oiid)
);
