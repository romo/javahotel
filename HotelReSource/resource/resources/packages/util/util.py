from java.util import ArrayList
from java.util import Date
from java.util import Calendar
from java.math import BigDecimal

from com.gwthotel.hotel.server.service import H
from com.gwthotel.hotel import HotelObjects
from com.gwthotel.hotel.reservation import ReservationPaymentDetail
from com.gwthotel.hotel.reservation import ResStatus
from com.gwthotel.hotel.stay import ResGuest
from com.gwthotel.hotel import ServiceType
from com.gwthotel.hotel.services import HotelServices
from com.gwthotel.hotel.payment import PaymentBill
from com.gwthotel.hotel.mailing import HotelMailElem
from com.gwthotel.hotel import HUtils
from com.gwthotel.hotel.rooms import HotelRoom
from com.gwthotel.shared import IHotelConsts
from com.gwthotel.hotel.reservationop.IReservationOp import ResInfoType

import cutil,xmlutil,con,sparam,pdfutil

#=============== JOURNAL TYPE
JOURNAL_RESCANCEL="STAYCANCEL"
JOURNAL_CHECKIN="CHECKIN"
JOURNAL_ADDPAYMENT="ADDPAYMENT"
JOURNAL_CHANGELISTOFGUEST="CHANGEGUESTS"
JOURNAL_STATUSCONFIRMED="CONFIRMED"
JOURNAL_STATUSNOTCONFIRMED="NOTCONFIRMED"
JOURNAL_CHANGEADVANCEPAYMENT="ADVANCEPAYMENT"
JOURNAL_SENDCONFMAIL="SENDCONFMAIN"
JOURNAL_ADDBILLPAYMENT="ADDBILLPAYMENT"
JOURNAL_REMOVEBILLPAYMENT="REMOVEBILLPAYMENT"


def setIntField(var,key,setF) :
  if var[key] == None : setF(IHotelConsts.PERSONIDNO)
  else : setF(var[key])
  
def getIntField(val) :
  if val == IHotelConsts.PERSONIDNO : return None
  return val

class HOTELDEFADATA(cutil.DEFAULTDATA) :
  
  def __init__(self) :
    cutil.DEFAULTDATA.__init__(self)
    
  def __getV(self,what) :
    
    if what == 0 : return "defsex"
    elif what == 1 : return "defcountry"
    elif what == 2 : return "defidcard"
    elif what == 3 : return "defpayment"
    elif what == 4 : return "defconfirm"
    elif what == 5 : return "defdoctype"
    
    elif what == 10 : return "lastnopersons"
    elif what == 11 : return "lastnoextrabeds"
    elif what == 12 : return "lastnochildren"
    elif what == 13 : return "lastperperson"
    elif what == 14 : return "lastvatused"
    elif what == 15 : return "lastvatusedextra"
    
    elif what == 20 : return "lastroomservices"
    elif what == 21 : return "lastroomnopersons"
    elif what == 22 : return "lastroomnochildren"
    elif what == 23 : return "lastroomnoextrabeds"
    
    elif what == 30 : return "lastreseroomservice"
    elif what == 31 : return "lastresepricelist"
  
    elif what == 40 : return "advancepaymentpercent"
    elif what == 41 : return "advancepaymentdays"
  
    elif what == 50 : return "journalfilter"
    
  def getDataH(self,what,defa=None) :
    return self.getData(self.__getV(what),defa)
  
  def putDataH(self,what,value) :
    self.putData(self.__getV(what),value)

  def getDataHI(self,what) :
    return self.getDataI(self.__getV(what))
  
  def putDataHI(self,what,v) :
    self.putDataI(self.__getV(what),v)
    
  def getDataHB(self,what) :
    return self.getDataB(self.__getV(what)) 
    
  def putDataHB(self,what,val) :
    self.putDataB(self.__getV(what),val)

class MESS :

  def __init__(self):
      self.M = H.getM()

  def __call__(self,key) :
      return self.M.getMessN(key)
    
