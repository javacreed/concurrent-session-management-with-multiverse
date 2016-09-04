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

import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.callables.TxnCallable;
import org.multiverse.api.references.TxnLong;

public class Item {

  private final String name;
  private final TxnLong price;

  public Item(final String name, final long price) {
    this.name = name;
    this.price = StmUtils.newTxnLong(price);
  }

  public String getName() {
    return name;
  }

  public TxnLong getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return StmUtils.atomic(new TxnCallable<String>() {
      @Override
      public String call(final Txn txn) throws Exception {
        return Item.this.toString(txn);
      }
    });
  }

  public String toString(final Txn txn) {
    return String.format("%s - Euro %d", name, price.get(txn));
  }
}