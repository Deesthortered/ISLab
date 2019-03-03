CREATE TABLE `Provider` (
	`Provider_ID` bigint NOT NULL AUTO_INCREMENT,
	`Provider_Name` char(30) NOT NULL,
	`Provider_Country` char(30) NOT NULL,
	`Provider_Description` char(255),
	PRIMARY KEY (`Provider_ID`)
);

CREATE TABLE `Customer` (
	`Customer_ID` bigint NOT NULL AUTO_INCREMENT,
	`Customer_Name` char(20) NOT NULL,
	`Customer_Country` char(20) NOT NULL,
	`Customer_Description` char(255),
	PRIMARY KEY (`Customer_ID`)
);

CREATE TABLE `Goods` (
	`Goods_ID` bigint NOT NULL AUTO_INCREMENT,
	`Goods_Name` char(30) NOT NULL,
	`Goods_AveragePrice` bigint NOT NULL,
	`Goods_Description` char(255),
	PRIMARY KEY (`Goods_ID`)
);

CREATE TABLE `ImportDocument` (
	`Document_ID` bigint NOT NULL AUTO_INCREMENT,
	`Document_ProviderID` bigint NOT NULL,
	`Document_ImportDate` DATE NOT NULL,
	`Document_Description` char(255),
	PRIMARY KEY (`Document_ID`)
);

CREATE TABLE `ExportDocument` (
	`Document_ID` bigint NOT NULL AUTO_INCREMENT,
	`Document_CustomerID` bigint NOT NULL,
	`Document_ExportDate` DATE NOT NULL,
	`Document_Description` char(255),
	PRIMARY KEY (`Document_ID`)
);

CREATE TABLE `ImportGoods` (
	`ImportGoods_ID` bigint NOT NULL AUTO_INCREMENT,
	`ImportGoods_DocumentID` bigint NOT NULL,
	`ImportGoods_GoodsID` bigint NOT NULL,
	`ImportGoods_GoodsCount` bigint NOT NULL,
	`ImportGoods_GoodsPrice` bigint NOT NULL,
	PRIMARY KEY (`ImportGoods_ID`)
);

CREATE TABLE `ExportGoods` (
	`ExportGoods_ID` bigint NOT NULL AUTO_INCREMENT,
	`ExportGoods_DocumentID` bigint NOT NULL,
	`ExportGoods_GoodsID` bigint NOT NULL,
	`ExportGoods_GoodsCount` bigint NOT NULL,
	`ExportGoods_GoodsPrice` bigint NOT NULL,
	PRIMARY KEY (`ExportGoods_ID`)
);

CREATE TABLE `Storage` (
	`Storage_ID` bigint NOT NULL AUTO_INCREMENT,
	`Storage_Name` char(30) NOT NULL,
	`Storage_Description` char(255),
	PRIMARY KEY (`Storage_ID`)
);

CREATE TABLE `ImportMoveDocument` (
	`Document_ID` bigint NOT NULL AUTO_INCREMENT,
	`Document_ImportGoodsID` bigint NOT NULL,
	`Document_StorageID` bigint NOT NULL,
	PRIMARY KEY (`Document_ID`)
);

CREATE TABLE `ExportMoveDocument` (
	`Document_ID` bigint NOT NULL AUTO_INCREMENT,
	`Document_ExportGoodsID` bigint NOT NULL,
	`Document_StorageID` bigint NOT NULL,
	PRIMARY KEY (`Document_ID`)
);

CREATE TABLE `AvailableGoods` (
	`Available_GoodsID` bigint NOT NULL,
	`Available_ProviderID` bigint NOT NULL,
	`Available_StorageID` bigint NOT NULL
);

CREATE TABLE `ImportSummary` (
	`Summary_ID` bigint NOT NULL AUTO_INCREMENT,
	`Summary_StartDate` DATE NOT NULL,
	`Summary_EndDate` DATE NOT NULL,
	`Summary_ImportsCount` int NOT NULL,
	`Summary_ImportsAmount` bigint NOT NULL,
	`Summary_MaxPrice` bigint NOT NULL,
	`Summary_MinPrice` bigint NOT NULL,
	PRIMARY KEY (`Summary_ID`)
);

CREATE TABLE `ExportSummary` (
	`Summary_ID` bigint NOT NULL AUTO_INCREMENT,
	`Summary_StartDate` DATE NOT NULL,
	`Summary_EndDate` DATE NOT NULL,
	`Summary_ExportsCount` int NOT NULL,
	`Summary_ExportsAmount` bigint NOT NULL,
	`Summary_MaxPrice` bigint NOT NULL,
	`Summary_MinPrice` bigint NOT NULL,
	PRIMARY KEY (`Summary_ID`)
);

ALTER TABLE `ImportDocument` ADD CONSTRAINT `ImportDocument_fk0` FOREIGN KEY (`Document_ProviderID`) REFERENCES `Provider`(`Provider_ID`);

ALTER TABLE `ExportDocument` ADD CONSTRAINT `ExportDocument_fk0` FOREIGN KEY (`Document_CustomerID`) REFERENCES `Customer`(`Customer_ID`);

ALTER TABLE `ImportGoods` ADD CONSTRAINT `ImportGoods_fk0` FOREIGN KEY (`ImportGoods_DocumentID`) REFERENCES `ImportDocument`(`Document_ID`);

ALTER TABLE `ImportGoods` ADD CONSTRAINT `ImportGoods_fk1` FOREIGN KEY (`ImportGoods_GoodsID`) REFERENCES `Goods`(`Goods_ID`);

ALTER TABLE `ExportGoods` ADD CONSTRAINT `ExportGoods_fk0` FOREIGN KEY (`ExportGoods_DocumentID`) REFERENCES `ExportDocument`(`Document_ID`);

ALTER TABLE `ExportGoods` ADD CONSTRAINT `ExportGoods_fk1` FOREIGN KEY (`ExportGoods_GoodsID`) REFERENCES `Goods`(`Goods_ID`);

ALTER TABLE `ImportMoveDocument` ADD CONSTRAINT `ImportMoveDocument_fk0` FOREIGN KEY (`Document_ImportGoodsID`) REFERENCES `ImportGoods`(`ImportGoods_ID`);

ALTER TABLE `ImportMoveDocument` ADD CONSTRAINT `ImportMoveDocument_fk1` FOREIGN KEY (`Document_StorageID`) REFERENCES `Storage`(`Storage_ID`);

ALTER TABLE `ExportMoveDocument` ADD CONSTRAINT `ExportMoveDocument_fk0` FOREIGN KEY (`Document_ExportGoodsID`) REFERENCES `ExportGoods`(`ExportGoods_ID`);

ALTER TABLE `ExportMoveDocument` ADD CONSTRAINT `ExportMoveDocument_fk1` FOREIGN KEY (`Document_StorageID`) REFERENCES `Storage`(`Storage_ID`);

ALTER TABLE `AvailableGoods` ADD CONSTRAINT `AvailableGoods_fk0` FOREIGN KEY (`Available_GoodsID`) REFERENCES `Goods`(`Goods_ID`);

ALTER TABLE `AvailableGoods` ADD CONSTRAINT `AvailableGoods_fk1` FOREIGN KEY (`Available_ProviderID`) REFERENCES `Provider`(`Provider_ID`);

ALTER TABLE `AvailableGoods` ADD CONSTRAINT `AvailableGoods_fk2` FOREIGN KEY (`Available_StorageID`) REFERENCES `Storage`(`Storage_ID`);

