/*
 * Copyright (C) 2020, Bost.com. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package com.bost.futures;

import com.alibaba.fastjson.JSONObject;
import com.bost.futures.model.Order;
import com.bost.util.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class OrderSubscription extends PrivateSubscription<List<Order>> {

    private final Map<String, Order> items = new HashMap<>();

    OrderSubscription(BostFuturesUSDTClient client, Listener<List<Order>> listener) {
        super(client, listener);
    }

    @Override
    String getDataName() {
        return "contract_pending";
    }

    @Override
    public List<Order> doDecode(JSONObject json) {
        ArrayList<Order> a = new ArrayList<>();
        a.add(JSONUtils.parseOrderEvent(json));
        return a;
    }

    @Override
    void onData(List<Order> data) {
        data.forEach(item -> items.put(item.getOrderId(), item));
    }

    @Override
    List<Order> getData() {
        List<Order> a = new ArrayList<>(items.values());
        items.clear();
        return a;
    }

}
