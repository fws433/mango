package com.louis.mango.backup.service;

import org.springframework.stereotype.Service;


public interface MysqlBackupService {
    boolean backup(String host,String userName,String password,
                   String backupFolderPath,String fileName,String database) throws Exception;
    boolean restore(String restoreFilePath,String host,String userName,String password,
                    String database)throws Exception;
}
