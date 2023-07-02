package mx.gob.sedesol.basegestor.ws.moodle.dto;

import java.util.Date;
import java.util.List;

import mx.gob.sedesol.basegestor.ws.moodle.dto.courses.CourseFormatOption;

public class Course {
	
	private Integer id;
	private String fullName;
	private String shortName;
	private Integer categoryId;
	private Integer categorySortOrder;
	private String idNombre;
	private Boolean visible;
	private String summary;
	private Integer summaryFormat;
	private String format;
	private Boolean showGrades;
	private Integer newsItems;
	private Date startDate;
	private Integer numSections;
	private Integer maxBytes;
	private Boolean showReports;
	private Boolean hiddenSections;
	private Integer groupMode;
	private Boolean groupModeForce;
	private Integer defaultGroupingId;
	private Date timeCreated;
	private Date timeModified;
	private Boolean enableCompletion;
	private Boolean completionNotify;
	private String lang;
	private String forceTheme;
	private List<CourseFormatOption> courseFormatOptions;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getCategorySortOrder() {
		return categorySortOrder;
	}
	public void setCategorySortOrder(Integer categorySortOrder) {
		this.categorySortOrder = categorySortOrder;
	}
	public String getIdNombre() {
		return idNombre;
	}
	public void setIdNombre(String idNombre) {
		this.idNombre = idNombre;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Integer getSummaryFormat() {
		return summaryFormat;
	}
	public void setSummaryFormat(Integer summaryFormat) {
		this.summaryFormat = summaryFormat;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Boolean getShowGrades() {
		return showGrades;
	}
	public void setShowGrades(Boolean showGrades) {
		this.showGrades = showGrades;
	}
	public Integer getNewsItems() {
		return newsItems;
	}
	public void setNewsItems(Integer newsItems) {
		this.newsItems = newsItems;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Integer getNumSections() {
		return numSections;
	}
	public void setNumSections(Integer numSections) {
		this.numSections = numSections;
	}
	public Integer getMaxBytes() {
		return maxBytes;
	}
	public void setMaxBytes(Integer maxBytes) {
		this.maxBytes = maxBytes;
	}
	public Boolean getShowReports() {
		return showReports;
	}
	public void setShowReports(Boolean showReports) {
		this.showReports = showReports;
	}
	public Boolean getHiddenSections() {
		return hiddenSections;
	}
	public void setHiddenSections(Boolean hiddenSections) {
		this.hiddenSections = hiddenSections;
	}
	public Integer getGroupMode() {
		return groupMode;
	}
	public void setGroupMode(Integer groupMode) {
		this.groupMode = groupMode;
	}
	public Boolean getGroupModeForce() {
		return groupModeForce;
	}
	public void setGroupModeForce(Boolean groupModeForce) {
		this.groupModeForce = groupModeForce;
	}
	public Integer getDefaultGroupingId() {
		return defaultGroupingId;
	}
	public void setDefaultGroupingId(Integer defaultGroupingId) {
		this.defaultGroupingId = defaultGroupingId;
	}
	public Date getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}
	public Date getTimeModified() {
		return timeModified;
	}
	public void setTimeModified(Date timeModified) {
		this.timeModified = timeModified;
	}
	public Boolean getEnableCompletion() {
		return enableCompletion;
	}
	public void setEnableCompletion(Boolean enableCompletion) {
		this.enableCompletion = enableCompletion;
	}
	public Boolean getCompletionNotify() {
		return completionNotify;
	}
	public void setCompletionNotify(Boolean completionNotify) {
		this.completionNotify = completionNotify;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getForceTheme() {
		return forceTheme;
	}
	public void setForceTheme(String forceTheme) {
		this.forceTheme = forceTheme;
	}
	public List<CourseFormatOption> getCourseFormatOptions() {
		return courseFormatOptions;
	}
	public void setCourseFormatOptions(List<CourseFormatOption> courseFormatOptions) {
		this.courseFormatOptions = courseFormatOptions;
	}

}
