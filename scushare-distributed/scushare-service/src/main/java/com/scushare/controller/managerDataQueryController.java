package com.scushare.controller;


import java.io.File;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scushare.service.ManagerService;
import com.scushare.utils.Page;

import scu.pojo.DataFile;

@Controller
public class managerDataQueryController {

	@Autowired
	private ManagerService managerService;
	
	@RequestMapping(value="/MdataQuery")
	public String dataQuery(Model model,
							@RequestParam(value="fileCollege",required=false) String fileCollege,
							@RequestParam(value="fileMajor",required=false) String fileMajor,
							@RequestParam(value="fileName",required=false) String fileName,
							@RequestParam(value="pageIndex",required=false) String pageIndex) throws UnsupportedEncodingException{
		
		
		
		List<DataFile> fileList = null;
		//����ҳ������
		int pageSize = 5;
		//���õ�ǰҳ��
		int currentPageNo = 1;
		if(fileName == null){
			fileName ="";
		}
		//
		if(fileCollege.equals("��ѡ��ѧԺ")){
			fileCollege = null;
		}
		if(fileMajor.equals("��ѡ��רҵ")){
			fileMajor = null;
		}
		//��õ�ǰҳ��
		if(pageIndex != null){
			currentPageNo = Integer.valueOf(pageIndex);
		}
		
		
		//��������
		int totalCount = 0;
		try{
			totalCount = managerService.pageCount(fileCollege,fileMajor,fileName);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("���������ʧ�ܣ�");
		}
		//��ҳ��
		Page pages = new Page();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalPageCount(totalCount);
		pages.setStartPage();
		pages.setEndPage();
		System.out.println("����"+totalCount);
		int totalPageCount =pages.getTotalPageCount();
		int startPage = pages.getStartPage();//��ȡ��ҳ������ʼҳ
		int endPage = pages.getEndPage();//��ȡ��ҳ�������ҳ
		System.out.println("��ҳ��"+totalPageCount+":pageSize:"+pages.getPageSize()+"��ʼҳ"+pages.getStartPage()+"���ҳ"+pages.getEndPage());
		//������ҳ��βҳ
		if(currentPageNo <1){
			currentPageNo = 1;
		}else if(currentPageNo >totalPageCount){
			currentPageNo =totalPageCount;
		}
		if( totalPageCount == 0){

			
			model.addAttribute("totalCount", totalCount);
			return "Manager_DataQuery";
		}
		try{
			fileList = managerService.GetPage(fileCollege, fileMajor, fileName, currentPageNo, pageSize);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("���ϻ�ȡ�쳣");
		}
		
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("currentPageNo", currentPageNo);
		model.addAttribute("startPage",startPage );
		model.addAttribute("endPage", endPage);
		model.addAttribute("totalCount", totalCount);
		if(fileCollege == null){
			model.addAttribute("fileCollege","��ѡ��ѧԺ");
		}else{
		model.addAttribute("fileCollege",fileCollege);
		}
		if(fileMajor == null){
			model.addAttribute("fileMajor","��ѡ��רҵ");
		}else{
		model.addAttribute("fileMajor", fileMajor);
		}
		model.addAttribute("fileName", fileName);
		
		
		return "Manager_DataQuery";
	}
	
	@RequestMapping("/MDeletData")
	@ResponseBody
	public String dataDelet(@Param(value="fileId") String fileId){
		boolean flag = true;
		flag = managerService.datadelet(fileId);
		return "rua";
	}
	
	@RequestMapping("/MDownload")
	@ResponseBody
	public ResponseEntity<byte[]> DownloadData(
								Model model,
								@Param(value="fileId") String fileId,
								@Param(value="fileName") String fileName,
								HttpServletResponse response,
								HttpServletRequest request){
		String fileUrl = managerService.dataDownload(fileId);
		String ReaFileUrl = "E:\\SCUSHARE_DATA"+fileUrl;
		System.out.println("��ʵ·��"+ReaFileUrl);
		
		try{
			// �õ�Ҫ���ص��ļ�
            File file = new File(ReaFileUrl);
            System.out.println(ReaFileUrl);
            //����ļ������ڣ�����ʾ����ʧ��
            if (!file.exists()) {
               model.addAttribute("NoFile", "Sorry!�ļ���ʧ");
                return null;
            } else {
//            	���濪ʼ����HttpHeaders,ʹ���������Ӧ����
                HttpHeaders headers = new HttpHeaders();
//                    ������Ӧ��ʽ
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//                    ������Ӧ�ļ�
                fileName = URLEncoder.encode(fileName,"UTF-8");
                headers.setContentDispositionFormData("attachment", fileName);
//                    ���ļ��Զ�������ʽд��
                return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);

            }  
            
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
}
	