def clearHotel(var,hotel) :
   iGet = H.getInstanceObjectId()
   aid = getAppId(var)
   instance = aid.getInstanceName()
   hi = iGet.getOObject(instance, hotel, aid.getPerson());
   H.getClearHotel().clearObjects(hi)
  
class SERVICES(cutil.CRUDLIST) :
    def __init__(self,var):
        cutil.CRUDLIST.__init__(self,var,str(HotelObjects.SERVICE))
        self.serviceS = H.getHotelServices()
        
    def getOtherServices(self):
        return self.getModifiedList(lambda e: e.getServiceType() == ServiceType.OTHER);                    

    def getRoomServices(self):
        return self.getModifiedList(lambda e: e.getServiceType() == ServiceType.HOTEL);
     
class PRICELIST(cutil.CRUDLIST) :

    def __init__(self,var):
        cutil.CRUDLIST.__init__(self,var,str(HotelObjects.PRICELIST))
        self.serviceS = H.getHotelPriceList()
        
class CUSTOMERLIST(cutil.CRUDLIST) :

    def __init__(self,var):
        cutil.CRUDLIST.__init__(self,var,str(HotelObjects.CUSTOMER))
        self.serviceS = H.getHotelCustomers()
        
class BILLLIST(cutil.CRUDLIST) :

    def __init__(self,var):
        cutil.CRUDLIST.__init__(self,var,str(HotelObjects.BILL))
        self.serviceS = H.getCustomerBills()
                
class ROOMLIST(cutil.CRUDLIST):        
    def __init__(self,var):
        cutil.CRUDLIST.__init__(self,var,str(HotelObjects.ROOM))
        self.serviceS = H.getHotelRooms()
  
    def setRoomServices(self,roomName,services):
        self.serviceS.setRoomServices(getHotelName(self.var),roomName,services)
        
    def getRoomServices(self,roomName):
        return self.serviceS.getRoomServices(getHotelName(self.var),roomName)
    
        
class HOTELMAILLIST(cutil.CRUDLIST) :

    def __init__(self,var):
        cutil.CRUDLIST.__init__(self,var)
        self.serviceS = H.getHotelMail()
        
class PRICEELEM :
    
    def __init__(self,var) :
        self.var = var
        self.service = H.getHotelPriceElem() 
    
    def getPricesForPriceList(self,pricelist)  :
        return self.service.getPricesForPriceList(getHotelName(self.var),pricelist)
             
    def savePricesForPriceList(self,pricelist,list) :
        self.service.savePricesForPriceList(getHotelName(self.var),pricelist,list)
        
class PAYMENTOP :        

     def __init__(self,var):
         self.var = var
         self.service = H.getPaymentsOp()
         self.J = cutil.JOURNAL(var)

     def getPaymentsForBill(self,billName) :
         return self.service.getPaymentsForBill(getHotelName(self.var),billName)
       
     def addPaymentForBill(self,billName,p) :
         r = self.service.addPaymentForBill(getHotelName(self.var),billName,p)
         self.J.addJournalElem(JOURNAL_ADDBILLPAYMENT,None,str(r.getId()),billName)
         return r         
         
     def removePaymentForBill(self,billName,id) :
         self.service.removePaymentForBill(getHotelName(self.var),billName,id)
         self.J.addJournalElem(JOURNAL_REMOVEBILLPAYMENT,None,str(id),billName)
         
class RESFORM(cutil.CRUDLIST) :

    def __init__(self,var):
        cutil.CRUDLIST.__init__(self,var,str(HotelObjects.RESERVATION))
        self.serviceS = H.getResForm()
        
    def changeCustName(self,resename,custname) :
        r = self.findElem(resename)
        r.setCustomerName(custname)
        self.changeElem(r)
         
        
