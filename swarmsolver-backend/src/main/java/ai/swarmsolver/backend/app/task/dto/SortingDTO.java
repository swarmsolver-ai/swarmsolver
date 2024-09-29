package ai.swarmsolver.backend.app.task.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SortingDTO {
    private SortingField field;
    private SortingOrder order;

    public static SortingDTO defaultSorting() {
        return SortingDTO.builder()
                .field(SortingField.NAME)
                .order(SortingOrder.ASCENDING)
                .build();
    }
}
