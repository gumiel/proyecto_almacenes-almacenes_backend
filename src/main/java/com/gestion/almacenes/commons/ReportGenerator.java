package com.gestion.almacenes.commons;

//@Service
public class ReportGenerator {

//    public byte[] exportToPdf(List<Pet> list) throws JRException, FileNotFoundException {
//        return JasperExportManager.exportReportToPdf(getReport(list));
//    }
//
//    public byte[] exportToXls(List<Pet> list) throws JRException, FileNotFoundException {
//        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
//        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
//        JRXlsExporter exporter = new JRXlsExporter();
//        exporter.setExporterInput(new SimpleExporterInput(getReport(list)));
//        exporter.setExporterOutput(output);
//        exporter.exportReport();
//        output.close();
//        return byteArray.toByteArray();
//    }
//
//    private JasperPrint getReport(List<Pet> list) throws FileNotFoundException, JRException {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("petsData", new JRBeanCollectionDataSource(list));
//
//        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
//                ResourceUtils.getFile("classpath:petsReport.jrxml")
//                        .getAbsolutePath()), params, new JREmptyDataSource());
//
//        return report;
//    }
}