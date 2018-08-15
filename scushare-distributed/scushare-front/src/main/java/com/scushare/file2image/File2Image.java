package com.scushare.file2image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class File2Image {
	
	static final int wdDoNotSaveChanges = 0;// ����������ĸ��ġ�  
    static final int wdFormatPDF = 17;// wordתPDF ��ʽ  
    static final int ppSaveAsPDF = 32;// ppt תPDF ��ʽ  
    
    
    public static int transform(String filepath){
        File file = new File(filepath);
        int pagenum;
      	String name = file.getName();
      	String ex = getExtensionName(name);
      	name = getFileNameNoEx(name);
      	String source = filepath;
      	String target = file.getParentFile().getAbsolutePath() +"\\" + name + ".pdf";
      	//System.out.println(target);
      	if (ex.equals("pdf")){
      		
      		pagenum = File2Image.parsePdf(file);
      		return pagenum;
      		
        }else if (ex.equals("doc") || ex.equals("docx")) {
        	
      		word2pdf(source, target);
      		
      	} else if (ex.equals("xls")||ex.equals("xlsx")) {
      		
      		excel2pdf(source, target);
      		
      	} else if (ex.equals("ppt")||ex.equals("pptx")) {
      		
      		ppt2pdf(source, target);
      		
      	}

      	  	File pdffile = new File(target);
      	  	try {
	      	  	pagenum = File2Image.parsePdf(pdffile);
      	  	}catch (Exception e) {
				e.printStackTrace();
				return -1;
			}

      	return pagenum;
    }
        
	public static void word2pdf(String source,String target){  
        System.out.println("����Word");  
        long start = System.currentTimeMillis();  
        ActiveXComponent app = null;  
        try {  
         app = new ActiveXComponent("Word.Application");  
         app.setProperty("Visible", false);  
        
         Dispatch docs = app.getProperty("Documents").toDispatch();  
         System.out.println("���ĵ�" + source);  
         Dispatch doc = Dispatch.call(docs,//  
           "Open", //  
           source,// FileName  
           false,// ConfirmConversions  
           true // ReadOnly  
           ).toDispatch();  
        
         System.out.println("ת���ĵ���PDF " + target);  
         File tofile = new File(target);  
         if (tofile.exists()) {  
          tofile.delete();  
         }  
         Dispatch.call(doc,//  
           "SaveAs", //  
           target, // FileName  
           wdFormatPDF);  
        
         Dispatch.call(doc, "Close", false);  
         long end = System.currentTimeMillis();  
         System.out.println("ת�����..��ʱ��" + (end - start) + "ms.");  
        } catch (Exception e) {  
         System.out.println("========Error:�ĵ�ת��ʧ�ܣ�" + e.getMessage());  
        } finally {  
         if (app != null)  
          app.invoke("Quit", wdDoNotSaveChanges);  
        }  
       }  
        
       public static void ppt2pdf(String source,String target){  
        System.out.println("����PPT");  
        long start = System.currentTimeMillis();  
        ActiveXComponent app = null;  
        try {  
         app = new ActiveXComponent("Powerpoint.Application");  
         Dispatch presentations = app.getProperty("Presentations").toDispatch();  
         System.out.println("���ĵ�" + source);  
         Dispatch presentation = Dispatch.call(presentations,//  
           "Open",   
           source,// FileName  
           true,// ReadOnly  
           true,// Untitled ָ���ļ��Ƿ��б��⡣  
           false // WithWindow ָ���ļ��Ƿ�ɼ���  
           ).toDispatch();  
        
         System.out.println("ת���ĵ���PDF " + target);  
         File tofile = new File(target);  
         if (tofile.exists()) {  
          tofile.delete();  
         }  
         Dispatch.call(presentation,//  
           "SaveAs", //  
           target, // FileName  
           ppSaveAsPDF);  
        
         Dispatch.call(presentation, "Close");  
         long end = System.currentTimeMillis();  
         System.out.println("ת�����..��ʱ��" + (end - start) + "ms.");  
        } catch (Exception e) {  
         System.out.println("========Error:�ĵ�ת��ʧ�ܣ�" + e.getMessage());  
        } finally {  
         if (app != null) app.invoke("Quit");  
        }  
       }  
        
       public static void excel2pdf(String source, String target) { 
    	   File f = new File(source);
    	   ComThread.InitSTA(); 
    	   System.out.println("����Excel");  
            long start = System.currentTimeMillis();  
            try {  
            System.out.println("���ĵ�" + source);
            
            ComThread.InitSTA();
            ActiveXComponent app = new ActiveXComponent("Excel.Application");
            app.setProperty("Visible", new Variant(false));
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.invoke(
                    (Dispatch) excels,
                    "Open",
                    Dispatch.Method,
                    new Object[] { source, new Variant(false),
                            new Variant(true) }, new int[9]).toDispatch();
            // �����ӡ
            // ��ȡactivate����
            Dispatch currentSheet = Dispatch.get((Dispatch) excel,
                    "ActiveSheet").toDispatch();
            Dispatch pageSetup = Dispatch.get(currentSheet, "PageSetup")
                    .toDispatch();
            Dispatch.put(pageSetup, "Orientation", new Variant(2));
            
            Dispatch.invoke(excel,"ExportAsFixedFormat",Dispatch.Method,new Object[]{  
                    new Variant(0), //PDF��ʽ=0
                    target,  
                    new Variant(0)  //0=��׼ (���ɵ�PDFͼƬ�����ģ��) 1=��С�ļ� (���ɵ�PDFͼƬ����һ����Ϳ)  
                },new int[1]);
          
            Dispatch.call(excel, "Close",new Variant(false));  
        	if (app != null) {
        		app.invoke("Quit", new Variant[] {});
        		app = null;
        	}
            ComThread.Release();
    
            System.out.println("ת���ĵ���PDF " + target);  
            //Dispatch.call(workbook, "Close", f);  
            long end = System.currentTimeMillis();  
            System.out.println("ת�����..��ʱ��" + (end - start) + "ms.");  
            } catch (Exception e) {  
             System.out.println("========Error:�ĵ�ת��ʧ�ܣ�" + e.getMessage());  
            }
       }
	
	

    public static int parsePdf(File pdfFile) {
	PDDocument document = null;
	
	try {
	    long start = System.currentTimeMillis();
	    document = PDDocument.load(pdfFile);
	    if (document == null) {
		return -1;
	    }
	    int size = document.getNumberOfPages();
	    BufferedImage image = null;
	    FileOutputStream out = null;
	    String name = pdfFile.getName();
	    
	    name = getFileNameNoEx(name);
	    String path = pdfFile.getParentFile().getAbsolutePath();
	    File f =new File(path+File.separator+name);
	    f.mkdir();
	    for (int i = 0; i < size; i++) {
		image = new PDFRenderer(document).renderImageWithDPI(i, 130, ImageType.RGB);
		out = new FileOutputStream(path+"//"+name+"//" + i + ".jpg");
		ImageIO.write(image, "jpg", out);
		out.close();
	    }
	    long end = System.currentTimeMillis();
	    System.out.println("�����ɹ���ʱ:" + (end - start) + "ms");
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
		if(document != null){
		    document.close();
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return document.getNumberOfPages();
    }
    
    
    
    /**
     * ��������ͬ��ͼƬ������׷����һ�� ##ע�⣺���ȱ�����ͬ,������ͼƬ
     * 
     * @param piclist �ļ�������
     * @param outPath ���·��
     */
    public static void y2Pic(List<BufferedImage> piclist, String outPath) {
	if (piclist == null || piclist.size() <= 0) {
	    System.out.println("ͼƬ����Ϊ��!");
	    return;
	}
	try {
		    int height 		= 0, // �ܸ߶�
			    width 		= 0, // �ܿ���
			    _height 	= 0, // ��ʱ�ĸ߶� , �򱣴�ƫ�Ƹ߶�
			    __height 	= 0, // ��ʱ�ĸ߶ȣ���Ҫ����ÿ���߶�
			    picNum = piclist.size();// ͼƬ������
		    File fileImg = null; // �����ȡ����ͼƬ
		    int[] heightArray = new int[picNum]; // ����ÿ���ļ��ĸ߶�
		    BufferedImage buffer = null; // ����ͼƬ��
		    List<int[]> imgRGB = new ArrayList<int[]>(); // �������е�ͼƬ��RGB
		    int[] _imgRGB; // ����һ��ͼƬ�е�RGB����
		    for (int i = 0; i < picNum; i++) {
		    	buffer = piclist.get(i);
		    	heightArray[i] = _height = buffer.getHeight();// ͼƬ�߶�
				if (i == 0) {
				    width = buffer.getWidth();// ͼƬ����
				}
				height += _height; // ��ȡ�ܸ߶�
				_imgRGB = new int[width * _height];// ��ͼƬ�ж�ȡRGB
				_imgRGB = buffer.getRGB(0, 0, width, _height, _imgRGB, 0, width);
				imgRGB.add(_imgRGB);
		    }
		    _height = 0; // ����ƫ�Ƹ߶�Ϊ0
		    // ������ͼƬ
		    BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		    for (int i = 0; i < picNum; i++) {
				__height = heightArray[i];
				if (i != 0)
				    _height += __height; // ����ƫ�Ƹ߶�
				imageResult.setRGB(0, _height, width, __height, imgRGB.get(i), 0, width); // д������
		    }
		    File outFile = new File(outPath);
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    ImageIO.write(imageResult, "jpg", out);// дͼƬ
		    byte[] b = out.toByteArray();
		    FileOutputStream output = new FileOutputStream(outFile);
	    	output.write(b);
	    	out.close();
	    	output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static String getFileNameNoEx(String filename) { 
	     if ((filename != null) && (filename.length() > 0)) { 
	         int dot = filename.lastIndexOf('.'); 
	         if ((dot >-1) && (dot < (filename.length()))) { 
	             return filename.substring(0, dot); 
	         } 
	     } 
	     return filename; 
	 }
	 
	 public static String getExtensionName(String filename) {   
	     if ((filename != null) && (filename.length() > 0)) {   
	         int dot = filename.lastIndexOf('.');   
	         if ((dot >-1) && (dot < (filename.length() - 1))) {   
	             return filename.substring(dot + 1);   
	         }   
	     }   
	     return filename;   
	 }
    
    
    

}