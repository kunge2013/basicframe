package com.basic.core.birt;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.birt.report.engine.api.DocxRenderOption;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class BirtReportGenerator {
	@Autowired
	private IReportEngine birtEngine;

	public ByteArrayOutputStream generate(ReportParameter rptParam) throws Exception {
		// ByteArrayOutputStream 底层维护了一个byte[]，可以自动扩容
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IReportRunnable runnable = null;
		ClassPathResource cpr = new ClassPathResource("report/" + rptParam.getReportName() + ".rptdesign");
		runnable = birtEngine.openReportDesign(cpr.getInputStream());
		IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(runnable);
		runAndRenderTask.setParameterValues(setParameters(runnable, rptParam.getParameter()));

		IRenderOption options = new RenderOption();

		if (rptParam.getFormat().equalsIgnoreCase("pdf")) {
			PDFRenderOption pdfOptions = new PDFRenderOption(options);
			pdfOptions.setOutputFormat("pdf");
			pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.FIT_TO_PAGE_SIZE);
			pdfOptions.setOutputStream(baos);
			runAndRenderTask.setRenderOption(pdfOptions);
		}

		if (rptParam.getFormat().equalsIgnoreCase("xls")) {
			EXCELRenderOption exoptions = new EXCELRenderOption(options);
			exoptions.setOutputFormat("xls");
			exoptions.setOutputStream(baos);
			runAndRenderTask.setRenderOption(exoptions);
		}

		if (rptParam.getFormat().equalsIgnoreCase("docx")) {
			DocxRenderOption docxOptions = new DocxRenderOption();
			docxOptions.setOutputFormat("docx");
			docxOptions.setOutputStream(baos);
			runAndRenderTask.setRenderOption(docxOptions);
		}
		runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY,
				this.getClass().getClassLoader());
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
			IParameterDefnBase param = (IParameterDefnBase) iter.next();
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
