package me.r2d2.keyword;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Park Ji Hong, ggikko.
 */
@Entity
@Data
public class Keywords {

        @Id
        @Column(name = "subway_number")
        private String subwayNumber;

        /** 키워드 1 */
        private String keyword1;
        /** 키워드 2 */
        private String keyword2;
        /** 키워드 3 */
        private String keyword3;
        /** 키워드 4 */
        private String keyword4;
        /** 키워드 5 */
        private String keyword5;
        /** 키워드 6 */
        private String keyword6;
        /** 키워드 7 */
        private String keyword7;
        /** 키워드 8 */
        private String keyword8;
        /** 키워드 9 */
        private String keyword9;
        /** 키워드 10 */
        private String keyword10;


}
