
CREATE TABLE Provider (
                Provider_ID   int PRIMARY KEY,
                Provider_Name char(20)
);

CREATE TABLE Customer (
                Customer_ID   int PRIMARY KEY,
                Customer_Name char(20)
);

CREATE TABLE Storage (
                Storage_ID  int PRIMARY KEY
);

CREATE TABLE Goods (
                Goods_ID    int PRIMARY KEY,
                Goods_Name  char(20),
                Goods_Price int
);

CREATE TABLE _Import (
                Import_ID    int PRIMARY KEY,
                Provider_ID  int,
                Storage_ID   int,
                Goods_ID     int,
                Import_Date  TIMESTAMP,
                Price        int,
                FOREIGN KEY (Provider_ID) REFERENCES Provider(Provider_ID),
                FOREIGN KEY (Storage_ID)  REFERENCES Storage(Storage_ID),
                FOREIGN KEY (Goods_ID)    REFERENCES Goods(Goods_ID)                
);

CREATE TABLE _Export (
                Export_ID    int PRIMARY KEY,
                Customer_ID  int,
                Storage_ID   int,
                Goods_ID     int,
                Export_Date  TIMESTAMP,
                Price        int,
                FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID),
                FOREIGN KEY (Storage_ID)  REFERENCES Storage(Storage_ID),
                FOREIGN KEY (Goods_ID)    REFERENCES Goods(Goods_ID)                
);

CREATE TABLE ImportSummary (
                Summary_ID    int PRIMARY KEY,
                StartDate     TIMESTAMP,
                EndDate       TIMESTAMP,
                ImportsAmount int,
                SummaryPrice  int,
                MaxPrice      int,
                MinPrice      int
);

CREATE TABLE ExportSummary (
                Summary_ID    int PRIMARY KEY,
                StartDate     TIMESTAMP,
                EndDate       TIMESTAMP,
                ExportAmount  int,
                SummaryPrice  int,
                MaxPrice      int,
                MinPrice      int
);