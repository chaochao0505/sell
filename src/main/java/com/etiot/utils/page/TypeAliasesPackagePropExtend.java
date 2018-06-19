package com.etiot.utils.page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
/**
 * 该类，目的是为了扩展mybatis扫描entity的功能，并且支持多个包路径配置。
 *
 * @author guojy
 * @version V1.0.0-RELEASE 日期：2016年8月5日
 * @since 1.0.0-RELEASE
 */
public class TypeAliasesPackagePropExtend implements FactoryBean<String> {
    private final Logger log = LoggerFactory.getLogger(TypeAliasesPackagePropExtend.class);
    
    /**
     * applicationContext-dao-base.xml文件中配置的名为typeAliasesPackages的属性，
     * 该属性值配置了多个entity的包路径，以数组形式返回。
     */
    private String[] typeAliasesPackages;
    
    /**
     * applicationContext-dao-base.xml文件中配置的名为rootPackage的属性，
     * 该属性是所有entity包路径的根目录。
     */
    private String rootPackage;

    /**
     * 解析mybatis中配置的多个entity的包路径。<br/>
     * 详细描述：该方法在加载配置时自动调用，通过对entity的包路径进行解析，把所有解析到的实体路径拼接成字符串返回给mybatis。<br/>
     * 使用方式：该方法无需调用，只需要在xml中配置mybatis的相关配置，具体参照applicationContext-dao-base.xml文件。
     * @return typeAliasesPackages配置的所有路径。
     */
    public String getObject() throws Exception {
        PathMatchingResourcePatternResolver resolover = new PathMatchingResourcePatternResolver();
        Assert.notNull(this.typeAliasesPackages, "typeAliasesPackages配置不能为空");
        try {
            Resource[] rootResource = resolover.getResources("classpath*:" + this.rootPackage);
            String[] rootPaths = new String[rootResource.length];
            int i = 0;
            for (Resource res : rootResource) {
                String path = res.getURL().toString();
                rootPaths[i] = path.substring(0, +path.length() - this.rootPackage.length());
                i++ ;
            }
            List<String> packagePathList = new ArrayList<String>();
            for (String typPackage : this.typeAliasesPackages) {
                Resource[] resources = resolover.getResources("classpath*:" + typPackage.replaceAll("\\.", "/"));
                for (Resource res : resources) {
                    String path = res.getURL().toString();
                    for (String rootPath : rootPaths) {
                        if (path.startsWith(rootPath)) {
                            path = path.replace(rootPath, "");
                            break;
                        }
                    }
                    if (path.endsWith("/")) {
                        path = path.substring(0, path.length() - 1);
                    }
                    path = path.replaceAll("/", ".");
                    packagePathList.add(path);
                }
            }
            String result[] = packagePathList.toArray(new String[packagePathList.size()]);
            String resultStr = StringUtils.arrayToDelimitedString(result, ",");
            log.info("Mybatis scan typeAliasesPackage is:[{}]", resultStr);
            return resultStr;
        } catch (IOException e) {
            log.error("Mybatis scan typeAliasesPackage error:[{}]", e.getMessage());
        }
        return null;
    }

    /**
     * getObject方法中返回的是拼接好的包路径字符串，因此该方法返回的也是String.class<br/>
     * 详细描述：实现FactoryBean接口设置的泛型为String，因此该方法也返回String.class对象。<br/>
     * 使用方式：该方法无需调用。
     * @return String.class对象类型
     */
    public Class<?> getObjectType() {
        return String.class;
    }
    
    /**
     * rootPackage变量的get方法，为了获取配置的根目录。
     * @return 工程的根目录com/ailk/tosd
     */
    public String getRootPackage() {
        return rootPackage;
    }
    
    /**
     * typeAliasesPackages变量的get方法，为了获取配置的所有路径。
     * @return typeAliasesPackages配置的所有路径
     */
    public String[] getTypeAliasesPackages() {
        return typeAliasesPackages;
    }

    /**
     * 设置该类是否为单例模式，默认设置为false，即不是单例模式。
     * @return boolean类型，是否为单例模式，默认返回false。
     */
    public boolean isSingleton() {
        return false;
    }
    
    /**
     * rootPackage变量的set方法。
     */
    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    /**
     * setTypeAliasesPackages变量的set方法。
     */
    public void setTypeAliasesPackages(String[] typeAliasesPackages) {
        this.typeAliasesPackages = typeAliasesPackages;
    }
}