class RESOP :
     def __init__(self,var):
         self.var = var
         self.service = H.getResOp()
         self.J = cutil.JOURNAL(var)
         
     def queryReservation(self,query):
         return self.service.queryReservation(getHotelName(self.var),query)
     
     def changeStatus(self,resId,status):
         self.service.changeStatus(getHotelName(self.var),resId,status)
         
     def changeStatusToStay(self,resId):
         self.changeStatus(resId,ResStatus.STAY)
         self.J.addJournalElem(JOURNAL_CHECKIN,None,resId)
         
     def changeStatusToCancel(self,resId):
         self.changeStatus(resId,ResStatus.CANCEL)
         self.J.addJournalElem(JOURNAL_RESCANCEL,None,resId)

     def changeStatusToReserv(self,resId):
         self.changeStatus(resId,ResStatus.OPEN)
         self.J.addJournalElem(JOURNAL_STATUSCONFIRMED,None,resId)
             
     def changeStatusToNotConfirmed(self,resId) :
         self.changeStatus(resId,ResStatus.SCHEDULED)             
         self.J.addJournalElem(JOURNAL_STATUSNOTCONFIRMED,None,resId)
         
     def setResGuestList(self,resId,list):
         self.service.setResGuestList(getHotelName(self.var),resId,list)
         
     def getResGuestList(self,resId):
         return self.service.getResGuestList(getHotelName(self.var),resId)
       
     def addResAddPayment(self,resId,a) :  
         rese = self.service.addResAddPayment(getHotelName(self.var),resId,a)
         self.J.addJournalElem(JOURNAL_ADDPAYMENT,None,resId,str(rese.getId()))
         return rese
         
     def getResAddPaymentList(self,resId) :
         return self.service.getResAddPaymentList(getHotelName(self.var),resId)
       
     def findBillsForReservation(self,resId) :
         return self.service.findBillsForReservation(getHotelName(self.var),resId)
     
     def searchReservation(self,query):
         return self.service.searchReservation(getHotelName(self.var),query)
       
     def getReseForInfoType(self,itype,infoname) :
         return self.service.getReseForInfoType(getHotelName(self.var),itype,infoname)
       
     def getReseForService(self,service) :
         return self.getReseForInfoType(ResInfoType.FORSERVICE,service)

     def getReseForRoom(self,room) :
         return self.getReseForInfoType(ResInfoType.FORROOM,room)
       
     def getReseForPriceList(self,pricelist) :
         return self.getReseForInfoType(ResInfoType.FORPRICELIST,pricelist)
       
     def getReseForCustomer(self,customer) :
         return self.getReseForInfoType(ResInfoType.FORCUSTOMER,customer)

     def getReseForGuest(self,customer) :
         return self.getReseForInfoType(ResInfoType.FORGUEST,customer)

     def getReseForPayer(self,customer) :
         return self.getReseForInfoType(ResInfoType.FORPAYER,customer)
                       
def addRoom(var,roomid,map,capa,descr) :
    RO = ROOMLIST(var).findElem(roomid)
    map[capa] = RO.getNoPersons()
    map[descr] = RO.getDescription()                
                
def isRoomService(service) :
  return service == ServiceType.HOTEL
        
def resStatus(rform):
    """ Check the reservation status
    
    Args: rform  ReservationForm
    
    Returns: 
      1 : stay, 
      2 : reservation open, confirmed ,
      3 : reservation scheduled, not confirmed
      0 : otherwise, canceled
    """
    status = rform.getStatus()
    if status == ResStatus.STAY : return 1
    if status == ResStatus.OPEN : return 2
    if status == ResStatus.SCHEDULED : return 3
    return 0       
  
                  
def printvar(method,action,var): 
    cutil.printVar(method,action,var)
   
def getHotelName(var):
    """ Get com.gwthotel.admin.HotelId class for current session
    
    Args: var
      
    Returns:
      com.gwthotel.admin.HotelId clas
    """
    return cutil.getObject(var)

def getHotel(var=None) :
    """ Get hotel name for current session
    Args: var
    
    Returns: hotel name
    """
    return getHotelName(var).getObject()

