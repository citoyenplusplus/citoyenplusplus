package org.citpp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.citpp.service.ResourceUnarchiver;
import org.citpp.service.Service;
import org.citpp.service.ServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;

public class ResourceUnarchiverImpl implements ResourceUnarchiver, BeanNameAware {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceUnarchiverImpl.class);

	private String beanName;

	@Override
	public void execute(ServiceContext context) {
		String sourceFilePath = (String) context.getParam(ResourceUnarchiver.INPUT_FILE_PATH_KEY);
		String filePath = new SimpleDateFormat((String) context.getParam(ResourceUnarchiver.UNARCHIVE_PATH_PATTERN_KEY))
				.format(new Date());
		context.putParam(Service.DEFAULT_OUTPUT_STRING_KEY, filePath);
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(sourceFilePath));
			ZipEntry ze = zis.getNextEntry();
			if (ze != null) {
				File newFile = new File(filePath);
				if (!newFile.getParentFile().exists()) {
					new File(newFile.getParent()).mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				byte[] buffer = new byte[1024];
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
			}
			zis.closeEntry();
			zis.close();
		} catch (IOException e) {
			LOG.error("{} Unable to unarchive file {}", this.beanName, filePath, e);
		}
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

}
