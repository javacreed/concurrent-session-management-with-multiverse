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

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShopServlet extends HttpServlet {

  /** */
  private static final long serialVersionUID = -7974891936453514360L;

  @Override
  protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException {

    final ShoppingCart shoppingCart = SessionUtils.getShoppingCart(request);

    final String action = request.getParameter("action");
    if (action == null) {
      response.setContentType("text/html");
      try (PrintWriter out = response.getWriter()) {
        out.println("<html><body><h1>Shopping Cart</h1>");
        /* Multiverse default list implementation does not support iterator */
        shoppingCart.printItems(out);
        out.println("<a href='?action=add'>Add</a>");
        out.println("</body></html>");
      }
      return;
    }

    switch (action) {
    case "add":
      shoppingCart.add();
      break;
    }

    response.sendRedirect("/");
  }
}
