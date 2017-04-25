        <% if(debugJSP != null && debugJSP.equalsIgnoreCase("true")) {
              out.println("<h4>Debug Request Parameters</h4>");
              out.println("<ol>");
              int index=0;
              String parameterString=null;
              Map<String,String[]>parameterMap=request.getParameterMap();
              for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) 
              {
                  out.println("<li>" + entry.getKey() + "=" + entry.getValue()[0] + "</li>");
                  if(index==0)
                     parameterString=("?"+entry.getKey()+"="+entry.getValue()[0]);
                  else
                     parameterString=(parameterString+"&"+entry.getKey()+"="+entry.getValue()[0]);
                  index++;
              }//for//
              out.println("</ol>");
              out.println("<h5>Parameter String = "+parameterString+"</h5>");

              out.println("<h4>Debug Request Attributes</h4>");
              out.println("<ol>");
              Enumeration e1=request.getAttributeNames();
              while(e1.hasMoreElements()) {
                 out.println("<li>" + e1.nextElement() + "</li>");
              }
              out.println("</ol>");
              
              out.println("<h4>Debug Page Attributes</h4>");
              out.println("<ol>");
              Enumeration e3=pageContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
              while(e3.hasMoreElements()) {
                 out.println("<li>" + e3.nextElement() + "</li>");
              }
              out.println("</ol>");
              out.println("<h4>Debug Session Attributes</h4>");
              out.println("<ol>");
              Enumeration e2=session.getAttributeNames();
              while(e2.hasMoreElements()) {
                 out.println("<li>" + e2.nextElement() + "</li>");
              }
              out.println("</ol>");
           }//if//
        %>        