def getPerson(var):
    return cutil.getPerson(var)

def getAppId(var):
    return cutil.getAppId(var)

def __toV(s,prefix) :
    return con.toP(s,prefix)

def copyNameDescr(desc,var,prefix=None):
    desc.setName(var[__toV("name",prefix)])
    desc.setDescription(var[__toV("descr",prefix)])
    
def toVarNameDesc(var,sou,prefix=None):
    var[__toV("name",prefix)] = sou.getName()
    var[__toV("descr",prefix)] = sou.getDescription()    
      
def findElemInSeq(pname,seq, getN = None):
    if getN == None : getN = lambda s : s.getName()
    for s in seq : 
       name = getN(s)
       if name == pname : return s
    return None  
          

def toDate(value):
    return cutil.toDate(value)

# d1 : datetime
# d2 : java.util.Date
def eqDate(d1,d2):
    return cutil.eqDate(d1,d2)

def toB(value):
    return cutil.toB(value)
  
def createListOfNames(li) :
  """ Create list of names from PropDescription
  Args:
    li : list of PropDescription objects
  Returns :
    list of extracted names (getName())
  """
  se = []
  for l in li : se.append(l.getName())
  return se


def createSeq(list,addName=False, displayname=None):    
  return cutil.createSeq(list,addName,displayname)

def toVar(var,sou,list,prefix=None):
    for s in list :
        var[__toV(s,prefix)] = sou.getAttr(s)
        
def toP(dest,var,list,prefix=None):
    for s in list :
        dest.setAttr(s,var[__toV(s,prefix)])   
        
def duplicatedName(var,S,duplicateM):    
    seq = S.getList()
    if findElemInSeq(var["name"],seq) != None :
      var["JERROR_name"] = duplicatedM
      return True
    return False

class ConstructObject :
    
    def __init__(self,var):
        self.factory = H.getObjectFactory()
        self.hotel = getHotelName(var)
               
    def getO(self,t) :
        oType = None
        if t == 0 : oType = HotelObjects.CUSTOMER
        if t == 1 : oType = HotelObjects.RESERVATION
        if t == 2 : oType = HotelObjects.BILL
        o = self.factory.construct(self.hotel,oType)
        return o
            
def newResForm(var):
    c = ConstructObject(var)
    return c.getO(1)   
  
def newBill(var) :
    c = ConstructObject(var)
    return c.getO(2)  
  
def newBillPayment() :
   return PaymentBill()

def newResGuest(var):
    return ResGuest()

def newOtherService(var):
    se = HotelServices()
    se.setServiceType(ServiceType.OTHER)
    se.setNoPersons(-1)
    return se

def newHotelRoom() :
    return HotelRoom()

def newHotelService(var):
    return HotelServices()
  
def newResAddPayment() :
    return ReservationPaymentDetail()

def newHotelMailElem():
    return HotelMailElem()
  
def setCopy(var,li) :
  cutil.setCopy(var,li)  
        
def getServicesForRoom(var,room):
  """ Get services and pricelist for room
  Args :
    var
    room : room name
  Returns:
    (servicelist, pricelist)
    servicelist : list of HotelServices objects
    pricelist : list of names
  """
  RO = ROOMLIST(var)
  services = RO.getRoomServices(room)
  if len(services) == 0 : return None
  PR = PRICELIST(var)
  PE = PRICEELEM(var)
  pList = PR.getList()
  li = []
  liList = []
  for p in pList :
      prList = PE.getPricesForPriceList(p.getName())
      for price in prList :
          service = price.getService()
          pr = price.getPrice()
          if pr == None : continue
          for s in services :
              if s.getName() == service : 
                li.append(s) 
                liList.append(p.getName())
  if len(li) == 0 : return None
  f = lambda(e) : e[0] == e[1]
  liList = cutil.removeDuplicates(liList,f)
  ff = lambda(e) : e[0].getName() == e[1].getName()
  li = cutil.removeDuplicates(li,ff)
  return [li,liList]  
  
