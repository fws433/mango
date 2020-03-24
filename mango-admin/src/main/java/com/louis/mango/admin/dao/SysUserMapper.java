package com.louis.mango.admin.dao;

import java.util.List;

import com.louis.mango.admin.model.SysUser;
import org.apache.ibatis.annotations.Param;

//@Repository(用来标注访问组件)
public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 查询全部
     *
     * @return
     */
    List<SysUser>findAll();
    List<SysUser> findPage();
     SysUser findByName(@Param(value="name")String name);
     List<SysUser>findPageByNameAndEmail(@Param(value="name")String name,@Param(value
     ="email")String email);

}