package com.ryx.credit.common.util;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * 
 * @author hairtail7
 *
 */
public class PropertiesFileUtil {

    /**
     * @param resource
     * @param name
     * @param defaultValue
     * @return str
     */
    public static String getPropertyValue(String resource, String name,
                                          String defaultValue) {
        String value = getPropertyValue(resource, name);

        if ((value == null) || (value.length() == 0)) {
            return defaultValue;
        }
        return value;
    }

    /**
     * @param resource
     * @param name
     * @param defaultValue
     * @return int
     */
    public static int getPropertyValue(String resource, String name,
                                       int defaultValue) {
        int value;
        try {
            value = Integer.parseInt(getPropertyValue(resource, name));
        } catch (NumberFormatException e) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * @param resource
     * @param name
     * @return str
     */
    public static String getPropertyValue(String resource, String name) {
        if ((resource == null) || (resource.length() <= 0)) {
            return "";
        }

        if ((name == null) || (name.length() <= 0)) {
            return "";
        }

        Properties properties = getProperties(resource);
        if (properties == null) {
            return "";
        }

        String value = null;
        try {
            value = properties.getProperty(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value == null ? "" : value.trim();
    }
    
    /**
     * @param resource
     * @param name
     * @return str
     * @throws Exception 
     */
    public static String getPropertyValue(File resource, String name) {
        if ((name == null) || (name.length() <= 0)) {
            return "";
        }

        String value = null;
        try {
            Properties properties = getProperties(resource);
            if (properties == null) {
                return "";
            }
            value = properties.getProperty(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value == null ? "" : value.trim();
    }


    /**
     * @param resource
     * @param name
     * @return str
     */
    public static String getPropertyFileValue(String resource, String name) {
        if ((resource == null) || (resource.length() <= 0)) {
            return "";
        }

        if ((name == null) || (name.length() <= 0)) {
            return "";
        }

        Properties properties = getPropertieFile(resource);
        if (properties == null) {
            return "";
        }

        String value = null;
        try {
            value = properties.getProperty(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value == null ? "" : value.trim();
    }

    /**
     * @param resource
     * @return Properties
     */
    public static Properties getPropertieFile(String resource) {
        if ((resource == null) || (resource.length() <= 0)) {
            return null;
        }

        Properties properties = null;

        if (properties == null) {
            try {
                InputStream ins =
                        new BufferedInputStream(new FileInputStream(resource));
                if (ins != null) {
                    try {
                        properties = new Properties();
                        properties.load(ins);
                        ins.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        return properties;
    }

    /**
     * @param resource
     * @return Properties
     */
    public static Properties getProperties(String resource) {
        if ((resource == null) || (resource.length() <= 0)) {
            return null;
        }

        Properties properties = null;

        if (properties == null) {
            InputStream ins =
                    PropertiesFileUtil.class.getClassLoader()
                            .getResourceAsStream(resource);
            if (ins != null) {
                try {
                    properties = new Properties();
                    properties.load(ins);
                    ins.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }

        return properties;
    }
    
    /**
     * @param resource
     * @return Properties
     * @throws FileNotFoundException 
     */
    public static Properties getProperties(File resource) throws Exception {
        if (resource == null) {
            return null;
        }

        Properties properties = null;
        InputStream ins = null;
        try {
            ins = new FileInputStream(resource);
            properties = new Properties();
            properties.load(ins);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
            } catch (IOException e) {
            }
        }

        return properties;
    }

    public static void writePropertie(String resource, String key, String value)
        throws Exception {
        Properties prop = new Properties();
        InputStream fis = null;
        OutputStream fos = null;
        try {
            URL url = PropertiesFileUtil.class.getClassLoader().getResource(resource);
            File file = new File(url.toURI());
            fis = new FileInputStream(file);
            prop.load(fis);
            fis.close();// 一定要在修改值之前关闭fis
            fos = new FileOutputStream(file);
            prop.setProperty(key, value);
            prop.store(fos, "Update '" + key + "' value");
            fos.close();

        } catch (Exception e) {
            throw new Exception("write properties exception.", e);
        } finally {
            try {
                fos.close();
                fis.close();
            } catch (IOException e) {
            }
        }
    }
    
    public static void writePropertie(File propFile, String key, String value) throws Exception {
        Properties prop = new Properties();
        InputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new FileInputStream(propFile);
            prop.load(fis);
            fis.close();// 一定要在修改值之前关闭fis
            fos = new FileOutputStream(propFile);
            prop.setProperty(key, value);
            prop.store(fos, "Update '" + key + "' value");
            fos.close();

        } catch (Exception e) {
            throw new Exception("write properties exception.", e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }
        }
    }
    
    public static String getPropValue(String resource, String name) {
        File propFile;
        try {
            propFile = new File(FileUtil.getJarFile().getParentFile(), resource);
            return getPropertyValue(propFile, name);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        PropertiesFileUtil.writePropertie("data.properties",
                                           "bank.date.time",
                                           "20160902");
    }
}
