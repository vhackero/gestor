/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

import java.util.List;

/**
 *
 * @author annelkaren
 */
public class Curso {

    private int id;
    private String fullname;
    private String shortname;
    private int categoryid;   //category id
    private String categoryname;   //category name
    private String summary;   //summary
    private int summaryformat;   //summary format (1 = HTML, 0 = MOODLE, 2 = PLAIN or 4 = MARKDOWN)
    private String idnumber;
    private Categoria category;
    private int visible;
    private Actividades activities;
    private int categorysortorder;
    private String format;
    private int showgrades;
    private int newsitems;
    private float startdate;
    private int numsections;
    private int maxbytes;
    private int showreports;
    private int hiddensections;
    private int groupmode;
    private int groupmodeforce;
    private int defaultgroupingid;
    private float timecreated;
    private float timemodified;
    private int enablecompletion;
    private int completionnotify;
    private String lang;
    private String forcetheme;
    private List<Preferencia> courseformatoptions;
    private List<ArchivoComplemento> overviewfiles;
    private List<Contactos> contacts;
    private List<String> enrollmentmethods;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    

    public List<ArchivoComplemento> getOverviewfiles() {
        return overviewfiles;
    }

    public void setOverviewfiles(List<ArchivoComplemento> overviewfiles) {
        this.overviewfiles = overviewfiles;
    }

