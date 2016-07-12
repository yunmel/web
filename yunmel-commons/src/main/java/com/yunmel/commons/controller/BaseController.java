package com.yunmel.commons.controller;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yunmel.commons.exception.BaseException;
import com.yunmel.commons.model.Result;
import com.yunmel.commons.utils.IOUtils;
import com.yunmel.commons.utils.RandomUtils;

/**
 * 基础控制器 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能
 * 
 * @description
 * @author xuyq
 * @date 20160617
 */
public abstract class BaseController {
  private final static Logger LOG = LoggerFactory.getLogger(BaseController.class);

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
    binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) {
        setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
      }

      @Override
      public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : "";
      }
    });
    // Date 类型转换
    binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) {
        try {
          setValue(DateUtils.parseDate(text, "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }

    });
    // Timestamp 类型转换
    binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) {
        Date date = null;
        try {
          date = DateUtils.parseDate(text, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
          e.printStackTrace();
        }
        setValue(date == null ? null : new Timestamp(date.getTime()));
      }
    });

  }

  /**
   * 成功请求
   * 
   * @param data
   * @return
   */
  protected ResponseEntity<Result> ok(Object data) {
    return ResponseEntity.ok(Result.build(0, "请求成功", data));
  }

  /**
   * 错误请求
   * 
   * @param e
   * @return
   */
  protected ResponseEntity<Result> error(BaseException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Result.build(e.getCode(), e.getMessage()));
  }

  /**
   * 错误请求
   * 
   * @param e
   * @return
   */
  protected ResponseEntity<Result> refuse(Result result) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
  }

  /**
   * 未授权
   * 
   * @param e
   * @return
   */
  protected ResponseEntity<Result> unauthorized(Result result) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
  }

  protected void uploadFile(HttpServletRequest request, String field, File file) {
    try {
      if (request instanceof MultipartHttpServletRequest) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
        List<MultipartFile> files = map.get(field);
        MultipartFile _file = files.get(0);
        IOUtils.copyInputStreamToFile(_file.getInputStream(), file);
      }
    } catch (IOException e) {
      LOG.error("upload file error.", e);
    }
  }

  protected File uploadFile(HttpServletRequest request, String field) {
    try {
      if (request instanceof MultipartHttpServletRequest) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
        List<MultipartFile> files = map.get(field);
        MultipartFile _file = files.get(0);
        File file = new File(RandomUtils.genRandom32Hex());
        IOUtils.copyInputStreamToFile(_file.getInputStream(), file);
        return file;
      }
    } catch (IOException e) {
      LOG.error("upload file error.", e);
    }

    return null;
  }

  protected MultipartFile getMultipartFile(HttpServletRequest request, String field) {
    try {
      if (request instanceof MultipartHttpServletRequest) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
        List<MultipartFile> files = map.get(field);
        return files.get(0);
      }
    } catch (Exception e) {
      LOG.error("upload file error.", e);
    }

    return null;
  }

  protected List<MultipartFile> getMultipartFiles(HttpServletRequest request, String field) {
    try {
      if (request instanceof MultipartHttpServletRequest) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
        return map.get(field);
      }
    } catch (Exception e) {
      LOG.error("upload file error.", e);
    }

    return null;
  }
}
