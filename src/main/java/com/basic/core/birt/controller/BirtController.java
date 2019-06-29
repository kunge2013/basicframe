package com.basic.core.birt.controller;

import com.basic.core.birt.BirtDto;
import com.basic.core.birt.ReportParameter;
import com.basic.core.service.IBirtService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@RestController
public class BirtController {
	@Autowired
	private IBirtService birtService;
	@Value("${birt.outPutPath}")
	private String outPutPath;


	@RequestMapping(value = "/report/exportPDF", method = RequestMethod.POST)
	public void exportStream(@ModelAttribute BirtDto dto, HttpServletResponse response) throws Exception {
		ReportParameter params = new ReportParameter();
		params.getParameter().put("id", dto.getId());
		params.getParameter().put("tt", new String[] {"1"});
		params.setFormat(dto.getFormat());
		params.setReportName(dto.getTemplateName());
		byte[] data = birtService.createFile(params);
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(data);
	}

	@RequestMapping("/displayPDF")
	public void displayPDF(HttpServletResponse response, String filename) {
		try {
			File file = new File(outPutPath + filename);
			FileInputStream fileInputStream = new FileInputStream(file);
			response.setHeader("Content-Disposition", "attachment;fileName=test.pdf");
			response.setContentType("multipart/form-data");
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(IOUtils.toByteArray(fileInputStream));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
