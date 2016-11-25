-- SELECT GETDATE()

SELECT DATEADD(dd,10,'2015-01-10')

SELECT DBO.DATEADD_DD(30,'2015-01-10')

SELECT DBO.DATEADD_MM(30,'2015-01-10 12:10:01')

SELECT DBO.DATEADD_MI(30,'2015-01-12 12:20:10')

SELECT DBO.DATEDIFF_DD('2015-02-21','2015-01-10')

SELECT DBO.DATEDIFF_MM('2015-01-21','2015-05-10')

SELECT DBO.DATEDIFF_MI('2015-01-10 12:04:01','2015-01-10 12:14:01')

SELECT DBO.DATEPART_HH('2015-01-10 12:04:01'),DBO.DATEPART_SS('2015-01-10 12:04:01'),DBO.DATEPART_MI('2015-01-10 12:04:01')

SELECT ISNULL(NULL,'A'),ISNULL('XX','YY')

SELECT SUBSTRING('ABCDEF',2,2)

SELECT REPLICATE('A',5)

SELECT STUFF('ABCDE',2,1,'123')

SELECT CHARINDEX('23','123456789'),CHARINDEX('XX','123456789')

SELECT LEN('12345')