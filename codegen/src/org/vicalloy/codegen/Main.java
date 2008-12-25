package org.vicalloy.codegen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.vicalloy.codegen_demo.hello.model.DemoUser;

import freemarker.template.TemplateException;

public class Main {
	private Config config;

	private String basePkg;

	@SuppressWarnings("unchecked")
	private List<Class> classes = new ArrayList<Class>();

	{
		// 相关参数的设置
		basePkg = "org.vicalloy.codegen_demo";
		// TODO 在这里添加需要生成的class
		classes.add(DemoUser.class);
	}

	/***************************************************************************
	 * 生成代码
	 * 
	 * @throws IOException
	 * @throws TemplateException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public void gen() throws IOException, TemplateException, DocumentException {
		// FIXME 考虑包名为当前目录的情况，这个时候baseAction应当不用生成
		this.config = new Config();
		config.setBasePkg(basePkg);
		for (Class c : classes) {
			new Generator(config, c).genAll();
		}
	}

	/**
	 * @param args
	 * @throws TemplateException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void main(String[] args) throws IOException,
			TemplateException, DocumentException {
		new Main().gen();
		System.out.println("-----OK-----");
	}

}
