package com.gm;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.security.UserGroupInformation;
import java.io.IOException;

public class Kerberos {

	public static final String USER_KEY = "hdfs/node01.bigdata.hadoop@HADOOP.COM";
	public static final String KEY_TAB_PATH = "hadoop.keytab";

	static Configuration conf = new Configuration();

	static {
		// ����krb5.conf��·��
		System.setProperty("java.security.krb5.conf", "krb5.conf");
		// ������֤��ʽ����kerberos��simple����
		conf.set("hadoop.security.authentication", "kerberos");
		// namenode�ĵ�ַ�Ͷ˿�
		conf.set("fs.defaultFS", "hdfs://node01.bigdata.hadoop:9000");

		try {
			UserGroupInformation.setConfiguration(conf);
			UserGroupInformation.loginUserFromKeytab(USER_KEY, KEY_TAB_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		RemoteIterator<LocatedFileStatus> it = fs.listFiles(new Path("/user/"), true);
		while (it.hasNext()) {
			LocatedFileStatus next = it.next();
			System.out.println(next.getPath().getName());

		}
	}
}
