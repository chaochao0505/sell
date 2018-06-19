package com.etiot.utils.page;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * @description :mybatis别名自动配置，自动扫描entity目录
 * @author : guojy
 * @version : V1.0.0
 * @date : 2017年9月19日
 */
public class TypeAliasesExtend implements FactoryBean<Class<?>[]> {
	private final Logger log = LoggerFactory.getLogger(TypeAliasesExtend.class);
	private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	private String[] typeAliasesPackages;

	@Override
	public Class<?>[] getObject() throws Exception {
		Assert.notNull(this.typeAliasesPackages, "typeAliasesPackages配置不能为空");
		PathMatchingResourcePatternResolver resolover = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolover);

		List<Class<?>> packagePathList = new ArrayList<Class<?>>();

		for (String typPackage : this.typeAliasesPackages) {
			String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ ClassUtils.convertClassNameToResourcePath(typPackage) + "/" + DEFAULT_RESOURCE_PATTERN;
			Resource[] resources = resolover.getResources(pattern);
			MetadataReader metadataReader = null;
			for (Resource res : resources) {
				if (res.isReadable()) {
					metadataReader = metadataReaderFactory.getMetadataReader(res);
					String className = metadataReader.getClassMetadata().getClassName();
					Class<?> cl = Class.forName(className);
					packagePathList.add(cl);
				}
			}
		}
		Class<?>[] result = packagePathList.toArray(new Class<?>[0]);
		log.info("Mybatis scan typeAliases is:[{}]", StringUtils.arrayToDelimitedString(result, ","));
		return result;
	}

	@Override
	public Class<?> getObjectType() {
		return Class[].class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	/**
	 * @param typeAliasesPackages
	 *            the typeAliasesPackages to set
	 */
	public void setTypeAliasesPackages(String[] typeAliasesPackages) {
		this.typeAliasesPackages = typeAliasesPackages;
	}

}
