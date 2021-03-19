CREATE TABLE [dbo].[T_GoodsStocksGlide] (
  [GoodsID] int NOT NULL,
  [dlyorder] int DEFAULT ((0)) NOT NULL,
  [ptypeid] varchar(50) COLLATE Chinese_PRC_CI_AS DEFAULT '' NOT NULL,
  [ktypeid] varchar(25) COLLATE Chinese_PRC_CI_AS DEFAULT '' NOT NULL,
  [GoodsDate] varchar(10) COLLATE Chinese_PRC_CI_AS DEFAULT '' NOT NULL,
  [GoodsOrder] int DEFAULT ((0)) NOT NULL,
  [JobNumber] varchar(50) COLLATE Chinese_PRC_CI_AS DEFAULT '' NULL,
  [OutFactoryDate] varchar(13) COLLATE Chinese_PRC_CI_AS DEFAULT '' NOT NULL,
  [Qty] numeric(22,10) DEFAULT ((0)) NOT NULL,
  [Price] numeric(22,10) DEFAULT ((0)) NOT NULL,
  [Total] numeric(22,10) DEFAULT ((0)) NOT NULL,
  [VchCode] int DEFAULT ((0)) NULL,
  [pgholqty] numeric(22,10) DEFAULT ((0)) NULL,
  [pgholInqty] int DEFAULT ((0)) NULL,
  [GoodsBatchID] varchar(50) COLLATE Chinese_PRC_CI_AS DEFAULT '' NULL,
  [Kwtypeid] varchar(50) COLLATE Chinese_PRC_CI_AS DEFAULT '' NULL,
  [BtypeID] varchar(50) COLLATE Chinese_PRC_CI_AS DEFAULT '' NOT NULL,
  [BuyDate] varchar(10) COLLATE Chinese_PRC_CI_AS DEFAULT '' NOT NULL,
  [UsefulEndDate] varchar(10) COLLATE Chinese_PRC_CI_AS DEFAULT '' NOT NULL,
  [Sltypeid] varchar(50) COLLATE Chinese_PRC_CI_AS DEFAULT '' NOT NULL,
  [goodsorderid] int DEFAULT ((0)) NOT NULL,
  CONSTRAINT [PK_T_GOODSSTOCKSGLIDE] PRIMARY KEY CLUSTERED ([GoodsID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
ON [PRIMARY]
)
ON [PRIMARY]
GO

ALTER TABLE [dbo].[T_GoodsStocksGlide] SET (LOCK_ESCALATION = TABLE)
GO

CREATE NONCLUSTERED INDEX [T_GOODSSTOCKSGLIDEINDEX]
ON [dbo].[T_GoodsStocksGlide] (
  [ktypeid] ASC,
  [GoodsDate] ASC,
  [ptypeid] ASC,
  [JobNumber] ASC,
  [OutFactoryDate] ASC
)
GO

CREATE NONCLUSTERED INDEX [T_GOODSSTOCKSGLIDEINDEX2]
ON [dbo].[T_GoodsStocksGlide] (
  [VchCode] ASC,
  [dlyorder] ASC
)
GO

CREATE NONCLUSTERED INDEX [_WA_Sys_dlyorder_4A55F802]
ON [dbo].[T_GoodsStocksGlide] (
  [dlyorder] ASC
)
GO

CREATE NONCLUSTERED INDEX [_WA_Sys_ptypeid_4A55F802]
ON [dbo].[T_GoodsStocksGlide] (
  [ptypeid] ASC
)
GO

CREATE NONCLUSTERED INDEX [_WA_Sys_GoodsDate_4A55F802]
ON [dbo].[T_GoodsStocksGlide] (
  [GoodsDate] ASC
)
GO

CREATE NONCLUSTERED INDEX [_WA_Sys_Qty_4A55F802]
ON [dbo].[T_GoodsStocksGlide] (
  [Qty] ASC
)
GO

CREATE NONCLUSTERED INDEX [_WA_Sys_Sltypeid_4A55F802]
ON [dbo].[T_GoodsStocksGlide] (
  [Sltypeid] ASC
)
GO

CREATE NONCLUSTERED INDEX [_WA_Sys_BtypeID_4A55F802]
ON [dbo].[T_GoodsStocksGlide] (
  [BtypeID] ASC
)
