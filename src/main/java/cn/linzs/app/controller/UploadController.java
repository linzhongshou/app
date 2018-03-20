package cn.linzs.app.controller;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.common.exception.FileNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author linzs
 * @Date 2018-03-16 17:04
 * @Description
 */
@Controller
public class UploadController {

    private final static int FILE_BUFF_SIZE = 512; // 下载时循环一次读取文件的大小

    @Value("${file.upload.base.dir}")
    private String baseDir; // 上传文件的根目录
    @Value("${file.upload.prefix}")
    private String filePrefix; // 文件URL的前缀

    @RequestMapping(value = "/api/upload", method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult upload(HttpServletRequest request) {
        ReturnResult result = null;
        List<ReturnResult> uploadList = new ArrayList<>();
        boolean success = false;
        // Path：从客户端指定保存文件的目录
        String path = request.getParameter("path");
        path = StringUtils.isEmpty(path) ? "default": removeFirstLastSymbol(path);

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        for(MultipartFile multipartFile : files) {
            String originalFileName = multipartFile.getOriginalFilename();
            try {
                String fileName;
                if(originalFileName.indexOf(".") > -1) {
                    fileName = System.currentTimeMillis() + originalFileName.substring(originalFileName.lastIndexOf("."));
                } else {
                    fileName = originalFileName;
                }

                File saveDir = new File(baseDir + path);
                if(!saveDir.exists()) {
                    saveDir.mkdirs();
                }
                File file = new File(saveDir, fileName);
                multipartFile.transferTo(file);

                uploadList.add(new ReturnResult(ReturnResult.OperationCode.SUCCESS, filePrefix + path + "/" + fileName));
                success = true;
            } catch (Exception e) {
                uploadList.add(new ReturnResult(ReturnResult.OperationCode.EXCEPTION, "file [" + originalFileName + "] upload failed."));
            }
        }

        if(success) {
            result = new ReturnResult(ReturnResult.OperationCode.SUCCESS, uploadList);
        } else {
            result = new ReturnResult(ReturnResult.OperationCode.ERROR, uploadList);
        }

        return  result;
    }

    private String removeFirstLastSymbol(String value) {
        if(value == null || "".equals(value)) {
            return value;
        }
        
        value = value.startsWith("/") ? value.substring(1) : value;
        value = value.endsWith("/") ? value.substring(0, value.length() - 1) : value;
        return value;
    }

    @RequestMapping(value = "/file/**", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRequestURI();
        if(path == null || "".equals(path)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            throw new FileNotFoundException("File can not be found.");
        } else {
            path = path.replaceFirst("/file", "");
        }

        path = removeFirstLastSymbol(path);
        File file = new File(baseDir + path);
        if(!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            throw new FileNotFoundException("File can not be found.");
        }

        try(FileInputStream fis = new FileInputStream(file);) {
            OutputStream os = response.getOutputStream();
            byte[] buff = new byte[FILE_BUFF_SIZE];
            int len = -1;
            while((len = fis.read(buff)) != -1) {
                os.write(buff);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/file/**", method = RequestMethod.DELETE)
    public ReturnResult delete(HttpServletRequest request, HttpServletResponse response) {
        ReturnResult result = new ReturnResult(ReturnResult.OperationCode.SUCCESS, "File is deleted.");
        String path = request.getRequestURI();
        if(path == null || "".equals(path)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            throw new FileNotFoundException("File can not be found.");
        } else {
            path = path.replaceFirst("/file", "");
        }

        path = removeFirstLastSymbol(path);
        File file = new File(baseDir + path);
        if(file.exists()) {
            file.delete();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            throw new FileNotFoundException("File can not be found.");
        }

        return result;
    }

}
