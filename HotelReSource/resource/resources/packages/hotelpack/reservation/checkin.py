from cutil import printVar
from cutil import setStandEditMode
from util.util import RESFORM
from util.util import ROOMLIST
from util.util import SERVICES
from util.util import RESOP
from util.util import CUSTOMERLIST
from util.util import getCustFieldId
from util.util import isResOpen


#   for r in reservation.getResDetail() :
#         map = { "name" : r.getRoom(), "resday" : r.getResDate(), "price" : r.getPrice(), "service" : r.getService() }
#         list.append(map)
#         sum.add(r.getPrice())

CUSTF = getCustFieldId()
CHECKINLIST= "checkinlist"

def __toMap(map,var,custid,CUST) :
    cust = CUST.findElem(custid)
    map["guestselect"] = cust.getName()
    map["guestname"] = cust.getName()
    map["guestdescr"] = cust.getDescription()
    for n in CUSTF :
      map[n] = cust.getAttr(n)


def checkinaction(action,var):
    printVar("checkinaction",action,var)
    R = RESFORM(var)
    ROOM = ROOMLIST(var)
    SE = SERVICES(var)
    ROP = RESOP(var)
    resName = var["resename"]
    CUST = CUSTOMERLIST(var)
    
    if action == "selectguest" :
        var["JUP_DIALOG"] = "hotel/reservation/customerselection.xml"
    
    if action == "before" :
        roomRes = {}
        reservation = R.findElem(resName)
        for r in reservation.getResDetail() :
            # list of guests
            gList = ROP.getResGuestList(resName)
            
            rname = r.getRoom()
            room = ROOM.findElem(rname)
            servicename = r.getService()
            serv = SE.findElem(servicename)
            nop = serv.getNoPersons()
            if roomRes.has_key(rname) :
                no = roomRes[rname][0]
                if nop > no : roomRes[rname][0] = nop
            else :
                roomRes[rname] = (nop,room) 
     
            list = []
            wasGuest = False
            for roomname in roomRes.keys() :
                for i in range(roomRes[roomname][0]) :
                    map = { "roomid" : roomname, "roomdesc" : roomRes[roomname][1].getDescription()}
                    # add guest details
                    # getGuestName()
                    # getRoomName()
                    no = -1
                    found = False
                    for g in gList :
                        if g.getRoomName() == roomname :
                            no = no + 1
                            if no == i :
                                found = True
                                custid = g.getGuestName()
                                __toMap(map,var,custid,CUST)
                                cust = CUST.findElem(custid)
                                found = True
                                wasGuest = True
                                break
                    if not found :
                        map["guestselect"] = "<select>"        
                    list.append(map)
            var["JLIST_MAP"] = { CHECKINLIST : list}
            setStandEditMode(var,CHECKINLIST,["guestdescr","firstname"])
            resform = R.findElem(resName)
            if isResOpen(resform) and not wasGuest :
                custid = resform.getCustomerName()
                map = list[0]
                __toMap(map,var,custid,CUST)                
             
    # resename
    