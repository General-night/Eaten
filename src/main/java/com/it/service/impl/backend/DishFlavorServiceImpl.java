package com.it.service.impl.backend;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.backend.DishFlavor;
import com.it.mapper.backend.DishFlavorMapper;
import com.it.service.backend.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author NightGeneral
 * @date 2022/4/3 16:22
 */

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
