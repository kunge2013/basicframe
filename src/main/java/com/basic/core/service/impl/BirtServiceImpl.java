package com.basic.core.service.impl;

import com.basic.core.birt.ReportParameter;
import com.basic.core.service.IBirtService;
import org.eclipse.birt.report.engine.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.*;

@Service
public class BirtServiceImpl implements IBirtService {

	@Autowired
	private IReportEngine birtEngine;

	@Value("${birt.outPutPath}")
	private String outPutPath;

	@Value("${birt.suffix}")
	private String suffix;

	@Override
	public String createPDF(ReportParameter rm) throws Exception {
		String name = "report" + System.currentTimeMillis() + "." + rm.getFormat();
		ByteArrayOutputStream baos = generate(rm);
		FileOutputStream fops = new FileOutputStream(outPutPath + name);
		fops.write(baos.toByteArray());
		fops.close();
		baos.close();
		return suffix + "/" + name;
	}

	@Override
	public void log(Object obj) {
		System.out.println(obj);
	}

	@Override
	public byte[] createFile(ReportParameter params) throws Exception {
		ByteArrayOutputStream baos = generate(params);
		byte[] data = baos.toByteArray();
		baos.close();
		return data;
	}

	private ByteArrayOutputStream generate(ReportParameter rptParam) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IReportRunnable runnable = null;
		ClassPathResource cpr = new ClassPathResource("report/" + rptParam.getReportName() + ".rptdesign");
		runnable = birtEngine.openReportDesign(cpr.getInputStream());
		IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(runnable);
		runAndRenderTask.setParameterValues(setParameters(runnable, rptParam.getParameter()));
		runAndRenderTask.setLocale(Locale.CHINA);
		IRenderOption options = null;// new RenderOption();
		
		if (rptParam.getFormat().equalsIgnoreCase("pdf")) {
			options = new PDFRenderOption();
			options.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.FIT_TO_PAGE_SIZE);
		}

		if (rptParam.getFormat().equalsIgnoreCase("xls")) {
			options = new EXCELRenderOption();
		}

		if (rptParam.getFormat().equalsIgnoreCase("docx")) {
			options = new DocxRenderOption();
		}
		options.setOutputFormat(rptParam.getFormat());
		options.setOutputStream(baos);
		runAndRenderTask.setRenderOption(options);

		runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, this.getClass().getClassLoader());
		runAndRenderTask.run();
		runAndRenderTask.close();
		return baos;
	}

	protected HashMap<String, Object> setParameters(IReportRunnable report, Map<String, Object> m) throws Exception {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		IGetParameterDefinitionTask task = birtEngine.createGetParameterDefinitionTask(report);
		// 拿到birt里所有的parameter定义
		Collection<IParameterDefnBase> params = task.getParameterDefns(true);
		Iterator<IParameterDefnBase> iter = params.iterator();
		while (iter.hasNext()) {
			IParameterDefnBase param = iter.next();
			Object val = m.get(param.getName());
			// 如果拿到birt的parameter有定义
			if (val != null) {
				parms.put(param.getName(), val);
			}
		}
		task.close();
		return parms;
	}
}
