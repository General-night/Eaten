package com.it.service.impl.front;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.front.AddressBook;
import com.it.mapper.front.AddressBookMapper;
import com.it.service.front.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author NightGeneral
 * @date 2022/4/6 23:32
 */

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
