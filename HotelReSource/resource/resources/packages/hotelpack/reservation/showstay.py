from com.jythonui.server.holder import SHolder

import cutil,con,pdfutil

from util import rutil,util,rpdf,cust

from rrutil import rbefore,reseparam,cbill

ADDBLOB=SHolder.getAddBlob()

BILLIST="billlist"
CUST="cust_"
	        

def _listOfPayments(var) :
  rese = rutil.getReseName(var)
  li = util.RESOP(var).getResAddPaymentList(rese)
  seq = []
  sum = util.SUMBDECIMAL()  
  CU = util.CUSTOMERLIST(var)
  for e in li :
    g = e.getGuestName()    
    customer = CU.findElem(g)
    assert customer != None
    map = { "roomid" : e.getRoomName(), "total" : e.getPriceTotal(), "price" : e.getPrice(), "servdescr" : e.getDescription(), "quantity" : e.getQuantity() }
    cust.toCustomerVar(map,customer,"guest_")
    seq.append(map)
    sum.add(e.getPriceTotal())
  cutil.setJMapList(var,"paymentlist",seq)
  cutil.setFooter(var,"paymentlist","total",sum.sum)

def _ListOfBills(var) :
   rese = rutil.getReseName(var)
   assert rese != None
   bList = util.RESOP(var).findBillsForReservation(rese)
   seq = []
   pli = rutil.getPayments(var)
   sumtotal = 0.0
   footerpayments = 0.0
   sumsell = 0.0
   CU = util.CUSTOMERLIST(var)
   for b in bList :
     C = cbill.BILLCALC(var,b)
     C.calc()
     bName = b.getName()
     assert bName != None
     payer = b.getPayer()
     customer = CU.findElem(payer)
     assert customer != None
     da = b.getIssueDate()     
     tot = C.getTotal()
     sell = C.getCharge()
     paysum = C.getPayment()
     sumtotal = cutil.addDecimal(sumtotal,tot)
     footerpayments = cutil.addDecimal(footerpayments,paysum)
     sumsell = cutil.addDecimal(sumsell,sell)
     ma = { "billname" : bName, "billtotal" : tot, "payments" : paysum, "sell" : sell }
     cust.toCustomerVar(ma,customer,"payer_")
     seq.append(ma)
   cutil.setJMapList(var,BILLIST,seq)
   cutil.setFooter(var,BILLIST,"billtotal",sumtotal) 
   cutil.setFooter(var,BILLIST,"payments",footerpayments) 
   cutil.setFooter(var,BILLIST,"sell",sumsell)    
  
def _setChangedFalse(var) :
   var["billlistwaschanged"] = False
   cutil.setCopy(var,"billlistwaschanged")  
  
def showstay(action,var):
   cutil.printVar("show stay",action,var)
   
   if action == "before" :
     rbefore.setvarBefore(var)
     # after 
     _listOfPayments(var)
     _ListOfBills(var)
     _setChangedFalse(var)
     PA = reseparam.RESPARAM(rutil.getReseName(var))
     PA.setParam(var)
     PA.copyParam(var)

   
   if var["billlistwaschanged"] :
    _setChangedFalse(var)   
    _ListOfBills(var)
     
   if action == "crud_readlist" and var["JLIST_NAME"] == BILLIST :
     _ListOfBills(var)     
     
   if action == "afterbill" and var["JUPDIALOG_BUTTON"] == "acceptdocument" :
     _ListOfBills(var)
     
   if action == "payerdetail" :
      cust.showCustomerDetails(var,var["payer_name"])
     
   if action == "changetoreserv" and var["JYESANSWER"] :
     res = getReservForDay(var)
     resname = res[0].getResId()
     R = util.RESOP(var)
     R.changeStatusToReserv(resname)
     a = cutil.createArrayList()
     R.setResGuestList(resname,a)
     var["JCLOSE_DIALOG"] = True
     var["JREFRESH_DATELINE_reservation"] = ""
     
   if action == "addpayment" :
      var["JUP_DIALOG"]="?addpayment.xml" 
      var["JAFTERDIALOG_ACTION"] = "afteraddpayment" 
      
   if action == "paymentslist" :
      assert var["billname"] != None
      var["JUP_DIALOG"]="?listofpayment.xml" 
      var["JAFTERDIALOG_ACTION"] = "afterlistpayments"
      var["JUPDIALOG_START"] = var["billname"]
      
   if action == "printbill" and var[BILLIST + "_lineset"] :
      var["JUP_DIALOG"]="?billprint.xml" 
      var["JUPDIALOG_START"] = var["billname"]
      
   if action == "afteraddpayment" and var["JUPDIALOG_BUTTON"] == "addpayment" :
     _listOfPayments(var)
        
   if action == "guestdesc" :
       var["JAFTERDIALOG_ACTION"] = "acceptdetails" 
       cust.showCustomerDetailstoActive(var,var[CUST+"name"])
       
   if action == "acceptdetails" and var["JUPDIALOG_BUTTON"] == "acceptask" :
        xml = var["JUPDIALOG_RES"]
        util.xmlToVar(var,xml,cust.getCustFieldIdAll(),CUST)
        cutil.setCopy(var,cust.getCustFieldIdAll(),None,CUST)
        name = var[CUST+"name"]
        resename = rutil.getReseName(var)          
        util.RESFORM(var).changeCustName(resename,name)
              
   if action == "guestdetail" :
       cust.showCustomerDetails(var,var["guest_name"])
       
   if action == "listpdf" and var[BILLIST + "_lineset"] :
      var["JUP_DIALOG"]="?pdflist.xml" 
      var["JUPDIALOG_START"] = var["billname"]
   