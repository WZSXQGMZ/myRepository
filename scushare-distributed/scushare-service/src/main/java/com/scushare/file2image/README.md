#pdf向图片的转换，我们调用的是public static int transform(String filepath);传入文件路径，然后自我判断进行文件转换。

public static int transform(String filepath);//对文件类型判断并进行转换

public static Object parsePdf(File pdfFile);//pdf转换为图片

public static void y2Pic(List<BufferedImage> piclist, String outPath);//图片连接且来形成巨型图片，输入为上面方法的结果

#office对pdf的转换
public void word2pdf(String source,String target);//word转换为pdf

public void ppt2pdf(String source,String target);//ppt转换为pdf

public void excel2pdf(String source, String target);//excel转化为pdf


#两个工具函数

public static String getFileNameNoEx(String filename);//获取文件名没有扩展名

public static String getExtensionName(String filename);//获取文件扩展名
