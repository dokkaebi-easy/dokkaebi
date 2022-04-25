package com.ssafy.dockerby.dto.project;

import com.sun.istack.NotNull;
import java.util.List;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectListDto {
    private List<Map> projects;
}