    public List<Contactos> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contactos> contacts) {
        this.contacts = contacts;
    }

    public List<String> getEnrollmentmethods() {
        return enrollmentmethods;
    }

    public void setEnrollmentmethods(List<String> enrollmentmethods) {
        this.enrollmentmethods = enrollmentmethods;
    }
    
    /**
     * @return the summaryformat
     */
    public int getSummaryformat() {
        return summaryformat;
    }
    /**
     * @param summaryformat the summaryformat to set
     */
    public void setSummaryformat(int summaryformat) {
        this.summaryformat = summaryformat;
    }
    /**
     * @return the category
     */
    public Categoria getCategory() {
        return category;
    }
    /**
     * @param category the category to set
     */
    public void setCategory(Categoria category) {
        this.category = category;
    }
    /**
     * @return the activities
     */
    public Actividades getActivities() {
        return activities;
    }
    /**
     * @param activities the activities to set
     */
    public void setActivities(Actividades activities) {
        this.activities = activities;
    }
    /**
     * @return the idnumber
     */
    public String getIdnumber() {
        return idnumber;
    }
    /**
     * @param idnumber the idnumber to set
     */
    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
    /**
     * @return the visible
     */
    public int getVisible() {
        return visible;
    }
    /**
     * @param visible the visible to set
     */
    public void setVisible(int visible) {
        this.visible = visible;
    }
    /**
     * @return the categorysortorder
     */
    public int getCategorysortorder() {
        return categorysortorder;
    }
    /**
     * @param categorysortorder the categorysortorder to set
     */
    public void setCategorysortorder(int categorysortorder) {
        this.categorysortorder = categorysortorder;
    }
    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }
    /**
     * @param format the format to set
     */
    public void setFormat(String format) {
        this.format = format;
    }
    /**
     * @return the showgrades
     */
    public int getShowgrades() {
        return showgrades;
    }
    /**
     * @param showgrades the showgrades to set
     */
    public void setShowgrades(int showgrades) {
        this.showgrades = showgrades;
    }
    /**
     * @return the newsitems
     */
    public int getNewsitems() {
        return newsitems;
    }
    /**
     * @param newsitems the newsitems to set
     */
    public void setNewsitems(int newsitems) {
        this.newsitems = newsitems;
    }
    /**
     * @return the startdate
     */
    public float getStartdate() {
        return startdate;
    }
    /**
     * @param startdate the startdate to set
     */
    public void setStartdate(float startdate) {
        this.startdate = startdate;
    }
    /**
     * @return the numsections
     */
    public int getNumsections() {
        return numsections;
    }
    /**
     * @param numsections the numsections to set
     */
    public void setNumsections(int numsections) {
        this.numsections = numsections;
    }
    /**
     * @return the maxbytes
     */
    public int getMaxbytes() {
        return maxbytes;
    }
    /**
     * @param maxbytes the maxbytes to set
     */
    public void setMaxbytes(int maxbytes) {
        this.maxbytes = maxbytes;
    }
    /**
     * @return the showreports
     */
    public int getShowreports() {
        return showreports;
    }
    /**
     * @param showreports the showreports to set
     */
    public void setShowreports(int showreports) {
        this.showreports = showreports;
    }
    /**
     * @return the hiddensections
     */
    public int getHiddensections() {
        return hiddensections;
    }
    /**
     * @param hiddensections the hiddensections to set
     */
    public void setHiddensections(int hiddensections) {
        this.hiddensections = hiddensections;
    }
    /**
     * @return the groupmode
     */
    public int getGroupmode() {
        return groupmode;
    }
    /**
     * @param groupmode the groupmode to set
     */
    public void setGroupmode(int groupmode) {
        this.groupmode = groupmode;
    }
    /**
     * @return the groupmodeforce
     */
    public int getGroupmodeforce() {
        return groupmodeforce;
    }
    /**
     * @param groupmodeforce the groupmodeforce to set
     */
    public void setGroupmodeforce(int groupmodeforce) {
        this.groupmodeforce = groupmodeforce;
    }
    /**
     * @return the defaultgroupingid
     */
    public int getDefaultgroupingid() {
        return defaultgroupingid;
    }
    /**
     * @param defaultgroupingid the defaultgroupingid to set
     */
    public void setDefaultgroupingid(int defaultgroupingid) {
        this.defaultgroupingid = defaultgroupingid;
    }
    /**
     * @return the timecreated
     */
    public float getTimecreated() {
        return timecreated;
    }
    /**
     * @param timecreated the timecreated to set
     */
    public void setTimecreated(float timecreated) {
        this.timecreated = timecreated;
    }
    /**
     * @return the timemodified
     */
    public float getTimemodified() {
        return timemodified;
    }
    /**
     * @param timemodified the timemodified to set
     */
    public void setTimemodified(float timemodified) {
        this.timemodified = timemodified;
    }
    /**
     * @return the enablecompletion
     */
    public int getEnablecompletion() {
        return enablecompletion;
    }
    /**
     * @param enablecompletion the enablecompletion to set
     */
    public void setEnablecompletion(int enablecompletion) {
        this.enablecompletion = enablecompletion;
    }
    /**
     * @return the completionnotify
     */
    public int getCompletionnotify() {
        return completionnotify;
    }
    /**
     * @param completionnotify the completionnotify to set
     */
    public void setCompletionnotify(int completionnotify) {
        this.completionnotify = completionnotify;
    }
    /**
     * @return the lang
     */
    public String getLang() {
        return lang;
    }
    /**
     * @param lang the lang to set
     */
    public void setLang(String lang) {
        this.lang = lang;
    }
    /**
     * @return the forcetheme
     */
    public String getForcetheme() {
        return forcetheme;
    }
    /**
     * @param forcetheme the forcetheme to set
     */
    public void setForcetheme(String forcetheme) {
        this.forcetheme = forcetheme;
    }
    /**
     * @return the courseformatoptions
     */
    public List<Preferencia> getCourseformatoptions() {
        return courseformatoptions;
    }
    /**
     * @param courseformatoptions the courseformatoptions to set
     */
    public void setCourseformatoptions(List<Preferencia> courseformatoptions) {
        this.courseformatoptions = courseformatoptions;
    }
    
    @Override
    public String toString() {
        return "Curso{" + "id=" + id + ", fullname=" + fullname + ", shortname=" + shortname + ", categoryid=" + categoryid + ", categoryname=" + categoryname + ", summary=" + summary + ", summaryformat=" + summaryformat + ", overviewfiles=" + overviewfiles + ", contacts=" + contacts + ", enrollmentmethods=" + enrollmentmethods + '}';
    }
}
