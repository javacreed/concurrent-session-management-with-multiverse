/*
 * #%L
 * Concurrent Session Management with Multiverse
 * %%
 * Copyright (C) 2012 - 2016 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.examples.multiverse;

import java.io.PrintWriter;

import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.callables.TxnVoidCallable;
import org.multiverse.api.collections.TxnList;

public class ShoppingCart {

  /* This is quite limited and does not support all list methods */
  private final TxnList<Item> items = StmUtils.newTxnLinkedList();

  public ShoppingCart() {
    StmUtils.atomic(new TxnVoidCallable() {
      @Override
      public void call(final Txn txn) throws Exception {
        items.add(txn, new Item("a", 10));
        items.add(txn, new Item("b", 20));
      }
    });
  }

  public void add() {
    StmUtils.atomic(new TxnVoidCallable() {
      @Override
      public void call(final Txn txn) throws Exception {
        items.add(txn, new Item("c", 30));
      }
    });
  }

  public void printItems(final PrintWriter out) {
    StmUtils.atomic(new TxnVoidCallable() {
      @Override
      public void call(final Txn txn) throws Exception {
        long total = 0;
        out.println("  <ol>");
        for (int i = 0, size = items.size(); i < size; i++) {
          final Item item = items.get(txn, i);
          total += item.getPrice().get(txn);
          out.printf("    <li>%s (<del><a href='?action=remove&index=%d'>Remove</a></del> ", item.toString(txn), i);
          out.println("Multiverse default list implementation is incomplete and does not support remove)</li>");
        }
        out.println("  </ol>");
        out.printf("  <div>Total: Euro %d</div>%n", total);
      }
    });
  }
}