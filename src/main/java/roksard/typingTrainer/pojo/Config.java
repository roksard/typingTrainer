package roksard.typingTrainer.pojo;

import java.util.ArrayList;
import java.util.List;

public class Config {
	public Config() {
	}

	public Config(Integer winX, Integer winY, Integer winW, Integer winH, String fileName, Long filePos,
			List<Statistic> statistic, String fontName, Integer fontStyle, Integer fontSize) {
		this.winX = winX;
		this.winY = winY;
		this.winW = winW;
		this.winH = winH;
		this.fileName = fileName;
		this.filePos = filePos;
		this.statistic = statistic;
		this.fontName = fontName;
		this.fontStyle = fontStyle;
		this.fontSize = fontSize;
	}

	public void setWinX(Integer winX) {
		this.winX = winX;
	}

	public void setWinY(Integer winY) {
		this.winY = winY;
	}

	public void setWinW(Integer winW) {
		this.winW = winW;
	}

	public void setWinH(Integer winH) {
		this.winH = winH;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFilePos(Long filePos) {
		this.filePos = filePos;
	}

	public void setStatistic(List<Statistic> statistic) {
		this.statistic = statistic;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public void setFontStyle(Integer fontStyle) {
		this.fontStyle = fontStyle;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public Integer getWinX() {
		return winX;
	}

	public Integer getWinY() {
		return winY;
	}

	public Integer getWinW() {
		return winW;
	}

	public Integer getWinH() {
		return winH;
	}

	public String getFileName() {
		return fileName;
	}

	public Long getFilePos() {
		return filePos;
	}

	public List<Statistic> getStatistic() {
		return statistic;
	}

	public String getFontName() {
		return fontName;
	}

	public Integer getFontStyle() {
		return fontStyle;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public static final Config DEFAULT = new Config();
	private Integer winX;
	private Integer winY;
	private Integer winW;
	private Integer winH;
	private String fileName;
	private Long filePos;
	private List<Statistic> statistic = new ArrayList<>();
	String fontName;
	private Integer fontStyle;
	private Integer fontSize;
}
