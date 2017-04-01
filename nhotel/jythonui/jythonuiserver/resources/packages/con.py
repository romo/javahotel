from java.util import Calendar
from java.math import BigDecimal
from java.sql import Timestamp
import java

import datetime

from com.gwtmodel.table.common import DateFormat
from com.gwtmodel.containertype import ContainerInfo
from com.jythonui.server import MUtil

import ctimezone

# ===========================
# date time
# ===========================    

def toJDate(value):
    if value == None : return None
    y = DateFormat.getY(value)
    m = DateFormat.getM(value)
    d = DateFormat.getD(value)
    return datetime.date(y,m,d)

def StoDate(s,timestamp = False):
    if timestamp : return toJDateTime(DateFormat.toD(s,True))
    return toJDate(DateFormat.toD(s,False))

def toDate(value):
    if value == None : return None
    ca = Calendar.getInstance()
    ca.clear()
    ca.set(value.year,value.month - 1,value.day)
    return ca.getTime()

def eqDate(d1,d2):
    dd1 = toDate(d1)
    return dd1.equals(d2)
        
def toJDateTime(value):
    if value == None : return None
    if type(value) == java.sql.Date : raise Exception("toJDateTime cannot be applied to java.sql.Date")        
    y = DateFormat.getY(value)
    m = DateFormat.getM(value)
    d = DateFormat.getD(value)
    # works only for java.util.Data
    # not for java.sql.Date
    hh = value.getHours()
    mm = value.getMinutes()
    ss = value.getSeconds()
    da = datetime.datetime(y,m,d,hh,mm,ss)
    return da.replace(tzinfo=ctimezone.LZONE)

def jTimestamp(da):
#    print da,type(da)
    if type(da) == java.sql.Timestamp : return da
    y = da.year - 1900
    m = da.month - 1
    d = da.day
    ho = da.hour
    mi = da.minute
    se = da.second
    ti = Timestamp(y,m,d,ho,mi,se,0)
#    print "tttt",ti
    return ti

def jDate(y,m,d,ho=None,mi=None,se=None):
    if ho == None : return datetime.date(y,m,d)
    da = datetime.datetime(y,m,d,ho,mi,se)
    return da.replace(tzinfo=ctimezone.LZONE)

def javaDate(y,m,d):
    da = jDate(y,m,d)
    return toDate(da)
    
# ===============================================

def toP(s,prefix):
    if prefix : return prefix + s
    return s

def calculatePercent(total,percent) :
  return total * (percent / 100.0)

def isAppEngine() :
    return not ContainerInfo.TransactionContainer()

def eqUL(l1,l2) :
    return toL(l1) == toL(l2)

def toL(l) :
  if type(l) == long : return l  
  return l.longValue()


def toB(value):
    if value == None : return None
    b = BigDecimal(value)
    return MUtil.roundB(b)

def BigDecimalToDecimal(b):
    if b : return b.doubleValue()
    return None

def today():
    return datetime.date.today()

def maxdate() :
    return datetime.date(2999,12,31);

def mulDecimal(dec1,dec2,afterdot=2):
    return round(dec1 * dec2,afterdot) 

def mulIntDecimal(int,dec,afterdot=2):
    if int and dec :
       if type(dec) == BigDecimal : dec = BigDecimalToDecimal(dec) 
       return round(int * dec, afterdot)
    return None 

def addDecimal(sum1,sum2,afterdot=2):
   if sum1 == None : return sum2
   if sum2 == None : return sum1
   return round(sum1 + sum2,afterdot)

def minusDecimal(sum1,sum2,afterdot=2):
   if sum1 == None : 
       if sum2 == None : return None
       return round(0 - sum2,afterdot)
   if sum2 == None : return sum1
   return round(sum1 - sum2,afterdot)

def fToS(f) :
  return '%.4f' % f
  
def fToSDisp(f,afterdot):
    if f == None: return None
    if afterdot == 0 : return '%d' % f
    if afterdot == 1 : return '%.1f' % f
    if afterdot == 2 : return '%.2f' % f
    if afterdot == 3 : return '%.3f' % f
    return '%.4f' % f      

def toS(val): 
    s  = val
    f = None
    if type(val) == BigDecimal : f = BigDecimalToDecimal(val)
    elif type(val) == datetime.date : s = str(val)
    elif type(val) == int : s = str(val)    
    elif type(val) == float : f = val
    elif type(val) == java.sql.Date : s = str(toJDate(val))
    elif type(val) == java.util.Date : s = str(toJDate(val))
    elif type(val) == java.sql.Timestamp : s = str(toJDate(val))
    elif type(val) == unicode : s = val.encode('utf-8')
    elif type(val) == bool : s = str(val)
    if f != None : s = fToS(f)
    return s

def incDays(date,days = 1) :
  return date+datetime.timedelta(days)
  
def nofDays(dfrom,dto) :
  return (dto-dfrom).days
  
def todayYear() :
  return today().year

def lastDay(year,month) :
  C = Calendar.getInstance()
  C.set(Calendar.YEAR,year)
  C.set(Calendar.MONTH,month-1)
  C.set(Calendar.DATE,1)
  C.add(Calendar.MONTH,1)
  C.add(Calendar.DATE,-1)
  return C.get(Calendar.DATE)
 
def getPeriod(year1,month1,year2,month2) :
   dstart = datetime.date(year1,month1,1)
   dend = datetime.date(year2,month2,lastDay(year2,month2))
   return (dstart,dend)
  
  