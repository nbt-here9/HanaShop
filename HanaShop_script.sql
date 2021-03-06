USE [master]
GO
/****** Object:  Database [HanaShop]    Script Date: 01/19/2021 8:31:19 PM ******/
CREATE DATABASE [HanaShop]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'HanaShop', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\HanaShop.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'HanaShop_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\HanaShop_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [HanaShop] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [HanaShop].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [HanaShop] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [HanaShop] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [HanaShop] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [HanaShop] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [HanaShop] SET ARITHABORT OFF 
GO
ALTER DATABASE [HanaShop] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [HanaShop] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [HanaShop] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [HanaShop] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [HanaShop] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [HanaShop] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [HanaShop] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [HanaShop] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [HanaShop] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [HanaShop] SET  DISABLE_BROKER 
GO
ALTER DATABASE [HanaShop] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [HanaShop] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [HanaShop] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [HanaShop] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [HanaShop] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [HanaShop] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [HanaShop] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [HanaShop] SET RECOVERY FULL 
GO
ALTER DATABASE [HanaShop] SET  MULTI_USER 
GO
ALTER DATABASE [HanaShop] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [HanaShop] SET DB_CHAINING OFF 
GO
ALTER DATABASE [HanaShop] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [HanaShop] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [HanaShop] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'HanaShop', N'ON'
GO
USE [HanaShop]
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 01/19/2021 8:31:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[categoryID] [int] IDENTITY(1,1) NOT NULL,
	[categoryName] [nvarchar](150) NOT NULL,
 CONSTRAINT [PK_Categories] PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Logs]    Script Date: 01/19/2021 8:31:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Logs](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[productID] [int] NOT NULL,
	[username] [varchar](50) NOT NULL,
	[timeUp] [datetime] NOT NULL,
	[action] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_Logs] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 01/19/2021 8:31:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[OrderDetails](
	[orderID] [varchar](12) NOT NULL,
	[productID] [int] NULL,
	[quantity] [int] NOT NULL,
	[total] [bigint] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 01/19/2021 8:31:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Orders](
	[orderID] [varchar](12) NOT NULL,
	[username] [varchar](50) NULL,
	[customerName] [nvarchar](200) NOT NULL,
	[customerAddress] [nvarchar](300) NOT NULL,
	[customerPhone] [varchar](12) NOT NULL,
	[orderDate] [datetime] NOT NULL,
	[paymentMethod] [int] NOT NULL,
	[total] [bigint] NOT NULL,
 CONSTRAINT [PK_Oders] PRIMARY KEY CLUSTERED 
(
	[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PaymentMethods]    Script Date: 01/19/2021 8:31:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PaymentMethods](
	[methodType] [int] NOT NULL,
	[methodName] [varchar](100) NOT NULL,
 CONSTRAINT [PK_PaymentMethods] PRIMARY KEY CLUSTERED 
(
	[methodType] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Products]    Script Date: 01/19/2021 8:31:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products](
	[productID] [int] IDENTITY(1,1) NOT NULL,
	[productName] [nvarchar](150) NULL,
	[image] [nvarchar](200) NULL,
	[description] [nvarchar](300) NULL,
	[price] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[createDate] [datetime] NOT NULL,
	[categoryID] [int] NOT NULL,
	[statusID] [int] NOT NULL CONSTRAINT [DF_Products_statusID]  DEFAULT ((1)),
 CONSTRAINT [PK_Products] PRIMARY KEY CLUSTERED 
(
	[productID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ProductStatus]    Script Date: 01/19/2021 8:31:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ProductStatus](
	[statusID] [int] NOT NULL,
	[statusName] [varchar](50) NOT NULL,
 CONSTRAINT [PK_ProductStatus] PRIMARY KEY CLUSTERED 
(
	[statusID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[UserRoles]    Script Date: 01/19/2021 8:31:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UserRoles](
	[roleID] [int] NOT NULL,
	[roleName] [varchar](50) NOT NULL,
 CONSTRAINT [PK_UserRoles] PRIMARY KEY CLUSTERED 
(
	[roleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Users]    Script Date: 01/19/2021 8:31:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Users](
	[username] [varchar](50) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[googleID] [varchar](64) NULL,
	[name] [nvarchar](200) NOT NULL,
	[address] [nvarchar](300) NOT NULL,
	[phone] [varchar](12) NOT NULL,
	[roleID] [int] NOT NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[Logs]  WITH CHECK ADD  CONSTRAINT [FK_Logs_Products] FOREIGN KEY([productID])
REFERENCES [dbo].[Products] ([productID])
GO
ALTER TABLE [dbo].[Logs] CHECK CONSTRAINT [FK_Logs_Products]
GO
ALTER TABLE [dbo].[Logs]  WITH CHECK ADD  CONSTRAINT [FK_Logs_Users] FOREIGN KEY([username])
REFERENCES [dbo].[Users] ([username])
GO
ALTER TABLE [dbo].[Logs] CHECK CONSTRAINT [FK_Logs_Users]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OderDetails_Oders] FOREIGN KEY([orderID])
REFERENCES [dbo].[Orders] ([orderID])
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OderDetails_Oders]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OderDetails_Products] FOREIGN KEY([productID])
REFERENCES [dbo].[Products] ([productID])
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OderDetails_Products]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Oders_PaymentMethods] FOREIGN KEY([paymentMethod])
REFERENCES [dbo].[PaymentMethods] ([methodType])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Oders_PaymentMethods]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Oders_Users] FOREIGN KEY([username])
REFERENCES [dbo].[Users] ([username])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Oders_Users]
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD  CONSTRAINT [FK_Products_Categories] FOREIGN KEY([categoryID])
REFERENCES [dbo].[Categories] ([categoryID])
GO
ALTER TABLE [dbo].[Products] CHECK CONSTRAINT [FK_Products_Categories]
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD  CONSTRAINT [FK_Products_ProductStatus] FOREIGN KEY([statusID])
REFERENCES [dbo].[ProductStatus] ([statusID])
GO
ALTER TABLE [dbo].[Products] CHECK CONSTRAINT [FK_Products_ProductStatus]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_UserRoles] FOREIGN KEY([roleID])
REFERENCES [dbo].[UserRoles] ([roleID])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_Users_UserRoles]
GO
USE [master]
GO
ALTER DATABASE [HanaShop] SET  READ_WRITE 
GO
