package com.louis.mango.backup.util;

import java.io.File;
import java.io.IOException;

/**
 * MySQL备份还原工具类
 * @author Louis
 * @date Jan 15, 2019
 */
public class MySqlBackupRestoreUtils {

	/**
	 * 备份数据库
	 * @param host host地址，可以是本机也可以是远程
	 * @param userName 数据库的用户名
	 * @param password 数据库的密码

	 * @return
	 * @throws IOException 
	 */
	public static boolean backup(String host, String userName, String password, String backupFolderPath, String fileName,
			String database) throws Exception {
		File backupFolderFile=new File(backupFolderPath);
		if(!backupFolderFile.exists()){
			backupFolderFile.mkdirs();         //判断目录是否存在，若不存在就创建
		}
		if (!backupFolderPath.endsWith(File.separator)&& !backupFolderPath.endsWith("/")){
			backupFolderPath=backupFolderPath+File.separator;
		}
		String backupFilePath=backupFolderPath+fileName;
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("mysqldump --opt").append(" --add-drop-database ").append(" --add-drop-table");
		stringBuilder.append("-h").append(host).append("-u").append(userName).append("-p").append(password);
		stringBuilder.append("--result-file=").append(backupFilePath).append("--default-character-set=utf8").append(database);
		Process process=Runtime.getRuntime().exec(getCommand(stringBuilder.toString()));
		if(process.waitFor()==0){
			System.out.println("数据已经备份到"+backupFilePath+"文件中");
			return true;
		}
		return false;
	}

    /**
     * 还原数据库
     * @param restoreFilePath 数据库备份的脚本路径
     * @param host IP地址
     * @param database 数据库名称
     * @param userName 用户名
     * @param password 密码
     * @return
     */
	public static boolean restore(String restoreFilePath, String host, String userName, String password, String database)
			throws Exception {
		File restoreFile = new File(restoreFilePath);
		if (restoreFile.isDirectory()) {
			for (File file : restoreFile.listFiles()) {
				if (file.exists() && file.getPath().endsWith(".sql")) {
					restoreFilePath = file.getAbsolutePath();
					break;
				}
			}
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("mysql -h").append(host).append(" -u").append(userName).append(" -p").append(password);
		stringBuilder.append(" ").append(database).append(" < ").append(restoreFilePath);
		try {
			Process process = Runtime.getRuntime().exec(getCommand(stringBuilder.toString()));
			if (process.waitFor() == 0) {
				System.out.println("数据已从 " + restoreFilePath + " 导入到数据库中");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static String[] getCommand(String command) {
		String os = System.getProperty("os.name");  
		String shell = "/bin/bash";
		String c = "-c";
		if(os.toLowerCase().startsWith("win")){  
			shell = "cmd";
			c = "/c";
		}  
		String[] cmd = { shell, c, command };
		return cmd;
	}

	public static void main(String[] args) throws Exception {
		String host = "localhost";
		String userName = "root";
		String password = "fws1656621300";
		String database = "mango";
		
		System.out.println("开始备份");
		String backupFolderPath = "D:/beifen/";
		String fileName = "mango.sql";
		backup(host, userName, password, backupFolderPath, fileName, database);
		System.out.println("备份成功");
		
		System.out.println("开始还原");
		String restoreFilePath = "D:/beifen/mango.sql";
		restore(restoreFilePath, host, userName, password, database);
		System.out.println("还原成功");

	}

}
