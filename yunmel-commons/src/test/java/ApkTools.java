import java.util.UUID;

public class ApkTools {
  public static void main(String[] args) {
    // File file = new File("F:\\ImageData\\BaiduYun_7.11.7.apk");
    // System.out.println(file.exists());
    // try (ApkParser apkParser = new ApkParser(file)) {
    // ApkMeta apkMeta = apkParser.getApkMeta();
    // System.out.println(apkMeta.getLabel());
    // System.out.println(apkMeta.getPackageName());
    // System.out.println(apkMeta.getVersionCode());
    // for (UseFeature feature : apkMeta.getUsesFeatures()) {
    // System.out.println(feature.getName());
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    System.out.println(UUID.randomUUID().toString());
    System.out.println("8c28895eb30b849d8f95cb37dacff29a".length());
  }
  /**
   * 
   * SELECT CONCAT('alter table ',table_name,' MODIFY ',column_name,' BIGINT(20);') as col
   * ,TABLE_NAME,COLUMN_NAME,DATA_TYPE FROM INFORMATION_SCHEMA.columns a WHERE TABLE_SCHEMA='ppm' --
   * 代表所在的数据库 AND COLUMN_NAME='update_date'
   * 
   */
}
