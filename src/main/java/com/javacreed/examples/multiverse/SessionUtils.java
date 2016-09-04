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

import javax.servlet.http.HttpServletRequest;

public class SessionUtils {

  public static synchronized ShoppingCart getShoppingCart(final HttpServletRequest request) throws ClassCastException {
    ShoppingCart shoppinCart = (ShoppingCart) request.getSession(true).getAttribute("shoppingCart");
    if (shoppinCart == null) {
      request.getSession().setAttribute("shoppingCart", shoppinCart = new ShoppingCart());
    }

    return shoppinCart;
  }

  private SessionUtils() {}
}
