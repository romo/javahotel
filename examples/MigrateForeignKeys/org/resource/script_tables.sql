USE [TEST1]
GO
/****** Object:  Table [dbo].[departments]    Script Date: 2014-12-28 21:36:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[departments](
	[dept_no] [char](4) NOT NULL,
	[dept_name] [varchar](40) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[dept_no] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[dept_emp]    Script Date: 2014-12-28 21:36:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[dept_emp](
	[emp_no] [int] NOT NULL,
	[dept_no] [char](4) NOT NULL,
	[from_date] [date] NOT NULL,
	[to_date] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[emp_no] ASC,
	[dept_no] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[dept_manager]    Script Date: 2014-12-28 21:36:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[dept_manager](
	[dept_no] [char](4) NOT NULL,
	[emp_no] [int] NOT NULL,
	[from_date] [date] NOT NULL,
	[to_date] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[emp_no] ASC,
	[dept_no] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[employees]    Script Date: 2014-12-28 21:36:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[employees](
	[emp_no] [int] NOT NULL,
	[birth_date] [date] NOT NULL,
	[first_name] [varchar](14) NOT NULL,
	[last_name] [varchar](16) NOT NULL,
	[gender] [char](1) NOT NULL,
	[hire_date] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[emp_no] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[salaries]    Script Date: 2014-12-28 21:36:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[salaries](
	[emp_no] [int] NOT NULL,
	[salary] [int] NOT NULL,
	[from_date] [date] NOT NULL,
	[to_date] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[emp_no] ASC,
	[from_date] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[titles]    Script Date: 2014-12-28 21:36:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[titles](
	[emp_no] [int] NOT NULL,
	[title] [varchar](50) NOT NULL,
	[from_date] [date] NOT NULL,
	[to_date] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[emp_no] ASC,
	[title] ASC,
	[from_date] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[dept_emp]  WITH CHECK ADD FOREIGN KEY([dept_no])
REFERENCES [dbo].[departments] ([dept_no])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[dept_emp]  WITH CHECK ADD FOREIGN KEY([emp_no])
REFERENCES [dbo].[employees] ([emp_no])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[dept_manager]  WITH CHECK ADD FOREIGN KEY([dept_no])
REFERENCES [dbo].[departments] ([dept_no])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[dept_manager]  WITH CHECK ADD FOREIGN KEY([emp_no])
REFERENCES [dbo].[employees] ([emp_no])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[salaries]  WITH CHECK ADD FOREIGN KEY([emp_no])
REFERENCES [dbo].[employees] ([emp_no])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[titles]  WITH CHECK ADD FOREIGN KEY([emp_no])
REFERENCES [dbo].[employees] ([emp_no])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[employees]  WITH CHECK ADD CHECK  (([gender]='F' OR [gender]='M'))
GO
