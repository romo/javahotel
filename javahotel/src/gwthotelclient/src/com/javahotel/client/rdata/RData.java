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
package com.javahotel.client.rdata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gwtmodel.table.view.callback.CommonCallBack;
import com.javahotel.client.GWTGetService;
import com.javahotel.client.PersonHotelRoles;
import com.javahotel.client.rdata.CacheData.RetData;
import com.javahotel.common.command.CommandParam;
import com.javahotel.common.command.DictType;
import com.javahotel.common.command.ISignal;
import com.javahotel.common.command.RType;
import com.javahotel.common.rescache.ReadResParam;
import com.javahotel.common.rescache.ResObjectCache;
import com.javahotel.common.rescache.ResObjectCache.IReadResCallBack;
import com.javahotel.common.rescache.ResObjectCache.IReadResData;
import com.javahotel.common.toobject.AbstractTo;
import com.javahotel.common.toobject.ResDayObjectStateP;
import com.javahotel.types.LId;

/**
 * 
 * @author stanislawbartkowski@gmail.com
 */
public class RData {

    private ResObjectCache resCache;
    private final CacheData ca = new CacheData();

    private PersonHotelRoles pRoles;

    /**
     * @return the pRoles
     */
    public PersonHotelRoles getpRoles() {
        return pRoles;
    }

    /**
     * @param pRoles
     *            the pRoles to set
     */
    public void setpRoles(PersonHotelRoles pRoles) {
        this.pRoles = pRoles;
        resCache = new ResObjectCache(new ReadResInfoData());
        ca.clear();
    }

    public CommandParam getHotelCommandParam() {
        CommandParam p = new CommandParam();
        p.setHotel(pRoles.getHotel());
        return p;
    }

    public CommandParam getHotelDictId(final DictType d, final LId id) {
        CommandParam p = getHotelCommandParam();
        p.setDict(d);
        p.setRecId(id);
        return p;
    }

    public CommandParam getHotelDictName(final DictType d, final String name) {
        CommandParam p = getHotelCommandParam();
        p.setDict(d);
        p.setRecName(name);
        return p;
    }

    private class ReadResInfoData implements IReadResData {

        private class setRes extends CommonCallBack<List<ResDayObjectStateP>> {

            private final IReadResCallBack ca;

            setRes(final IReadResCallBack ca) {
                this.ca = ca;
            }

            @Override
            public void onMySuccess(List<ResDayObjectStateP> a) {
                ca.setCol(a);
            }
        }

        @Override
        public void getResData(final ReadResParam pa, final IReadResCallBack col) {
            CommandParam co = getHotelCommandParam();
            co.setDateFrom(pa.getPe().getFrom());
            co.setDateTo(pa.getPe().getTo());
            int no = 0;
            for (final String s : pa.getResList()) {
                co.setResListNo(no, s);
                no++;
            }
            GWTGetService.getService().getList(RType.ResObjectState, co,
                    new setRes(col));
        }
    }

    public interface IVectorList {

        void doVList(final List<? extends AbstractTo> val);
    }

    public interface IOneList<T> {

        void doOne(final T val);
    }

    private class CallList extends CommonCallBack<List<AbstractTo>> {

        private final RetData re;
        private final IVectorList i;

        CallList(final RetData re, final IVectorList i) {
            this.re = re;
            this.i = i;
        }

        @Override
        public void onMySuccess(List<AbstractTo> v) {
            re.col = v;
            ca.putData(re);
            i.doVList(v);
        }
    }

    private class CallOne extends CommonCallBack<AbstractTo> {

        private final IOneList i;
        private final RetData re;

        CallOne(final RetData re, final IOneList i) {
            this.re = re;
            this.i = i;
        }

        @Override
        public void onMySuccess(AbstractTo a) {
            List<AbstractTo> v = new ArrayList<AbstractTo>();
            v.add(a);
            re.col = v;
            ca.putData(re);
            i.doOne(a);
        }
    }

    public void getList(final RType r, final CommandParam p, final IVectorList i) {
        RetData re = ca.getCol(r, p);
        if (re.col != null) {
            i.doVList(re.col);
            return;
        }
        GWTGetService.getService().getList(r, p, new CallList(re, i));
    }

    public void getOne(final RType r, final CommandParam p, final IOneList i) {
        RetData re = ca.getCol(r, p);
        if (re.col != null) {
            AbstractTo aa = null;
            for (final AbstractTo a : re.col) {
                aa = a;
                break;
            }
            i.doOne(aa);
            return;
        }
        GWTGetService.getService().getOne(r, p, new CallOne(re, i));
    }

    public void invalidateCacheList() {
        invalidateCache(RType.ListDict);
    }

    public void invalidateCache(final RType... rt) {
        for (RType r : rt) {
            ca.invalidateCache(r);
        }
    }

    public void readResObjectState(final ReadResParam rp, final ISignal i) {
        resCache.ReadResState(rp, i);
    }

    public ResDayObjectStateP getResState(final String resObject, final Date d) {
        return resCache.getResState(resObject, d);
    }

    public void invalidateResCache() {
        resCache.invalidate();
    }

}