def createEnumFromList(li, f = lambda elem : [elem.getName(), elem.getDescription()]):
    seq= []
    for elem in li :
        e = f(elem)
        id = e[0]
        name = e[1]
        if cutil.emptyS(name): name = id
        seq.append({"id" : id, "displayname" : name })
    return seq

class SUMBDECIMAL :
    
    def __init__(self):
        self.sum = 0.0
       
    def add(self,b):
        if b : 
          if type(b) == BigDecimal : self.sum = self.sum + b.floatValue()    
          else : self.sum = self.sum + b
                
def duplicateService(var):    
    serv = SERVICES(var)
    seq = serv.getList()
    if findElemInSeq(var["name"],seq) != None :
      M = MESS()
      var["JERROR_name"] = M("DUPLICATEDSERVICENAME")
      return True
    return False

def getVatName(vat):
  list = cutil.getDict("vat")
  vat = findElemInSeq(vat,list)
  if vat == None : return ""
  return vat.getDescription()

def getRoomInfo(var,roomname) :
  R = ROOMLIST(var).findElem(roomname)
  return roomname + cutil.ifnull(R.getDescription(),"")
  

def getPriceForPriceList(var,pricelist,service) :
  PE = PRICEELEM(var)
  peList = PE.getPricesForPriceList(pricelist)
  pPrice = None
  for p in peList :
      if service == p.getService() :
          pPrice = p
          break
  return pPrice   

class HOTELTRANSACTION(cutil.SEMTRANSACTION) :
  
    def __init__(self,semid,var) :
      semname = None
      if semid == 0 : semname = "HOTELBILLSAVE"
      if semid == 1 : semname = "HOTELMAKERESERVATION"
      if semid == 2 : semname = "HOTELCHECKIN"
      cutil.SEMTRANSACTION.__init__(self,semname,var)

# ------------------------------

def xmlToVar(var,xml,list,pre=None) :
  xmlutil.xmlToVar(var,xml,list,pre)
        
def mapToXML(map,list=None,pre=None):
  return xmlutil.mapToXML(map,list,pre) 
  
# -------------------
def listOfRoomsForService(var,servname) :
  """ Return list of rooms (long ids) related to service
  Args:
    var
    servname : service name 
  Returns:
    List of ids (longs)
  """
  R = ROOMLIST(var)
  lRoom = R.getList()
  outR = []
  for r in lRoom :
    sList = R.getRoomServices(r.getName())
    for s in sList :
      if s.getName() == servname :
        outR.append(r.getId())
  return outR        

def listOfPriceListForService(var,servicename) :
  """ Returns list of pricelists (long id) containing this service  
  Args:
    var
    servicename : service name
  Returns:
    List of ids (longs)  
  """
  li = PRICELIST(var).getList()
  PE = PRICEELEM(var)
  outl = []
  for p in li :
    elemlist = PE.getPricesForPriceList(p.getName())
    for elem in elemlist :
      if elem.getService() == servicename :
        outl.append(p.getId())
        break
  return outl
  
# ==============

def getDictOfDocType():
    """ Reads list of document (finance) type
    
    Returns:
      List of map["id","name"] : identifier, document name
    """
    dic = cutil.getDictFromFile("dict","doctype")
    seq = cutil.createEnum(dic,lambda c : c.getName(),lambda c : c.getDescription(), False)
    return seq

# ==============

class HOTELSAVEPARAM(sparam.SAVEPARAM) :
  
  def __init__(self,key,li) :
    sparam.SAVEPARAM.__init__(self,getHotel(),key,li)

def transformXML(xslt,xmlcontent) :
   return pdfutil.xsltHtmlS("packages/hotelpack/reservation/xslt/"+xslt,xmlcontent)
 
def transformXMLFromMap(xslt,m) :
  xml = xmlutil.toXML(m)
  return transformXML(xslt,xml)
