package com.gestion.almacenes.commons;

import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;

@Component
@AllArgsConstructor
public class ReportManager {
	private final DataSource dataSource;
	private final ReportRepository reportRepository;
	public ByteArrayOutputStream export(Report reportSettings,ReportConfigurationPojo configurationPojo,
			Connection connection) throws JRException, IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		Boolean isPdf = configurationPojo.getParams().get("tipo").toString().equalsIgnoreCase(ReportTypeEnum.PDF.toString());
		String extension;
		 if (isPdf) {
			extension = "";
		} else {
			extension = "Xlsa";
		}
		ClassPathResource resource = new ClassPathResource(
				ReportPathPartsEnum.REPORT_FOLDER.getValue() + File.separator + reportSettings.getReportFileName() + extension + ReportPathPartsEnum.JASPER.getValue());

		configurationPojo.getParams().put("TITTLE",reportSettings.getTittle());
		configurationPojo.getParams().put("SUB_TITTLE",reportSettings.getSubTittle());



		InputStream inputStream = resource.getInputStream();
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, configurationPojo.getParams(), connection);
		if (isPdf) {
			System.out.println("no**"+jasperPrint);
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
		}else{

		}
		return stream;
	}
	public ReportPojo generateReport(ReportConfigurationPojo configurationPojo)
			throws JRException, IOException, SQLException {
		System.out.println("code"+configurationPojo);
		//Report reportSettings = reportRepository.getByCode(configurationPojo.getCodigo());
		Report reportSettings = reportRepository.getByCode(configurationPojo.getCode());
		System.out.println(configurationPojo.getCode());
		System.out.println("===>"+reportSettings);
		ReportPojo pojo = new ReportPojo();
		String type = configurationPojo.getParams().get("tipo").toString();
		String extension;
		boolean isXls = type.equalsIgnoreCase(ReportTypeEnum.XLSX.name());
		boolean isPdf = type.equalsIgnoreCase(ReportTypeEnum.PDF.name());
		if (isXls) {
			extension = ".xlsx";
		} else if (isPdf) {
			extension = ".pdf";
		} else {
			extension = ".xls";
		}
		String timeStamp = String.valueOf(System.currentTimeMillis());
		System.out.println("*****************************************************************");

		System.out.println(type+extension+timeStamp);
		System.out.println(">>>"+reportSettings+configurationPojo);
		System.out.println(reportSettings.getReportName());
		System.out.println("*****************************************************************");
		pojo.setFileName(reportSettings.getReportName() + "_" + timeStamp + extension);

		try (Connection connection = dataSource.getConnection()) {
			ByteArrayOutputStream stream = this.export(reportSettings,configurationPojo, connection);
			byte[] bytes = stream.toByteArray();
			pojo.setStream(new ByteArrayInputStream(bytes));
			pojo.setLength(bytes.length);
		}

		return pojo;
	}
	public ResponseEntity<Resource> downloadReport(ReportPojo pojo, String type) {
		InputStreamResource streamResource = new InputStreamResource(pojo.getStream());
		MediaType mediaType;
		if (type.equalsIgnoreCase(ReportTypeEnum.XLSX.name())) {
			mediaType = MediaType.APPLICATION_OCTET_STREAM;
		} else {
			mediaType = MediaType.APPLICATION_PDF;
		}
		return ResponseEntity.ok()
				.header("Content-Disposition", "inline; filename=\"" + pojo.getFileName() + "\"")
				.contentLength(pojo.getLength())
				.contentType(mediaType)
				.body(streamResource);
	}
	public AttachmentPojo downloadReportBase64(ReportPojo pojo, String type) {
		try {
			InputStreamResource streamResource = new InputStreamResource(pojo.getStream());
			AttachmentPojo attachmentPojo = new AttachmentPojo();
			byte[] byteArray = IOUtils.toByteArray(pojo.getStream());
			String base64Content = Base64.getEncoder().encodeToString(byteArray);
			attachmentPojo.setFile(base64Content);
			String fileName= pojo.getFileName();
			String[] splitName = fileName.split("\\.");
			attachmentPojo.setName(splitName[0]);
			attachmentPojo.setTypeFile(splitName[1]);
			return attachmentPojo;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}


}
