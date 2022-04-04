package com.it.dto;

import com.it.entity.backend.Setmeal;
import com.it.entity.backend.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 */
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
