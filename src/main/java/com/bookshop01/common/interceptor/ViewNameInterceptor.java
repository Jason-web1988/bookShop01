package com.bookshop01.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bookshop01.common.log.LoggingAdvice;

public class ViewNameInterceptor extends  HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);
	
	   @Override
	   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		   logger.info("=============preHandle================");
		   try {
			String viewName = getViewName(request);
			request.setAttribute("viewName", viewName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		   return true;
	   }

	   @Override
	   public void postHandle(HttpServletRequest request, HttpServletResponse response,
	                           Object handler, ModelAndView modelAndView) throws Exception {
		   logger.info("=============postHandle================");
	   }

	   @Override
	   public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
	                                    Object handler, Exception ex)    throws  Exception {
		   logger.info("=============afterCompletion================");
	   }
	   
	   private String getViewName(HttpServletRequest request) throws Exception {
			String contextPath = request.getContextPath();
			String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
			if (uri == null || uri.trim().equals("")) {
				uri = request.getRequestURI();
			}

			int begin = 0;
			if (!((contextPath == null) || ("".equals(contextPath)))) {
				begin = contextPath.length();
			}

			int end;
			if (uri.indexOf(";") != -1) {
				end = uri.indexOf(";");
			} else if (uri.indexOf("?") != -1) {
				end = uri.indexOf("?");
			} else {
				end = uri.length();
			}

			String fileName = uri.substring(begin, end);
			if (fileName.indexOf(".") != -1) {
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
			}
			if (fileName.lastIndexOf("/") != -1) {
				fileName = fileName.substring(fileName.lastIndexOf("/",1), fileName.length());
			}
			return fileName;
		}
	}
