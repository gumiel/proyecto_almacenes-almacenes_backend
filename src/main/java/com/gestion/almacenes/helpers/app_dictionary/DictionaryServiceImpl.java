package com.gestion.almacenes.helpers.app_dictionary;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

  private final AppDictionaryConfig appDictionaryConfig;

  @Override
  public String generatedMarkDown() throws Exception {
    StringBuilder tablaMd = new StringBuilder();
    Map<String,String> tableWithoutDescriptions = new HashMap<>();

    List<String> listEntities = listClassesInPackage(appDictionaryConfig.getDictionaryFolderPackage());

    for (String nameEntity : listEntities) {
      tablaMd.append(createTableMarkDown(nameEntity, tableWithoutDescriptions));
    }

    tableWithoutDescriptions.forEach((table, field)->{
      System.out.println("Tabla :"+table+ ", field:"+field);
    });

    return this.sendToGitlabWiki(tablaMd);

  }

  private StringBuilder createTableMarkDown(String className, Map<String,String> listEntitiesField ) throws ClassNotFoundException {

    StringBuilder tablaMd = new StringBuilder();

    Class<?> clazz = null;
    if (!className.contains(".")) {
      clazz = Class.forName(appDictionaryConfig.getDictionaryRoutePackage() + className);
    } else {
      clazz = Class.forName(className);
    }

    tablaMd.append("  \n");
    tablaMd.append("Nombre de tabla: " + clazz.getSimpleName() + "  \n");
    tablaMd.append("| Tipo de dato | Nombre de campo | Descripcion del campo |\n");
    tablaMd.append("|:----------|:-------------|:------|\n");

    for (var field : clazz.getDeclaredFields()) {

      Column column = null;
      Schema schema = null;

      for (var annotation : field.getAnnotations()) {

        if (annotation instanceof Column) {
          column = (Column) annotation;
        }

        if (annotation instanceof Schema) {
          schema = (Schema) annotation;
        }

      }

      String fieldType = field.getType().getSimpleName();
      String fieldName =
          (column != null) ? (!Objects.equals(column.name(), "")) ? column.name() : field.getName()
              : field.getName();
      String fieldDescription = (schema != null) ? schema.description() : "";
      tablaMd.append("| ").append(fieldType).append(" |").append(fieldName).append("|")
          .append(fieldDescription).append("|\n");

      if(fieldDescription==null || fieldDescription.isEmpty()){
        listEntitiesField.put(clazz.getSimpleName(),fieldName );
      }

    }
    tablaMd.append("  \n");
    return tablaMd;
  }

  /**
   * Devuelve la lista de todas las tablas en la carpeta de entidades
   *
   * @param directoryName Directorio
   * @return Lista de entidades
   * @throws ClassNotFoundException
   */
  private static List<String> listClassesInPackage(String directoryName)
      throws ClassNotFoundException {
    File directory = new File(directoryName);
    List<String> resultList = new ArrayList<>();

    File[] fList = directory.listFiles();
    if (fList != null) {
      for (File file : fList) {
        if (file.isFile() && file.getName().endsWith(".java")) {
          String packageName = directoryName.replace("src/main/java/", "").replace("/", ".");
          resultList.add(packageName + "." + file.getName().replace(".java", ""));
        } else if (file.isDirectory()) {
          resultList.addAll(listClassesInPackage(file.getAbsolutePath()));
        }
      }
    }
    return resultList;
  }

  private String sendToGitlabWiki(StringBuilder stringMD) throws Exception {

    disableSSLVerification(); // Deshabilita la validación SSL

    URL url = new URL(
        appDictionaryConfig.getGitlabApiUrl() + "/projects/" + appDictionaryConfig.getGitlabProjectId() + "/wikis/"
            + appDictionaryConfig.getGitlabPageSlug());
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("PUT");
    conn.setRequestProperty("PRIVATE-TOKEN", appDictionaryConfig.getGitlabPrivateToken());
    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    conn.setDoOutput(true);

    // Datos a enviar (contenido nuevo)
    String postData = "content=" + String.valueOf(stringMD);
    try (OutputStream os = conn.getOutputStream()) {
      os.write(postData.getBytes());
    }
    // Respuesta del servidor
    return "Código de respuesta: " + conn.getResponseCode();
  }

  private static void disableSSLVerification() throws Exception {
    TrustManager[] trustAllCertificates = new TrustManager[]{new X509TrustManager() {
      public void checkClientTrusted(X509Certificate[] chain, String authType) {
      }

      public void checkServerTrusted(X509Certificate[] chain, String authType) {
      }

      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }
    }};

    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(null, trustAllCertificates, new SecureRandom());

    // Configurar el SSLSocketFactory globalmente
    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

    // Deshabilitar la verificación del hostname
    HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
  }

}
