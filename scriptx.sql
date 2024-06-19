USE [master]
GO
/****** Object:  Database [db_datn]    Script Date: 6/18/2024 7:18:51 PM ******/
CREATE DATABASE [db_datn]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'db_datn', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\db_datn.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'db_datn_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\db_datn_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [db_datn] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [db_datn].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [db_datn] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [db_datn] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [db_datn] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [db_datn] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [db_datn] SET ARITHABORT OFF 
GO
ALTER DATABASE [db_datn] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [db_datn] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [db_datn] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [db_datn] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [db_datn] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [db_datn] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [db_datn] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [db_datn] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [db_datn] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [db_datn] SET  ENABLE_BROKER 
GO
ALTER DATABASE [db_datn] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [db_datn] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [db_datn] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [db_datn] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [db_datn] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [db_datn] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [db_datn] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [db_datn] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [db_datn] SET  MULTI_USER 
GO
ALTER DATABASE [db_datn] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [db_datn] SET DB_CHAINING OFF 
GO
ALTER DATABASE [db_datn] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [db_datn] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [db_datn] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [db_datn] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [db_datn] SET QUERY_STORE = ON
GO
ALTER DATABASE [db_datn] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [db_datn]
GO
/****** Object:  Table [dbo].[account]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[avatar] [varchar](255) NULL,
	[code] [varchar](255) NULL,
	[created_time] [date] NULL,
	[email] [varchar](255) NULL,
	[name] [varchar](255) NULL,
	[password] [varchar](255) NULL,
	[status] [int] NULL,
	[updated_time] [date] NULL,
	[role_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[address]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[address](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_time] [datetime2](7) NULL,
	[default_address] [varchar](255) NULL,
	[district] [int] NULL,
	[name] [varchar](255) NULL,
	[note] [varchar](255) NULL,
	[phone_number] [varchar](255) NULL,
	[province] [int] NULL,
	[specific_address] [varchar](255) NULL,
	[status] [int] NULL,
	[updated_time] [datetime2](7) NULL,
	[ward] [varchar](255) NULL,
	[account_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[brand]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[brand](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_time] [datetime2](7) NULL,
	[name] [varchar](255) NULL,
	[status] [int] NULL,
	[updated_time] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[cart]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cart](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_time] [datetime2](7) NULL,
	[status] [int] NULL,
	[updated_time] [datetime2](7) NULL,
	[account_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[cart_detail]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cart_detail](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[quantity] [int] NULL,
	[status] [int] NULL,
	[cart_id] [bigint] NULL,
	[shoe_detail_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_time] [datetime2](7) NULL,
	[name] [varchar](255) NULL,
	[status] [int] NULL,
	[updated_time] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[color]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[color](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_time] [datetime2](7) NULL,
	[name] [varchar](255) NULL,
	[status] [int] NULL,
	[updated_time] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[comment]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[comment](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[content] [varchar](255) NULL,
	[date] [datetime2](7) NULL,
	[stars] [int] NULL,
	[status] [int] NULL,
	[account_id] [bigint] NULL,
	[order_id] [bigint] NULL,
	[shoe_detail_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[email_template]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[email_template](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[mail_content] [varchar](255) NULL,
	[mail_type] [int] NULL,
	[subject] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[image]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[image](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_time] [datetime2](7) NULL,
	[img_name] [varchar](255) NULL,
	[img_url] [varchar](255) NULL,
	[shoe_detail_id] [bigint] NULL,
	[shoe_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[material]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[material](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_time] [datetime2](7) NULL,
	[name] [varchar](255) NULL,
	[status] [int] NULL,
	[updated_time] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[my_order]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[my_order](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[address] [varchar](255) NULL,
	[code] [varchar](255) NULL,
	[created_by] [varchar](255) NULL,
	[create_date] [datetime2](7) NULL,
	[customer_name] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[desired_date] [datetime2](7) NULL,
	[email] [varchar](255) NULL,
	[money_reduce] [numeric](38, 2) NULL,
	[note] [varchar](255) NULL,
	[pay_date] [datetime2](7) NULL,
	[phone_number] [varchar](255) NULL,
	[receive_date] [datetime2](7) NULL,
	[ship_date] [datetime2](7) NULL,
	[ship_fee] [numeric](38, 2) NULL,
	[status] [int] NULL,
	[total_money] [numeric](38, 2) NULL,
	[total_revenue] [numeric](38, 2) NULL,
	[type] [varchar](255) NULL,
	[updated_by] [varchar](255) NULL,
	[account_id] [bigint] NULL,
	[voucher_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_detail]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_detail](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[discount] [numeric](38, 2) NULL,
	[price] [numeric](38, 2) NULL,
	[quantity] [int] NULL,
	[status] [int] NULL,
	[order_id] [bigint] NULL,
	[shoe_detail_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_history]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_history](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[account] [bigint] NULL,
	[created_by] [varchar](255) NULL,
	[created_time] [datetime2](7) NULL,
	[note] [varchar](255) NULL,
	[type] [varchar](255) NULL,
	[order_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[payment_method]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[payment_method](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[method] [varchar](255) NULL,
	[note] [varchar](255) NULL,
	[payment_time] [datetime2](7) NULL,
	[status] [int] NULL,
	[total] [numeric](38, 2) NULL,
	[order_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[role]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[role](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[created_time] [datetime2](7) NULL,
	[name] [varchar](20) NULL,
	[status] [int] NULL,
	[updated_time] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[roleaccount]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[roleaccount](
	[account_id] [bigint] NOT NULL,
	[role_id] [int] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Shoe]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Shoe](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[code] [varchar](255) NULL,
	[created_time] [datetime2](7) NULL,
	[description] [varchar](255) NULL,
	[name] [varchar](255) NULL,
	[shoe_height] [float] NULL,
	[shoe_length] [float] NULL,
	[shoe_width] [float] NULL,
	[status] [int] NULL,
	[updated_time] [datetime2](7) NULL,
	[brand_id] [bigint] NULL,
	[category_id] [bigint] NULL,
	[material_id] [bigint] NULL,
	[sole_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[shoe_detail]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[shoe_detail](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_time] [datetime2](7) NULL,
	[created_by] [varchar](255) NULL,
	[price] [numeric](38, 2) NULL,
	[quantity] [int] NULL,
	[status] [int] NULL,
	[updated_time] [datetime2](7) NULL,
	[updated_by] [varchar](255) NULL,
	[color_id] [bigint] NULL,
	[shoe_id] [bigint] NULL,
	[size_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Size]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Size](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_time] [datetime2](7) NULL,
	[name] [int] NULL,
	[status] [int] NULL,
	[updated_time] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sole]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sole](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_time] [datetime2](7) NULL,
	[name] [varchar](255) NULL,
	[status] [int] NULL,
	[updated_time] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[voucher]    Script Date: 6/18/2024 7:18:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[voucher](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[code] [varchar](255) NULL,
	[created_time] [datetime2](7) NULL,
	[discount_amount] [numeric](38, 2) NULL,
	[end_date] [datetime2](7) NULL,
	[maximum_reduction_value] [numeric](38, 2) NULL,
	[min_order_value] [numeric](38, 2) NULL,
	[name] [varchar](255) NULL,
	[quantity] [int] NULL,
	[reduce_form] [int] NULL,
	[start_date] [datetime2](7) NULL,
	[status] [int] NULL,
	[updated_time] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[account]  WITH CHECK ADD  CONSTRAINT [FKd4vb66o896tay3yy52oqxr9w0] FOREIGN KEY([role_id])
REFERENCES [dbo].[role] ([id])
GO
ALTER TABLE [dbo].[account] CHECK CONSTRAINT [FKd4vb66o896tay3yy52oqxr9w0]
GO
ALTER TABLE [dbo].[address]  WITH CHECK ADD  CONSTRAINT [FKascogpq8x3gfx04oy2fr6l3i5] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[address] CHECK CONSTRAINT [FKascogpq8x3gfx04oy2fr6l3i5]
GO
ALTER TABLE [dbo].[cart]  WITH CHECK ADD  CONSTRAINT [FK4pljlvncf45mr98etwpubxvbt] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[cart] CHECK CONSTRAINT [FK4pljlvncf45mr98etwpubxvbt]
GO
ALTER TABLE [dbo].[cart_detail]  WITH CHECK ADD  CONSTRAINT [FKhaotc29w1xvfigjiuxmxvy3g4] FOREIGN KEY([shoe_detail_id])
REFERENCES [dbo].[shoe_detail] ([id])
GO
ALTER TABLE [dbo].[cart_detail] CHECK CONSTRAINT [FKhaotc29w1xvfigjiuxmxvy3g4]
GO
ALTER TABLE [dbo].[cart_detail]  WITH CHECK ADD  CONSTRAINT [FKrg4yopd2252nwj8bfcgq5f4jp] FOREIGN KEY([cart_id])
REFERENCES [dbo].[cart] ([id])
GO
ALTER TABLE [dbo].[cart_detail] CHECK CONSTRAINT [FKrg4yopd2252nwj8bfcgq5f4jp]
GO
ALTER TABLE [dbo].[comment]  WITH CHECK ADD  CONSTRAINT [FK4bdae8foac2s6x1nswk50g7o7] FOREIGN KEY([shoe_detail_id])
REFERENCES [dbo].[shoe_detail] ([id])
GO
ALTER TABLE [dbo].[comment] CHECK CONSTRAINT [FK4bdae8foac2s6x1nswk50g7o7]
GO
ALTER TABLE [dbo].[comment]  WITH CHECK ADD  CONSTRAINT [FKmhqvhfxg62gjfg0uhvu045udt] FOREIGN KEY([order_id])
REFERENCES [dbo].[my_order] ([id])
GO
ALTER TABLE [dbo].[comment] CHECK CONSTRAINT [FKmhqvhfxg62gjfg0uhvu045udt]
GO
ALTER TABLE [dbo].[comment]  WITH CHECK ADD  CONSTRAINT [FKp41h5al2ajp1q0u6ox3i68w61] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[comment] CHECK CONSTRAINT [FKp41h5al2ajp1q0u6ox3i68w61]
GO
ALTER TABLE [dbo].[image]  WITH CHECK ADD  CONSTRAINT [FKb63106dlrq40u4b925ofmb79c] FOREIGN KEY([shoe_id])
REFERENCES [dbo].[Shoe] ([id])
GO
ALTER TABLE [dbo].[image] CHECK CONSTRAINT [FKb63106dlrq40u4b925ofmb79c]
GO
ALTER TABLE [dbo].[image]  WITH CHECK ADD  CONSTRAINT [FKhrv80oqqjlcib4dw0bv24pfyn] FOREIGN KEY([shoe_detail_id])
REFERENCES [dbo].[shoe_detail] ([id])
GO
ALTER TABLE [dbo].[image] CHECK CONSTRAINT [FKhrv80oqqjlcib4dw0bv24pfyn]
GO
ALTER TABLE [dbo].[my_order]  WITH CHECK ADD  CONSTRAINT [FKqg19676hpbg0hpcqi5bgjkqyr] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[my_order] CHECK CONSTRAINT [FKqg19676hpbg0hpcqi5bgjkqyr]
GO
ALTER TABLE [dbo].[my_order]  WITH CHECK ADD  CONSTRAINT [FKr4oqfrxbh9gjpqbkw4mt29bad] FOREIGN KEY([voucher_id])
REFERENCES [dbo].[voucher] ([id])
GO
ALTER TABLE [dbo].[my_order] CHECK CONSTRAINT [FKr4oqfrxbh9gjpqbkw4mt29bad]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FK87an4j878d94rbg3icae0r1e] FOREIGN KEY([order_id])
REFERENCES [dbo].[my_order] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FK87an4j878d94rbg3icae0r1e]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FKqpdh0hunilgfixuxjhh7oveda] FOREIGN KEY([shoe_detail_id])
REFERENCES [dbo].[shoe_detail] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FKqpdh0hunilgfixuxjhh7oveda]
GO
ALTER TABLE [dbo].[order_history]  WITH CHECK ADD  CONSTRAINT [FKixl24wwt8lpedyl0qnc2ccu54] FOREIGN KEY([order_id])
REFERENCES [dbo].[my_order] ([id])
GO
ALTER TABLE [dbo].[order_history] CHECK CONSTRAINT [FKixl24wwt8lpedyl0qnc2ccu54]
GO
ALTER TABLE [dbo].[payment_method]  WITH CHECK ADD  CONSTRAINT [FKjgjdj0yh3lf99n0yh33sqasoc] FOREIGN KEY([order_id])
REFERENCES [dbo].[my_order] ([id])
GO
ALTER TABLE [dbo].[payment_method] CHECK CONSTRAINT [FKjgjdj0yh3lf99n0yh33sqasoc]
GO
ALTER TABLE [dbo].[roleaccount]  WITH CHECK ADD  CONSTRAINT [FK15phj5cffij91xfqnpn3jb78j] FOREIGN KEY([role_id])
REFERENCES [dbo].[role] ([id])
GO
ALTER TABLE [dbo].[roleaccount] CHECK CONSTRAINT [FK15phj5cffij91xfqnpn3jb78j]
GO
ALTER TABLE [dbo].[roleaccount]  WITH CHECK ADD  CONSTRAINT [FKeu51ua9a9kw9wp9dq91jgcog6] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[roleaccount] CHECK CONSTRAINT [FKeu51ua9a9kw9wp9dq91jgcog6]
GO
ALTER TABLE [dbo].[Shoe]  WITH CHECK ADD  CONSTRAINT [FK9w5191ls9uen5i50b8d3f7dhr] FOREIGN KEY([brand_id])
REFERENCES [dbo].[brand] ([id])
GO
ALTER TABLE [dbo].[Shoe] CHECK CONSTRAINT [FK9w5191ls9uen5i50b8d3f7dhr]
GO
ALTER TABLE [dbo].[Shoe]  WITH CHECK ADD  CONSTRAINT [FKcndb5u9pt6iax3ryas6wr5jpc] FOREIGN KEY([sole_id])
REFERENCES [dbo].[Sole] ([id])
GO
ALTER TABLE [dbo].[Shoe] CHECK CONSTRAINT [FKcndb5u9pt6iax3ryas6wr5jpc]
GO
ALTER TABLE [dbo].[Shoe]  WITH CHECK ADD  CONSTRAINT [FKnh6cc1r0oe5q5023wcwcwft1m] FOREIGN KEY([category_id])
REFERENCES [dbo].[Category] ([id])
GO
ALTER TABLE [dbo].[Shoe] CHECK CONSTRAINT [FKnh6cc1r0oe5q5023wcwcwft1m]
GO
ALTER TABLE [dbo].[Shoe]  WITH CHECK ADD  CONSTRAINT [FKsofh1elk4ari6wcyk9fkbmcgw] FOREIGN KEY([material_id])
REFERENCES [dbo].[material] ([id])
GO
ALTER TABLE [dbo].[Shoe] CHECK CONSTRAINT [FKsofh1elk4ari6wcyk9fkbmcgw]
GO
ALTER TABLE [dbo].[shoe_detail]  WITH CHECK ADD  CONSTRAINT [FKgvjdwkecl2rostmt1qth6psay] FOREIGN KEY([shoe_id])
REFERENCES [dbo].[Shoe] ([id])
GO
ALTER TABLE [dbo].[shoe_detail] CHECK CONSTRAINT [FKgvjdwkecl2rostmt1qth6psay]
GO
ALTER TABLE [dbo].[shoe_detail]  WITH CHECK ADD  CONSTRAINT [FKki5uka31ftom934pvl18q7yi1] FOREIGN KEY([color_id])
REFERENCES [dbo].[color] ([id])
GO
ALTER TABLE [dbo].[shoe_detail] CHECK CONSTRAINT [FKki5uka31ftom934pvl18q7yi1]
GO
ALTER TABLE [dbo].[shoe_detail]  WITH CHECK ADD  CONSTRAINT [FKmgd3iiafdcjqh75w7hud6e83r] FOREIGN KEY([size_id])
REFERENCES [dbo].[Size] ([id])
GO
ALTER TABLE [dbo].[shoe_detail] CHECK CONSTRAINT [FKmgd3iiafdcjqh75w7hud6e83r]
GO
USE [master]
GO
ALTER DATABASE [db_datn] SET  READ_WRITE 
GO
