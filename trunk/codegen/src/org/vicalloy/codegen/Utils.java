package org.vicalloy.codegen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Utils {
	/***************************************************************************
	 * 创建多级目录
	 * 
	 * @param aParentDir
	 *            String
	 * @param aSubDir
	 *            以 / 开头
	 * @return boolean 是否成功
	 */
	public static boolean creatDirs(String aParentDir, String aSubDir) {
		File aFile = new File(aParentDir);
		if (aFile.exists()) {
			return creatDirs(aParentDir + "/" + aSubDir);
		} else {
			return false;
		}
	}

	/***************************************************************************
	 * 读出文件中的文本，同时去掉前后的空格
	 * 
	 * @param fileName
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String fileName, String encoding)
			throws IOException {
		if (encoding == null)
			encoding = "UTF-8";
		File afile = new File(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(afile), encoding));
		StringBuffer s = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			s.append(line);
			s.append("\r\n");
		}
		reader.close();
		return s.toString().trim();
	}

	/***************************************************************************
	 * 创建目录
	 * 
	 * @param aDir
	 * @return
	 */
	public static boolean creatDirs(String aDir) {
		File aSubFile = new File(aDir);
		if (!aSubFile.exists()) {
			return aSubFile.mkdirs();
		} else {
			return true;
		}
	}

	/***************************************************************************
	 * 将字符串写入文本
	 * 
	 * @param s
	 *            需要写入文件的文字
	 * @param fileName
	 *            文件名
	 * @param encoding
	 *            编码
	 * @param rewrite
	 *            文件存在时候是否覆盖
	 * @throws IOException
	 */
	public static void write2file(String s, String fileName, String encoding,
			boolean rewrite) throws IOException {
		if (encoding == null) {
			encoding = "UTF-8";
		}
		File afile = new File(fileName);
		if (afile.exists() && !rewrite) {
			return;
		}
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(afile), encoding));
		out.write(s);
		out.close();
	}

	/***************************************************************************
	 * 处理模板，返回处理后的string
	 * 
	 * @param t
	 *            模板
	 * @param encoding
	 *            编码方式
	 * @param map
	 *            模板匹配文件
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	@SuppressWarnings("unchecked")
	public static String processTemplate(Template t, String encoding, Map map)
			throws TemplateException, IOException {
		Writer out = new StringWriter();
		t.process(map, out);
		String s = out.toString().trim();
		out.close();
		return s;
	}

	/***************************************************************************
	 * 替换文件中的某些特定文字
	 * 
	 * @param fileName
	 * @param encoding
	 * @param oldStr
	 * @param newStr
	 * @throws IOException
	 */
	public static void replaceFileStr(String fileName, String encoding,
			String oldStr, String newStr) throws IOException {
		if (encoding == null) {
			encoding = "UTF-8";
		}
		String s = readFile(fileName, encoding);
		s = s.replace(oldStr, newStr);
		write2file(s, fileName, encoding, true);
	}

	/**
	 * 在某个xml文档中查看某个节点是否存在
	 * /beans/bean[@class='com.shareinfo.eim.security.web.UserAction']
	 * 
	 * @param fileName
	 * @param path
	 * @return
	 * @throws DocumentException
	 */
	public static boolean hasNode(String fileName, String path)
			throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(fileName));
		Node node = document.selectSingleNode(path);
		if (node == null) {
			return false;
		} else {
			return true;
		}
	}

}
