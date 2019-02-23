DROP TABLE IF EXISTS Import;
DROP TABLE IF EXISTS Export;
DROP TABLE IF EXISTS Provider;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Storage;
DROP TABLE IF EXISTS Goods;
DROP TABLE IF EXISTS ImportSummary;
DROP TABLE IF EXISTS ExportSummary;

CREATE TABLE Provider (
                Provider_ID    bigint auto_increment PRIMARY KEY not null,
                Provider_Name  char(30) not null
);
CREATE TABLE Customer (
                Customer_ID    bigint auto_increment PRIMARY KEY not null,
                Customer_Name  char(30) not null
);
CREATE TABLE Storage (
                Storage_ID     bigint auto_increment PRIMARY KEY not null,
		Storage_Number char(10) not null
);
CREATE TABLE Goods (
                Goods_ID            bigint auto_increment PRIMARY KEY not null,
                Goods_Name          char(30) not null,
                Goods_Average_Price bigint not null
);

CREATE TABLE Import (
                Import_ID    bigint auto_increment PRIMARY KEY not null,
                Provider_ID  bigint not null,
                Storage_ID   bigint not null,
                Goods_ID     bigint not null,
                Import_Date  TIMESTAMP not null,
                Price        bigint not null,
                FOREIGN KEY (Provider_ID) REFERENCES Provider(Provider_ID),
                FOREIGN KEY (Storage_ID)  REFERENCES Storage(Storage_ID),
                FOREIGN KEY (Goods_ID)    REFERENCES Goods(Goods_ID)                
);

CREATE TABLE Export (
                Export_ID    bigint auto_increment PRIMARY KEY not null,
                Customer_ID  bigint not null,
                Storage_ID   bigint not null,
                Goods_ID     bigint not null,
                Export_Date  TIMESTAMP not null,
                Price        bigint not null,
                FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID),
                FOREIGN KEY (Storage_ID)  REFERENCES Storage(Storage_ID),
                FOREIGN KEY (Goods_ID)    REFERENCES Goods(Goods_ID)                
);

CREATE TABLE ImportSummary (
                Summary_ID    bigint auto_increment PRIMARY KEY not null,
                StartDate     TIMESTAMP not null,
                EndDate       TIMESTAMP not null,
                ImportsAmount int not null,
                SummaryPrice  bigint not null,
                MaxPrice      bigint not null,
                MinPrice      bigint not null
);

CREATE TABLE ExportSummary (
                Summary_ID    bigint auto_increment PRIMARY KEY not null,
                StartDate     TIMESTAMP not null,
                EndDate       TIMESTAMP not null,
                ExportAmount  int not null,
                SummaryPrice  bigint not null,
                MaxPrice      bigint not null,
                MinPrice      bigint not null
);