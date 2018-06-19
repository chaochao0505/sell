package com.etiot.utils.page;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.io.VFS;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @description :自定义vsf实现，解决springboot 打包扫描不到别名的bug
 * @author : guojy
 * @version : V1.0.0
 * @date : 2017年10月23日
 */
public class SpringBootVFS extends VFS {

	/* (non-Javadoc)
	 * @see org.apache.ibatis.io.VFS#isValid()
	 */
	@Override
	public boolean isValid() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.io.VFS#list(java.net.URL, java.lang.String)
	 */
	@Override
	protected List<String> list(URL url, String path) throws IOException {
		ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        Resource[] resources = resolver.getResources(path + "/**/*.class");
        List<Resource> resources1 = Arrays.asList(resources);
        List<String> resourcePaths = new ArrayList<String>();
        for (Resource resource: resources1) {
            resourcePaths.add(preserveSubpackageName(resource.getURI(), path));
        }
        return resourcePaths;
	}
	
	private static String preserveSubpackageName(final URI uri, final String rootPath) {
        final String uriStr = uri.toString();
        final int start = uriStr.indexOf(rootPath);
        return uriStr.substring(start, uriStr.length());
    }
}
