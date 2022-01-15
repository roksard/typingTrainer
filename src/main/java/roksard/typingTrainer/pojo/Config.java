package roksard.typingTrainer.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Config {
    public static final Config DEFAULT = new Config();
    private Integer winX;
    private Integer winY;
    private Integer winW;
    private Integer winH;
    private String fileName;
    private Integer filePos;
    private List<Statistic> statistic = new ArrayList<>();
    String fontName;
    private Integer fontStyle;
    private Integer fontSize;
}
