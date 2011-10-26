/*
 * Copyright 2011 stanislawbartkowski@gmail.com 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.javahotel.db.commands;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.javahotel.common.command.BookingEnumTypes;
import com.javahotel.common.command.BookingStateType;
import com.javahotel.common.command.PaymentMethod;
import com.javahotel.common.toobject.BookingP;
import com.javahotel.common.toobject.BookingStateP;
import com.javahotel.common.util.GetMaxUtil;
import com.javahotel.common.util.StringU;
import com.javahotel.db.copy.BeanPrepareKeys;
import com.javahotel.db.copy.CommonCopyBean;
import com.javahotel.db.copy.CopyHelper;
import com.javahotel.db.hotelbase.jpa.Booking;
import com.javahotel.db.hotelbase.jpa.BookingState;
import com.javahotel.db.hotelbase.jpa.Payment;
import com.javahotel.remoteinterfaces.HotelT;
import com.javahotel.remoteinterfaces.SessionT;

/**
 * Changes Booking to Stay by copying the content and assigning new id
 * @author stanislawbartkowski@gmail.com
 */
public class ChangeBookingToStay extends CommandAbstract {

    private final String resName;
    private Booking resO;

    /**
     * Constructor
     * @param se Session
     * @param ho Hotel
     * @param resName Symbol of reservation to be transformed to stay
     */
    public ChangeBookingToStay(final SessionT se, final String ho,
            final String resName) {
        // do not start transaction automatically (GAE specific)
        super(se, false, new HotelT(ho));
        this.resName = resName;
    }

    private void modifBooking(final Booking p) {
        BookingState sta = new BookingState();
        CopyHelper.checkPersonDateOp(iC, sta);
        sta.setBState(BookingStateType.ChangedToCheckin);
        sta.setBooking(p);
        GetMaxUtil.setNextLp(p.getState(), sta);
        p.getState().add(sta);

        BigDecimal sum = HotelHelper.sumPayment(p.getPayments());
        if (StringU.eqZero(sum)) {
            return;
        }
        Payment pa = new Payment();
        CopyHelper.checkPersonDateOp(iC, pa);
        pa.setAmount(sum);
        pa.setPayMethod(PaymentMethod.Cache);
        pa.setDatePayment(pa.getDateOp());
        GetMaxUtil.setNextLp(p.getPayments(), pa);
        p.getPayments().add(pa);
    }

    private void changeToStay(final BookingP bp) {
        // change header set resName
        bp.setResName(resName);
        bp.setName(null);
        bp.equals(null);
        bp.setBookingType(BookingEnumTypes.Stay);

        List<BookingStateP> col = new ArrayList<BookingStateP>();
        BookingStateP bs = new BookingStateP();
        bs.setBState(BookingStateType.Stay);
        GetMaxUtil.setFirstLp(bs);
        col.add(bs);
        bp.setState(col);

//        BookRecordP rec = GetMaxUtil.getLastBookRecord(bp);
//        if (rec != null) {
//            GetMaxUtil.setFirstLp(rec);
//            rec.setId(null);
//            rec.setSeqId(null); // next seq id should be assigned
//            for (BookElemP el : rec.getBooklist()) {
//                el.setId(null);
//                for (PaymentRowP ro : el.getPaymentrows()) {
//                    ro.setId(null);
//                }
//            }
//            List<BookRecordP> bCol = BillUtil.getEmptyCol(rec);
//            bp.setBookrecords(bCol);
//        }
    }

    /**
     * Run command
     */
    @Override
    protected void command() {
        Booking b = getBook(resName);
        if (b.getBookingType() == BookingEnumTypes.Stay) {
            String s = resName + " to jest już pobyt !";
            ret.setErrorMessage(s);
            return;
        }
        BookingState sta = GetMaxUtil.getLast(b.getState());
        if (sta.getBState() == BookingStateType.ChangedToCheckin) {
            String s = resName + " juz zamieniony na pobyt !";
            ret.setErrorMessage(s);
            return;
        }
        BookingP bp = new BookingP();
        CommonCopyBean.copyB(iC, b, bp);
        changeToStay(bp);
        BeanPrepareKeys.prepareKeys(iC, bp);
        resO = new Booking();
        CommonCopyBean.copyB(iC, bp, resO);
        startTra();
        modifBooking(b);
        iC.getJpa().changeRecord(b);
        iC.getJpa().addRecord(resO);
    }


    @Override
    protected void aftercommit() {
        getRet(ret, resO);
    }
}
