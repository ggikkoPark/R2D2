package me.r2d2.batch;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Park Ji Hong, ggikko.
 */

@Data
@AllArgsConstructor
public class SelectedRestaurant {

    private String restaurantName;
    private int count;

}
