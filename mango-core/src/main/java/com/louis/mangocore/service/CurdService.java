package com.louis.mangocore.service;

/*
import com.louis.mangocore.page.PageRequest;
import com.louis.mangocore.page.PageResult;
*/
import com.louis.mangocore.page.PageRequest;
import com.louis.mangocore.page.PageResult;
import java.util.List;
/*
*对通用增删改查接口的封装，统一定义了包含保存，删除，批量删除，
* 根据ID查询和分页查询方法
*
* */
public interface CurdService<T> {
    int save(T record);  //保存操作
    int delete(T record);//删除操作
    int delete(List<T> records); //批量删除操作
    T findById(Long id); //根据id查询
    PageResult findPage(PageRequest pageRequest);


}
