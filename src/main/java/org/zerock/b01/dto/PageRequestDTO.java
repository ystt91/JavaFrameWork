package org.zerock.b01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
//     페이지 요청 정보
//     페이지, 사이즈, 검색의 종류, keyword, link에 대한 타입 지정

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type; // 검색의 종류 t,c,w, tc,tw, twc

    private String keyword; // 들어올 키워드가 뭔지 알야아 함

    // type 문자열을 배열로 반환해주는 기능
    public String[] getTypes() {
        if (type == null || type.isEmpty()) {
            return null;
        }
        return type.split("");
    }

    //페이징 처리를 위해 Pageable 타입을 반환해야함
    //PageRequest.of 반환형이 Pageable이니까 그냥 return 하면 됨
    public Pageable getPageable(String... props) {
        return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
    }

    private String link;

    public String getLink() {
        if (link == null) {
            //멀티 스레드 환경에서 안전하지 않다. 단일 스레드에서는 builder를 사용하는게 좋다.
            StringBuilder builder = new StringBuilder();

            builder.append("page=" + this.page);

            builder.append("&size=" + this.size);

            if (type != null && type.length() > 0) {
                builder.append("&type=" + type);
            }

            if (keyword != null) {
                try {
                    builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }

            link = builder.toString();
        }
        return link;
    }
}
