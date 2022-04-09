package com.it.mapper.front;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.front.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NightGeneral
 * @date 2022/4/6 23:31
 */

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
